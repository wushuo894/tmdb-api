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
     @Schema(name = "id")
    private Integer id;

     @Schema(name = "名称")
    private String name;

     @Schema(name = "原名")
    @SerializedName(value = "originalName", alternate = "original_name")
    private String originalName;

     @Schema(name = "热度")
    private Double popularity;

     @Schema(name = "头像路径")
    @SerializedName(value = "profilePath", alternate = "profile_path")
    private String profilePath;

     @Schema(name = "角色")
    private String character;

     @Schema(name = "演职员ID")
    @SerializedName(value = "creditId", alternate = "credit_id")
    private String creditId;

     @Schema(name = "排序")
    private String order;

    /**
     * 成人
     */
     @Schema(name = "成人")
    private Boolean adult;

    /**
     * 性别
     */
     @Schema(name = "性别")
    public Integer gender;

     @Schema(name = "所属部门")
    @SerializedName(value = "knownForDepartment", alternate = "known_for_department")
    private String knownForDepartment;
}
