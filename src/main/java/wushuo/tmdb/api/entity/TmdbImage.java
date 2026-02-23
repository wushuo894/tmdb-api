package wushuo.tmdb.api.entity;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 图片
 */
@Data
@Accessors(chain = true)
public class TmdbImage implements Serializable {
    @Schema(description = "宽高比")
    @SerializedName(value = "aspectRatio", alternate = "aspect_ratio")
    private Double aspectRatio;

    @Schema(description = "宽度")
    private Integer width;

    @Schema(description = "高度")
    private Integer height;

    @Schema(description = "国家代码")
    @SerializedName(value = "iso31661", alternate = "iso_3166_1")
    private String iso31661;

    @Schema(description = "语言代码")
    @SerializedName(value = "iso6391", alternate = "iso_639_1")
    private String iso6391;

    @Schema(description = "文件路径")
    @SerializedName(value = "filePath", alternate = "file_path")
    private String filePath;

    @Schema(description = "评分")
    @SerializedName(value = "voteAverage", alternate = "vote_average")
    private Double voteAverage;

    @Schema(description = "评分人数")
    @SerializedName(value = "voteCount", alternate = "vote_count")
    private Integer voteCount;
}
