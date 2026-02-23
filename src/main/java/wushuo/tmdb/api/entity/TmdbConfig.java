package wushuo.tmdb.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class TmdbConfig implements Serializable {
    public TmdbConfig(@NonNull String tmdbApiKey) {
        this.tmdbApiKey = tmdbApiKey;
        this.tmdbApi = "https://api.themoviedb.org";
        this.tmdbLanguage = "zh-CN";
        this.tmdbAnime = false;
        this.proxy = false;
    }

    public TmdbConfig(@NonNull String tmdbApiKey, @NonNull String tmdbApi, @NonNull String tmdbLanguage) {
        this.tmdbApiKey = tmdbApiKey;
        this.tmdbApi = tmdbApi;
        this.tmdbLanguage = tmdbLanguage;
        this.tmdbAnime = false;
        this.proxy = false;
    }

    /**
     * tmdbApi
     */
    @Schema(name = "tmdbApi")
    private String tmdbApi;

    /**
     * tmdbApiKey
     */
    @Schema(name = "tmdbApiKey")
    private String tmdbApiKey;

    /**
     * tmdb 语言
     */
    @Schema(name = "tmdb 语言")
    private String tmdbLanguage;

    /**
     * 过滤出动漫
     */
    @Schema(name = "过滤出动漫")
    private Boolean tmdbAnime;

    /**
     * 代理是否开启
     */
    @Schema(name = "代理是否开启")
    private Boolean proxy;

    /**
     * 代理host
     */
    @Schema(name = "代理host")
    private String proxyHost;

    /**
     * 代理端口
     */
    @Schema(name = "代理端口")
    private Integer proxyPort;

    /**
     * 代理用户名
     */
    @Schema(name = "代理用户名")
    private String proxyUsername;

    /**
     * 代理密码
     */
    @Schema(name = "代理密码")
    private String proxyPassword;
}
