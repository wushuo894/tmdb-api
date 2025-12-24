package wushuo.tmdb.api.common;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpConnection;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import cn.hutool.http.cookie.GlobalCookieManager;
import lombok.extern.slf4j.Slf4j;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;

@Slf4j
public class HttpReq {

    public static final CookieManager COOKIE_MANAGER;

    static {
        COOKIE_MANAGER = new CookieManager();
        COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
    }

    private static void config(HttpRequestPlus req) {
        GlobalCookieManager.setCookieManager(COOKIE_MANAGER);

        req.timeout(1000 * 20)
                .setFollowRedirects(true);

        String ua = "wushuo894/tmdb-api (https://github.com/wushuo894/tmdb-api)";

        req.header(Header.USER_AGENT, ua);
    }

    public static HttpRequestPlus post(String url) {
        HttpRequestPlus req = HttpRequestPlus.of(url).method(Method.POST);
        config(req);
        return req;
    }

    public static HttpRequestPlus get(String url) {
        HttpRequestPlus req = HttpRequestPlus.of(url).method(Method.GET);
        config(req);
        return req;
    }

    public static HttpRequestPlus put(String url) {
        HttpRequestPlus req = HttpRequestPlus.of(url).method(Method.PUT);
        config(req);
        return req;
    }

    public static HttpRequestPlus delete(String url) {
        HttpRequestPlus req = HttpRequestPlus.of(url).method(Method.DELETE);
        config(req);
        return req;
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
