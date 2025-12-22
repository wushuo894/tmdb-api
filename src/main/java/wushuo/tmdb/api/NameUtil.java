package wushuo.tmdb.api;

import cn.hutool.core.util.StrUtil;

import java.util.Map;

public class NameUtil {
    public static String getName(String s) {
        if (StrUtil.isBlank(s)) {
            return "";
        }

        s = s.replace("1/2", "½");

        Map<String, String> map = Map.of(
                "/", " ",
                "\\", " ",
                ":", "：",
                "?", "？",
                "|", "｜",
                "*", " ",
                "<", " ",
                ">", " ",
                "\"", " "
        );

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            s = s.replace(key, value);
        }
        while (s.contains("  ")) {
            s = s.replace("  ", " ");
        }
        return s.trim();
    }
}
