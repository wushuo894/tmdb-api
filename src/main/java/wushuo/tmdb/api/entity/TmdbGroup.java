package wushuo.tmdb.api.entity;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * tmdb剧集组
 */
@Data
@Accessors(chain = true)
public class TmdbGroup implements Serializable {
    /**
     * id
     */
    @Schema(description = "id")
    private String id;

    /**
     * 剧集组名
     */
    @Schema(description = "剧集组名")
    private String name;

    /**
     * 类型
     */
    @Schema(description = "类型")
    private Integer type;

    /**
     * 类型名
     */
    @Schema(description = "类型名")
    @SerializedName(value = "typeName", alternate = "type_name")
    private String typeName;

    /**
     * 集数量
     */
    @Schema(description = "集数量")
    @SerializedName(value = "episodeCount", alternate = "episode_count")
    private String episodeCount;

    /**
     * 组数量
     */
    @Schema(description = "组数量")
    @SerializedName(value = "groupCount", alternate = "group_count")
    private String groupCount;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;
}
