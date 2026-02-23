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
     @Schema(name = "id")
    private String id;

     @Schema(name = "名称")
    private String name;

     @Schema(name = "Key")
    private String key;

     @Schema(name = "站点")
    private String site;

     @Schema(name = "尺寸")
    private Integer size;

     @Schema(name = "类型")
    private String type;

     @Schema(name = "官方")
    private Boolean official;

     @Schema(name = "发布时间")
    @SerializedName(value = "publishedAt", alternate = "published_at")
    private Date publishedAt;

     @Schema(name = "国家代码")
    @SerializedName(value = "iso31661", alternate = "iso_3166_1")
    private String iso31661;

     @Schema(name = "语言代码")
    @SerializedName(value = "iso6391", alternate = "iso_639_1")
    private String iso6391;
}
