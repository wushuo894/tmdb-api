package wushuo.tmdb.api.entity;

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
    private String tmdbApi;

    /**
     * tmdbApiKey
     */
    private String tmdbApiKey;

    /**
     * tmdb 语言
     */
    private String tmdbLanguage;

    /**
     * 过滤出动漫
     */
    private Boolean tmdbAnime;

    /**
     * 代理是否开启
     */
    private Boolean proxy;

    /**
     * 代理host
     */
    private String proxyHost;

    /**
     * 代理端口
     */
    private Integer proxyPort;

    /**
     * 代理用户名
     */
    private String proxyUsername;

    /**
     * 代理密码
     */
    private String proxyPassword;
}
