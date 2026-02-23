package wushuo.tmdb.api.entity;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class TmdbVideo implements Serializable {
    @Schema(description = "id")
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "Key")
    private String key;

    @Schema(description = "站点")
    private String site;

    @Schema(description = "尺寸")
    private Integer size;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "官方")
    private Boolean official;

    @Schema(description = "发布时间")
    @SerializedName(value = "publishedAt", alternate = "published_at")
    private Date publishedAt;

    @Schema(description = "国家代码")
    @SerializedName(value = "iso31661", alternate = "iso_3166_1")
    private String iso31661;

    @Schema(description = "语言代码")
    @SerializedName(value = "iso6391", alternate = "iso_639_1")
    private String iso6391;
}
