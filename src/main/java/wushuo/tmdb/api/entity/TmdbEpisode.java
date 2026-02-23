package wushuo.tmdb.api.entity;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 集
 */
@Data
@Accessors(chain = true)
public class TmdbEpisode implements Serializable {
     @Schema(name = "id")
    private Integer id;

     @Schema(name = "播出日期")
    @SerializedName(value = "airDate", alternate = "air_date")
    private Date airDate;

     @Schema(name = "集数")
    @SerializedName(value = "episodeNumber", alternate = "episode_number")
    private Integer episodeNumber;

     @Schema(name = "集类型")
    @SerializedName(value = "episodeType", alternate = "episode_type")
    private String episodeType;

     @Schema(name = "名称")
    private String name;

     @Schema(name = "概述")
    private String overview;

     @Schema(name = "时长")
    private String runtime;

     @Schema(name = "季数")
    @SerializedName(value = "seasonNumber", alternate = "season_number")
    private Integer seasonNumber;

     @Schema(name = "剧ID")
    @SerializedName(value = "showId", alternate = "show_id")
    private Integer showId;

     @Schema(name = "剧照路径")
    @SerializedName(value = "stillPath", alternate = "still_path")
    private String stillPath;

     @Schema(name = "评分")
    @SerializedName(value = "voteAverage", alternate = "vote_average")
    private Double voteAverage;

     @Schema(name = "排序")
    private Integer order;
}
