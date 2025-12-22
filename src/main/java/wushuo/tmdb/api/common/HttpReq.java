package wushuo.tmdb.api.common;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpConnection;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.cookie.GlobalCookieManager;
import lombok.extern.slf4j.Slf4j;
import wushuo.tmdb.api.entity.TmdbConfig;

import java.net.*;
import java.util.Objects;

@Slf4j
public class HttpReq {

    public static final CookieManager COOKIE_MANAGER;

    static {
        COOKIE_MANAGER = new CookieManager();
        COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
    }

    private static void config(HttpRequest req) {
        GlobalCookieManager.setCookieManager(COOKIE_MANAGER);

        req.timeout(1000 * 20)
                .setFollowRedirects(true);

        String ua = "wushuo894/tmdb-api (https://github.com/wushuo894/tmdb-api)";

        req.header(Header.USER_AGENT, ua);
    }

    public static HttpRequest post(String url) {
        HttpRequest req = HttpRequestPlus.post(url);
        config(req);
        return req;
    }

    public static HttpRequest get(String url) {
        HttpRequest req = HttpRequestPlus.get(url);
        config(req);
        return req;
    }

    public static HttpRequest put(String url) {
        HttpRequest req = HttpRequestPlus.put(url);
        config(req);
        return req;
    }

    public static HttpRequest delete(String url) {
        HttpRequest req = HttpRequestPlus.delete(url);
        config(req);
        return req;
    }

    /**
     * 设置代理
     *
     * @param req
     * @param config
     * @return
     */
    public static void setProxy(HttpRequest req, TmdbConfig config) {
        String url = req.getUrl();
        Boolean proxy = config.getProxy();
        if (!proxy) {
            log.debug("代理未开启 {}", url);
            return;
        }

        String proxyHost = config.getProxyHost();
        Integer proxyPort = config.getProxyPort();
        if (StrUtil.isBlank(proxyHost) || Objects.isNull(proxyPort)) {
            log.debug("代理参数不全 {}", url);
            return;
        }

        String proxyUsername = config.getProxyUsername();
        String proxyPassword = config.getProxyPassword();
        try {
            req.setHttpProxy(proxyHost, proxyPort);
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
    }

    public static String getUrl(HttpResponse response) {
        URL url = ((HttpConnection) ReflectUtil.getFieldValue(response, "httpConnection")).getUrl();
        return url.toString();
    }

    public static void assertStatus(HttpResponse response) {
        boolean ok = response.isOk();
        int status = response.getStatus();
        String url = getUrl(response);
        Assert.isTrue(ok, "url: {}, status: {}", url, status);
    }

}
