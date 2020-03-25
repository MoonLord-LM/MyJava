package cn.moonlord.test;

import cn.moonlord.http.M3U8VideoDownloader;

public class M3U8VideoDownloaderTest {

    public static void test1() {
        String url = "http://t2.cdn2020.com:12345/video/m3u8/2019/07/25/58fdc83b/index.m3u8";
        String refer = "http://thzx.cc/forum.php?mod=viewthread&tid=2098770&extra=page%3D2%26filter%3Dtypeid%26typeid%3D735";
        String filePath = "out/video.ts";
        new M3U8VideoDownloader(url, refer, filePath);
    }

}
