package wushuo.tmdb.api.entity;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 工作室
 */
@Data
@Accessors(chain = true)
public class TmdbNetwork implements Serializable {
     @Schema(name = "id")
    private String id;

     @Schema(name = "Logo路径")
    @SerializedName(value = "logoPath", alternate = "logo_path")
    private String logoPath;

     @Schema(name = "名称")
    private String name;

     @Schema(name = "原产地")
    @SerializedName(value = "originCountry", alternate = "origin_country")
    private String originCountry;
}
