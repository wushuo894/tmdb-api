import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import wushuo.tmdb.api.TmdbUtil;
import wushuo.tmdb.api.common.GsonStatic;
import wushuo.tmdb.api.entity.Tmdb;
import wushuo.tmdb.api.entity.TmdbConfig;
import wushuo.tmdb.api.enums.TmdbTypeEnum;

import java.util.List;

@Slf4j
public class TestTmdb {

    @Test
    public void test() {
        TmdbConfig config = new TmdbConfig("450e4f651e1c93e31383e20f8e731e5f");
        TmdbUtil tmdbUtil = new TmdbUtil(config);
        List<Tmdb> tmdbList = tmdbUtil.search("从零开始的", TmdbTypeEnum.TV);
        for (Tmdb tmdb : tmdbList) {
            log.info("{}", GsonStatic.toJson(tmdb));
        }
    }
}
