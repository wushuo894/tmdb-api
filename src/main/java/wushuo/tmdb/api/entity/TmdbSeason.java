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

    @Schema(description = "id")
    private String id;

    @Schema(description = "季数")
    @SerializedName(value = "seasonNumber", alternate = "season_number")
    private Integer seasonNumber;

    @Schema(description = "评分")
    @SerializedName(value = "voteAverage", alternate = "vote_average")
    private Double voteAverage;

    @Schema(description = "首播日期")
    @SerializedName(value = "airDate", alternate = "air_date")
    private Date airDate;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "概述")
    private String overview;

    @Schema(description = "集列表")
    @SerializedName(value = "episodes")
    private List<TmdbEpisode> episodes;

    @Schema(description = "封面路径")
    @SerializedName(value = "posterPath", alternate = "poster_path")
    private String posterPath;

    @Schema(description = "排序")
    private Integer order;

}
