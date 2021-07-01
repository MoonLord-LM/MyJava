package cn.moonlord.http;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Enclosed.class)
public class HLSDownloaderTest {

    public static Logger logger = LoggerFactory.getLogger(HLSDownloaderTest.class);

    public static class run {
        @Test
        public void success_1() {
            (new HLSDownloader(
                    "http://v.vvshi.top/videos/60b43979ac357ae01c47393d/index.m3u8",
                    "http://www.mmnn.bid/index.php/vod/play/id/2387/sid/1/nid/1.html",
                    "target/斗鱼学妹enen - 睡觉之前用振动模式哦/"
            )).run();
        }

        @Test
        public void success_2() {
            (new HLSDownloader(
                    "http://v.vvshi.top/videos/60b445ceac357ae01c47393e/index.m3u8",
                    "http://www.mmnn.bid/index.php/vod/play/id/2385/sid/1/nid/1.html",
                    "target/斗鱼学妹enen - 福利小护士/"
            )).run();
        }

        @Test
        public void success_3() {
            (new HLSDownloader(
                    "http://v.vvshi.top/videos/60b75639f40af736ad4ee78b/index.m3u8",
                    "http://www.mmnn.bid/index.php/vod/play/id/2380/sid/1/nid/1.html",
                    "target/斗鱼学妹enen - 火箭大尺度福利/"
            )).run();
        }

        @Test
        public void success_4() {
            (new HLSDownloader(
                    "http://v.vvshi.top/videos/60afa9a19a56178e6d8dbf42/index.m3u8",
                    "http://www.mmnn.bid/index.php/vod/play/id/2350/sid/1/nid/1.html",
                    "target/斗鱼学妹enen - 手撕丝袜福利/"
            )).run();
        }
    }

}
