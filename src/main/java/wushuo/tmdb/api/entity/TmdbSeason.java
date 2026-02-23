package wushuo.tmdb.api.entity;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 季
 */
@Data
@Accessors(chain = true)
public class TmdbSeason implements Serializable {

     @Schema(name = "id")
    private String id;

     @Schema(name = "季数")
    @SerializedName(value = "seasonNumber", alternate = "season_number")
    private Integer seasonNumber;

     @Schema(name = "评分")
    @SerializedName(value = "voteAverage", alternate = "vote_average")
    private Double voteAverage;

     @Schema(name = "首播日期")
    @SerializedName(value = "airDate", alternate = "air_date")
    private Date airDate;

     @Schema(name = "名称")
    private String name;

     @Schema(name = "概述")
    private String overview;

     @Schema(name = "集列表")
    @SerializedName(value = "episodes")
    private List<TmdbEpisode> episodes;

     @Schema(name = "封面路径")
    @SerializedName(value = "posterPath", alternate = "poster_path")
    private String posterPath;

     @Schema(name = "排序")
    private Integer order;

}
