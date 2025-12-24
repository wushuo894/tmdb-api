package wushuo.tmdb.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wushuo.tmdb.api.common.GsonStatic;
import wushuo.tmdb.api.common.HttpReq;
import wushuo.tmdb.api.entity.*;
import wushuo.tmdb.api.enums.TmdbTypeEnum;

import java.util.*;
import java.util.function.ToDoubleFunction;

/**
 * tmdb封装
 */
@Slf4j
@RequiredArgsConstructor
public class TmdbUtil {
    private final TmdbConfig config;

    /**
     * api
     *
     * @return
     */
    public String getTmdbApi() {
        String tmdbApi = config.getTmdbApi();
        tmdbApi = StrUtil.blankToDefault(tmdbApi, "https://api.themoviedb.org");
        return tmdbApi;
    }

    /**
     * apiKey
     *
     * @return
     */
    public String getTmdbApiKey() {
        String tmdbApiKey = config.getTmdbApiKey();
        Assert.notNull(tmdbApiKey, "tmdbApiKey is null");
        return tmdbApiKey;
    }

    /**
     * 获取所有标题
     *
     * @param tmdb     tmdb
     * @param tmdbType 类型
     * @return
     */
    public List<TmdbTitle> getTitles(Tmdb tmdb, TmdbTypeEnum tmdbType) {
        if (Objects.isNull(tmdb)) {
            return List.of();
        }

        String tmdbApi = getTmdbApi();
        String tmdbApiKey = getTmdbApiKey();
        String id = tmdb.getId();
        String url = StrFormatter.format("{}/3/{}/{}/alternative_titles", tmdbApi, tmdbType.getValue(), id);
        List<TmdbTitle> tmdbTitles = HttpReq.get(url)
                .proxy(config)
                .timeout(5000)
                .form("api_key", tmdbApiKey)
                .form("include_adult", "true")
                .thenFunction(res -> {
                    HttpReq.assertStatus(res);
                    JsonObject jsonObject = GsonStatic.fromJson(res.body(), JsonObject.class);
                    return GsonStatic.fromJsonList(
                            jsonObject.getAsJsonArray("results"),
                            TmdbTitle.class);
                });

        for (TmdbTitle tmdbTitle : tmdbTitles) {
            String name = NameUtil.getName(tmdbTitle.getTitle());
            tmdbTitle.setTitle(name);
        }

        return tmdbTitles;
    }

    /**
     * 根据标题获得tmdb
     *
     * @param titleName 标题名
     * @return
     */
    public Optional<Tmdb> getTmdbMovie(String titleName) {
        return getTmdb(titleName, TmdbTypeEnum.MOVIE);
    }

    /**
     * 根据标题获得tmdb
     *
     * @param titleName 标题名
     * @return
     */
    public Optional<Tmdb> getTmdbTv(String titleName) {
        return getTmdb(titleName, TmdbTypeEnum.TV);
    }

    /**
     * 刷新tmdb信息
     *
     * @param tmdb     tmdb
     * @param tmdbType 类型
     * @return
     */
    public Optional<Tmdb> getTmdb(Tmdb tmdb, TmdbTypeEnum tmdbType) {
        String tmdbLanguage = config.getTmdbLanguage();
        String tmdbApi = getTmdbApi();
        String tmdbApiKey = getTmdbApiKey();

        String id = tmdb.getId();

        String url = StrFormatter.format("{}/3/{}/{}", tmdbApi, tmdbType.getValue(), id);

        Tmdb newTmdb = HttpReq.get(url)
                .proxy(config)
                .timeout(5000)
                .form("api_key", tmdbApiKey)
                .form("include_adult", "true")
                .form("append_to_response", "translations,credits")
                .form("language", tmdbLanguage)
                .thenFunction(res -> {
                    HttpReq.assertStatus(res);
                    return GsonStatic.fromJson(res.body(), Tmdb.class)
                            .setTmdbType(tmdbType);
                });

        BeanUtil.copyProperties(
                newTmdb,
                tmdb,
                CopyOptions
                        .create()
                        .setIgnoreNullValue(true)
        );

        // 宣传语
        String tagLine = tmdb.getTagline();

        if (StrUtil.isBlank(tagLine)) {
            TmdbTranslations translations = tmdb.getTranslations();
            Optional<String> first = translations
                    .getTranslations()
                    .stream()
                    .sorted(Comparator.comparingInt(it -> {
                        int i = List.of("CN", "TW", "HK", "JP", "US").indexOf(it.getIso31661());
                        if (i > -1) {
                            return i;
                        }
                        return Integer.MAX_VALUE;
                    }))
                    .map(TmdbTranslation::getData)
                    .map(TmdbTranslationData::getTagline)
                    .filter(StrUtil::isNotBlank)
                    .findFirst();
            if (first.isPresent()) {
                tagLine = first.get();
            }
        }

        // 宣传片
        List<TmdbVideo> videos = getVideos(tmdb, tmdbType);

        tmdb
                .setTagline(tagLine)
                .setVideos(videos);

        return Optional.of(tmdb);
    }

    /**
     * 根据名称获取tmdb信息
     *
     * @param titleName 标题名
     * @param tmdbType  类型
     * @return
     */
    public List<Tmdb> search(String titleName, TmdbTypeEnum tmdbType) {
        titleName = NameUtil.nameDel(titleName);
        if (StrUtil.isBlank(titleName)) {
            return new ArrayList<>();
        }

        String tmdbLanguage = config.getTmdbLanguage();

        String tmdbApi = getTmdbApi();
        String tmdbApiKey = getTmdbApiKey();

        List<Tmdb> tmdbList = HttpReq.get(tmdbApi + "/3/search/" + tmdbType.getValue())
                .proxy(config)
                .timeout(5000)
                .form("query", URLUtil.encodeBlank(titleName))
                .form("api_key", tmdbApiKey)
                .form("include_adult", "true")
                .form("language", tmdbLanguage)
                .thenFunction(res -> {
                    HttpReq.assertStatus(res);
                    JsonObject body = GsonStatic.fromJson(res.body(), JsonObject.class);

                    List<Tmdb> tmdbs = new ArrayList<>();

                    for (JsonElement item : body.getAsJsonArray("results")) {
                        try {
                            Tmdb tmdb = GsonStatic.fromJson(item, Tmdb.class);
                            tmdb.setTmdbType(tmdbType);
                            tmdbs.add(tmdb);
                        } catch (Exception ignored) {
                        }
                    }

                    Boolean tmdbAnime = config.getTmdbAnime();

                    if (tmdbAnime) {
                        // 过滤出动漫 genreIds 16
                        tmdbs = tmdbs.stream()
                                .filter(it -> {
                                    List<Integer> genreIds = it.getGenreIds();
                                    return genreIds.contains(16);
                                }).toList();
                    }

                    return tmdbs.stream()
                            .sorted(Comparator.comparingLong(tmdb -> Long.MAX_VALUE - tmdb.getDate().getTime()))
                            .toList();
                });

        if (!tmdbList.isEmpty()) {
            return tmdbList;
        }

        if (!titleName.contains(" ")) {
            return tmdbList;
        }

        List<String> split = StrUtil.split(titleName, " ", true, true);
        split.remove(split.size() - 1);
        return search(String.join(" ", split), tmdbType);
    }

    /**
     * 根据名称获取tmdb信息
     *
     * @param titleName 标题名
     * @param tmdbType  类型
     * @return
     */
    public Optional<Tmdb> getTmdb(String titleName, TmdbTypeEnum tmdbType) {
        if (StrUtil.isBlank(titleName)) {
            return Optional.empty();
        }

        titleName = NameUtil.nameDel(titleName);

        List<Tmdb> tmdbList = search(titleName, tmdbType);

        if (tmdbList.isEmpty()) {
            return Optional.empty();
        }

        // 优先使用名称完全匹配
        String finalTitleName = titleName;
        Tmdb get = tmdbList
                .stream()
                .filter(tmdb -> {
                    String name = tmdb.getName();
                    String originalName = tmdb.getOriginalName();
                    return List.of(name, originalName).contains(finalTitleName);
                })
                .findFirst()
                .orElse(tmdbList.get(0));
        String name = get.getName();
        name = NameUtil.getName(name);
        get.setName(name);
        return Optional.of(get);
    }

    /**
     * 获取季信息
     *
     * @param tmdb   tmdb
     * @param season 季
     * @return
     */
    public Optional<TmdbSeason> getTmdbSeason(Tmdb tmdb, Integer season) {
        String tmdbLanguage = config.getTmdbLanguage();

        String id = tmdb.getId();

        String tmdbApi = getTmdbApi();

        String tmdbApiKey = getTmdbApiKey();

        String tmdbGroupId = tmdb.getTmdbGroupId();

        if (StrUtil.isBlank(tmdbGroupId)) {
            String url = StrFormatter.format("{}/3/tv/{}/season/{}", tmdbApi, id, season);

            return HttpReq.get(url)
                    .proxy(config)
                    .timeout(5000)
                    .form("api_key", tmdbApiKey)
                    .form("include_adult", "true")
                    .form("language", tmdbLanguage)
                    .thenFunction(res -> {
                        int status = res.getStatus();
                        if (status == 404) {
                            return Optional.empty();
                        }
                        HttpReq.assertStatus(res);
                        TmdbSeason tmdbSeason = GsonStatic.fromJson(res.body(), TmdbSeason.class);
                        return Optional.of(tmdbSeason);
                    });
        }

        String url = StrFormatter.format("{}/3/tv/episode_group/{}", tmdbApi, tmdbGroupId);

        Optional<JsonObject> jsonObject = HttpReq.get(url)
                .proxy(config)
                .timeout(5000)
                .form("api_key", tmdbApiKey)
                .form("include_adult", "true")
                .form("language", tmdbLanguage)
                .thenFunction(res -> {
                    int status = res.getStatus();
                    if (status == 404) {
                        return Optional.empty();
                    }
                    HttpReq.assertStatus(res);
                    return Optional.of(
                            GsonStatic.fromJson(res.body(), JsonObject.class)
                    );
                });

        if (jsonObject.isEmpty()) {
            return Optional.empty();
        }

        JsonArray groups = jsonObject
                .get()
                .getAsJsonArray("groups");

        List<TmdbSeason> tmdbSeasons = GsonStatic.fromJsonList(groups, TmdbSeason.class);

        Optional<TmdbSeason> first = tmdbSeasons
                .stream()
                .filter(it -> it.getOrder().intValue() == season)
                .findFirst();
        first.ifPresent(tmdbSeason -> {
            List<TmdbEpisode> episodes = tmdbSeason.getEpisodes();
            for (TmdbEpisode episode : episodes) {
                Integer order = episode.getOrder();
                episode
                        .setSeasonNumber(season)
                        .setEpisodeNumber(order + 1);
            }

            Date airDate = episodes
                    .get(0)
                    .getAirDate();

            tmdbSeason
                    .setSeasonNumber(season)
                    .setOverview("")
                    .setVoteAverage(0.0)
                    .setAirDate(airDate);
        });

        return first;
    }

    /**
     * 获取每集的标题
     *
     * @param tmdb   tmdb
     * @param season 季
     * @return
     */
    public Map<Integer, String> getEpisodeTitleMap(Tmdb tmdb, Integer season) {
        Map<Integer, String> map = new HashMap<>();

        if (Objects.isNull(tmdb)) {
            return map;
        }
        try {
            Optional<TmdbSeason> tmdbSeason = getTmdbSeason(tmdb, season);
            if (tmdbSeason.isEmpty()) {
                return map;
            }
            List<TmdbEpisode> episodes = tmdbSeason.get()
                    .getEpisodes();
            for (TmdbEpisode episode : episodes) {
                Integer episodeNumber = episode.getEpisodeNumber();
                String name = episode.getName();
                name = NameUtil.getName(name);
                map.put(episodeNumber, name);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return map;
    }

    /**
     * 获取剧集组
     *
     * @param tmdb tmdb
     * @return
     */
    public List<TmdbGroup> getTmdbGroup(Tmdb tmdb) {
        String tmdbApi = getTmdbApi();
        String tmdbApiKey = getTmdbApiKey();

        String tmdbLanguage = config.getTmdbLanguage();

        String id = tmdb.getId();

        Map<Integer, String> typeMap = Map.of(
                1, "首播日期",
                2, "独立",
                3, "DVD",
                4, "数字",
                5, "故事线",
                6, "制片",
                7, "电视"
        );

        String url = StrFormatter.format("{}/3/tv/{}/episode_groups", tmdbApi, id);
        return HttpReq.get(url)
                .proxy(config)
                .timeout(5000)
                .form("api_key", tmdbApiKey)
                .form("include_adult", "true")
                .form("language", tmdbLanguage)
                .thenFunction(response -> {
                    HttpReq.assertStatus(response);
                    JsonObject body = GsonStatic.fromJson(response.body(), JsonObject.class);
                    JsonArray results = body.getAsJsonArray("results");
                    return GsonStatic.fromJsonList(results, TmdbGroup.class)
                            .stream()
                            .peek(tmdbGroup -> {
                                Integer type = tmdbGroup.getType();
                                String typeName = typeMap.getOrDefault(type, "其他");
                                tmdbGroup.setTypeName(typeName);
                            }).toList();
                });
    }

    /**
     * 演职人员
     *
     * @param tmdb     tmdb
     * @param tmdbType 类型
     * @return
     */
    public List<TmdbCredits> getCredits(Tmdb tmdb, TmdbTypeEnum tmdbType) {
        String tmdbApi = getTmdbApi();
        String tmdbApiKey = getTmdbApiKey();

        String tmdbLanguage = config.getTmdbLanguage();

        String id = tmdb.getId();

        String url = StrFormatter.format("{}/3/{}/{}/credits", tmdbApi, tmdbType.getValue(), id);
        return HttpReq.get(url)
                .proxy(config)
                .timeout(5000)
                .form("api_key", tmdbApiKey)
                .form("include_adult", "true")
                .form("language", tmdbLanguage)
                .thenFunction(res -> {
                    HttpReq.assertStatus(res);

                    JsonObject jsonObject = GsonStatic.fromJson(res.body(), JsonObject.class);
                    JsonArray cast = jsonObject.getAsJsonArray("cast");

                    return GsonStatic.fromJsonList(cast, TmdbCredits.class);
                });
    }

    /**
     * 获取图片
     *
     * @param tmdb     tmdb
     * @param tmdbType 类型
     * @return
     */
    public TmdbImages getTmdbImages(Tmdb tmdb, TmdbTypeEnum tmdbType) {
        String tmdbApi = getTmdbApi();
        String tmdbApiKey = getTmdbApiKey();

        String id = tmdb.getId();

        String tmdbLanguage = config.getTmdbLanguage();

        String url = StrFormatter.format("{}/3/{}/{}/images", tmdbApi, tmdbType.getValue(), id);
        TmdbImages tmdbImages = HttpReq.get(url)
                .proxy(config)
                .timeout(5000)
                .form("api_key", tmdbApiKey)
                .form("include_adult", "true")
                .thenFunction(res -> {
                    HttpReq.assertStatus(res);
                    return GsonStatic.fromJson(res.body(), TmdbImages.class);
                });

        // 排序方法
        ToDoubleFunction<TmdbImage> sortedFun = it -> {
            Double voteAverage = it.getVoteAverage();

            String iso6391 = it.getIso6391();
            String iso31661 = it.getIso31661();

            if (StrUtil.isBlank(iso6391)) {
                return 50 + voteAverage;
            }

            String lang = iso6391 + "-" + iso31661;

            if (tmdbLanguage.equals(lang)) {
                return voteAverage;
            }

            if (lang.equals("zh-CN")) {
                return 10 + voteAverage;
            }

            if (lang.startsWith("zh-")) {
                return 20 + voteAverage;
            }

            if (lang.startsWith("ja-")) {
                return 30 + voteAverage;
            }

            return 40 + voteAverage;
        };

        List<TmdbImage> logos = tmdbImages.getLogos();
        List<TmdbImage> posters = tmdbImages.getPosters();
        List<TmdbImage> backdrops = tmdbImages.getBackdrops();

        // 图片排序
        logos = logos.stream()
                .sorted(Comparator.comparingDouble(sortedFun))
                .toList();
        posters = posters.stream()
                .sorted(Comparator.comparingDouble(sortedFun))
                .toList();
        backdrops = backdrops.stream()
                .sorted(Comparator.comparingDouble(sortedFun))
                .toList();

        return tmdbImages.setLogos(logos)
                .setPosters(posters)
                .setBackdrops(backdrops);
    }

    /**
     * 获取预告片
     *
     * @param tmdb     tmdb
     * @param tmdbType 类型
     * @return
     */
    public List<TmdbVideo> getVideos(Tmdb tmdb, TmdbTypeEnum tmdbType) {
        String tmdbApi = getTmdbApi();
        String tmdbApiKey = getTmdbApiKey();

        String id = tmdb.getId();

        String url = StrFormatter.format("{}/3/{}/{}/videos", tmdbApi, tmdbType.getValue(), id);
        return HttpReq.get(url)
                .proxy(config)
                .timeout(5000)
                .form("api_key", tmdbApiKey)
                .form("include_adult", "true")
                .thenFunction(res -> {
                    HttpReq.assertStatus(res);
                    JsonArray results = GsonStatic.fromJson(res.body(), JsonObject.class)
                            .getAsJsonArray("results");
                    return GsonStatic.fromJsonList(results, TmdbVideo.class);
                });
    }

}

