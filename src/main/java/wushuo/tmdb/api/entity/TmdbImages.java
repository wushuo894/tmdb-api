package wushuo.tmdb.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 图片列表
 */
@Data
@Accessors(chain = true)
public class TmdbImages implements Serializable {
    @Schema(description = "id")
    private String id;

    /**
     * logo
     */
    @Schema(description = "Logo")
    private List<TmdbImage> logos;

    /**
     * 封面
     */
    @Schema(description = "封面")
    private List<TmdbImage> posters;

    /**
     * 背景
     */
    @Schema(description = "背景")
    private List<TmdbImage> backdrops;
}
