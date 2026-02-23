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
    @Schema(description = "id")
    private String id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    @SerializedName(value = "name", alternate = "title")
    private String name;

    /**
     * 原名
     */
    @Schema(description = "原名")
    @SerializedName(value = "originalName", alternate = {"original_name", "original_title"})
    private String originalName;

    /**
     * 剧集组id
     */
    @Schema(description = "剧集组id")
    @SerializedName(value = "tmdbGroupId", alternate = "tmdb_group_id")
    private String tmdbGroupId;

    /**
     * 概述
     */
    @Schema(description = "概述")
    private String overview;

    /**
     * 平均评分
     */
    @Schema(description = "平均评分")
    @SerializedName(value = "voteAverage", alternate = "vote_average")
    private String voteAverage;

    /**
     * 评分人数
     */
    @Schema(description = "评分人数")
    @SerializedName(value = "voteCount", alternate = "vote_count")
    private String voteCount;

    /**
     * 封面
     */
    @Schema(description = "封面")
    @SerializedName(value = "posterPath", alternate = "poster_path")
    private String posterPath;

    /**
     * 背景图
     */
    @Schema(description = "背景图")
    @SerializedName(value = "backdropPath", alternate = "backdrop_path")
    private String backdropPath;

    /**
     * 成人
     */
    @Schema(description = "成人")
    private Boolean adult;

    /**
     * 原语言
     */
    @Schema(description = "原语言")
    @SerializedName(value = "originalLanguage", alternate = "original_language")
    private String originalLanguage;

    /**
     * 原产地
     */
    @Schema(description = "原产地")
    @SerializedName(value = "originCountry", alternate = "origin_country")
    private List<String> originCountry;

    /**
     * 首映日期
     */
    @Schema(description = "首映日期")
    @SerializedName(value = "date", alternate = {"first_air_date", "release_date"})
    private Date date;

    /**
     * 种类
     */
    @Schema(description = "种类")
    @SerializedName(value = "genreIds", alternate = "genre_ids")
    private List<Integer> genreIds;

    /**
     * 主页
     */
    @Schema(description = "主页")
    private String homepage;

    /**
     * 种类
     */
    @Schema(description = "种类")
    private List<TmdbGenres> genres;

    /**
     * 演职人员
     */
    @Schema(description = "演职人员")
    private TmdbCredits credits;

    /**
     * 工作室
     */
    @Schema(description = "工作室")
    @SerializedName(value = "networks", alternate = "production_companies")
    private List<TmdbNetwork> networks;

    /**
     * 预告片
     */
    @Schema(description = "预告片")
    private List<TmdbVideo> videos;

    /**
     * 时长
     */
    @Schema(description = "时长")
    private Integer runtime;

    /**
     * 宣传语
     */
    @Schema(description = "宣传语")
    private String tagline;

    /**
     * 翻译
     */
    @Schema(description = "翻译")
    private TmdbTranslations translations;

    @Schema(description = "类型")
    private TmdbTypeEnum tmdbType;

    /**
     * 用于存放其他信息
     */
    @Schema(description = "其他信息")
    private Map<String, String> otherMap = new HashMap<>();
}
