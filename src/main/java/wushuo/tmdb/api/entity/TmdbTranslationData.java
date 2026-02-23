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
     @Schema(name = "名称")
    private String name;

     @Schema(name = "概述")
    private String overview;

     @Schema(name = "主页")
    private String homepage;

     @Schema(name = "宣传语")
    private String tagline;
}
