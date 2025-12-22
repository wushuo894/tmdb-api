package wushuo.tmdb.api;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Map;

public class NameUtil {
    public static final String YEAR_REG = " ?\\(((19|20)\\d{2})\\)";
    public static final String TMDB_ID_REG = " ?(\\[tmdbid=(\\d+)]|\\{tmdb-(\\d+)})";

    public static String nameDel(String titleName) {
        if (StrUtil.isBlank(titleName)) {
            return "";
        }

        titleName = ReUtil.replaceAll(titleName, TMDB_ID_REG, "");
        titleName = ReUtil.replaceAll(titleName, YEAR_REG, "");
        titleName = titleName.trim();
        return titleName;
    }

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
