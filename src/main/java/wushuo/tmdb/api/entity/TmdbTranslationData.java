package wushuo.tmdb.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 翻译
 */
@Data
@Accessors(chain = true)
public class TmdbTranslationData implements Serializable {
    @Schema(description = "名称")
    private String name;

    @Schema(description = "概述")
    private String overview;

    @Schema(description = "主页")
    private String homepage;

    @Schema(description = "宣传语")
    private String tagline;
}
