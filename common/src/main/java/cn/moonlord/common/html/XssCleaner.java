package cn.moonlord.common.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

import java.nio.charset.StandardCharsets;

public class XssCleaner {

    public static String clean(String source) {
        if(source == null){
            throw new IllegalArgumentException("XssCleaner clean error, source must not be null");
        }
        String baseUri = "";
        Safelist safelist = Safelist.relaxed();
        Document.OutputSettings outputSettings = new Document.OutputSettings().charset(StandardCharsets.UTF_8).prettyPrint(false);
        return Jsoup.clean(source, baseUri, safelist, outputSettings);
    }

    public static boolean isValid(String source) {
        if(source == null){
            throw new IllegalArgumentException("XssCleaner isValid error, source must not be null");
        }
        Safelist safelist = Safelist.relaxed();
        return Jsoup.isValid(source, safelist);
    }

}
