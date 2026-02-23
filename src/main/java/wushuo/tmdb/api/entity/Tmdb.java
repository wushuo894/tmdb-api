package wushuo.tmdb.api.entity;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import wushuo.tmdb.api.enums.TmdbTypeEnum;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tmdb
 */
@Data
@Accessors(chain = true)
public class Tmdb implements Serializable {
    /**
     * id
     */
    @Schema(name = "id")
    private String id;

    /**
     * 名称
     */
    @Schema(name = "名称")
    @SerializedName(value = "name", alternate = "title")
    private String name;

    /**
     * 原名
     */
    @Schema(name = "原名")
    @SerializedName(value = "originalName", alternate = {"original_name", "original_title"})
    private String originalName;

    /**
     * 剧集组id
     */
    @Schema(name = "剧集组id")
    @SerializedName(value = "tmdbGroupId", alternate = "tmdb_group_id")
    private String tmdbGroupId;

    /**
     * 概述
     */
    @Schema(name = "概述")
    private String overview;

    /**
     * 平均评分
     */
    @Schema(name = "平均评分")
    @SerializedName(value = "voteAverage", alternate = "vote_average")
    private String voteAverage;

    /**
     * 评分人数
     */
    @Schema(name = "评分人数")
    @SerializedName(value = "voteCount", alternate = "vote_count")
    private String voteCount;

    /**
     * 封面
     */
    @Schema(name = "封面")
    @SerializedName(value = "posterPath", alternate = "poster_path")
    private String posterPath;

    /**
     * 背景图
     */
    @Schema(name = "背景图")
    @SerializedName(value = "backdropPath", alternate = "backdrop_path")
    private String backdropPath;

    /**
     * 成人
     */
    @Schema(name = "成人")
    private Boolean adult;

    /**
     * 原语言
     */
    @Schema(name = "原语言")
    @SerializedName(value = "originalLanguage", alternate = "original_language")
    private String originalLanguage;

    /**
     * 原产地
     */
    @Schema(name = "原产地")
    @SerializedName(value = "originCountry", alternate = "origin_country")
    private List<String> originCountry;

    /**
     * 首映日期
     */
    @Schema(name = "首映日期")
    @SerializedName(value = "date", alternate = {"first_air_date", "release_date"})
    private Date date;

    /**
     * 种类
     */
    @Schema(name = "种类")
    @SerializedName(value = "genreIds", alternate = "genre_ids")
    private List<Integer> genreIds;

    /**
     * 主页
     */
    @Schema(name = "主页")
    private String homepage;

    /**
     * 种类
     */
    @Schema(name = "种类")
    private List<TmdbGenres> genres;

    /**
     * 演职人员
     */
    @Schema(name = "演职人员")
    private TmdbCredits credits;

    /**
     * 工作室
     */
    @Schema(name = "工作室")
    @SerializedName(value = "networks", alternate = "production_companies")
    private List<TmdbNetwork> networks;

    /**
     * 预告片
     */
    @Schema(name = "预告片")
    private List<TmdbVideo> videos;

    /**
     * 时长
     */
    @Schema(name = "时长")
    private Integer runtime;

    /**
     * 宣传语
     */
    @Schema(name = "宣传语")
    private String tagline;

    /**
     * 翻译
     */
    @Schema(name = "翻译")
    private TmdbTranslations translations;

    @Schema(name = "类型")
    private TmdbTypeEnum tmdbType;

    /**
     * 用于存放其他信息
     */
    @Schema(name = "其他信息")
    private Map<String, String> otherMap = new HashMap<>();
}
