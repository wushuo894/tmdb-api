package wushuo.tmdb.api.common;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import lombok.extern.slf4j.Slf4j;
import wushuo.tmdb.api.entity.TmdbConfig;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
public class HttpRequestPlus extends HttpRequest {
    public HttpRequestPlus(UrlBuilder url) {
        super(url);
    }

    public static HttpRequestPlus of(UrlBuilder url) {
        return new HttpRequestPlus(url);
    }

    public static HttpRequestPlus of(String url) {
        return HttpRequestPlus.of(UrlBuilder.ofHttp(url, StandardCharsets.UTF_8));
    }

    public HttpRequestPlus method(Method method) {
        super.method(method);
        return this;
    }

    @Override
    public HttpResponse execute(boolean isAsync) {
        String url = getUrl();
        try {
            return super.execute(isAsync);
        } catch (Exception e) {
            log.error("url: {}, error: {}", url, e.getMessage());
            throw e;
        }
    }

    public HttpRequestPlus proxy(TmdbConfig config) {
        String url = getUrl();
        Boolean proxy = config.getProxy();
        if (!proxy) {
            log.debug("代理未开启 {}", url);
            return this;
        }

        String proxyHost = config.getProxyHost();
        Integer proxyPort = config.getProxyPort();
        if (StrUtil.isBlank(proxyHost) || Objects.isNull(proxyPort)) {
            log.debug("代理参数不全 {}", url);
            return this;
        }

        String proxyUsername = config.getProxyUsername();
        String proxyPassword = config.getProxyPassword();
        try {
            setHttpProxy(proxyHost, proxyPort);
            Authenticator.setDefault(
                    new Authenticator() {
                        @Override
                        public PasswordAuthentication getPasswordAuthentication() {
                            if (StrUtil.isAllNotBlank(proxyUsername, proxyPassword)) {
                                return new PasswordAuthentication(proxyUsername, proxyPassword.toCharArray());
                            }
                            return null;
                        }
                    }
            );
            log.debug("使用代理 {}", url);
        } catch (Exception e) {
            log.error("设置代理出现问题 {}", url);
            log.error(e.getMessage(), e);
        }
        return this;
    }
}
