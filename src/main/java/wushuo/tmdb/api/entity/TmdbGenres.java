package wushuo.tmdb.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 类型
 */
@Data
@Accessors(chain = true)
public class TmdbGenres implements Serializable {
     @Schema(name = "id")
    private Integer id;
     @Schema(name = "名称")
    private String name;
}
