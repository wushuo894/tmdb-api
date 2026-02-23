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
    @Schema(description = "id")
    private String id;

    @Schema(description = "Logo路径")
    @SerializedName(value = "logoPath", alternate = "logo_path")
    private String logoPath;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "原产地")
    @SerializedName(value = "originCountry", alternate = "origin_country")
    private String originCountry;
}
