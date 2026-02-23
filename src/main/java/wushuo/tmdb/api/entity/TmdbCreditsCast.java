package wushuo.tmdb.api.entity;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 演职人员
 */
@Data
@Accessors(chain = true)
public class TmdbCreditsCast implements Serializable {
    @Schema(description = "id")
    private Integer id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "原名")
    @SerializedName(value = "originalName", alternate = "original_name")
    private String originalName;

    @Schema(description = "热度")
    private Double popularity;

    @Schema(description = "头像路径")
    @SerializedName(value = "profilePath", alternate = "profile_path")
    private String profilePath;

    @Schema(description = "角色")
    private String character;

    @Schema(description = "演职员ID")
    @SerializedName(value = "creditId", alternate = "credit_id")
    private String creditId;

    @Schema(description = "排序")
    private String order;

    /**
     * 成人
     */
    @Schema(description = "成人")
    private Boolean adult;

    /**
     * 性别
     */
    @Schema(description = "性别")
    public Integer gender;

    @Schema(description = "所属部门")
    @SerializedName(value = "knownForDepartment", alternate = "known_for_department")
    private String knownForDepartment;
}
