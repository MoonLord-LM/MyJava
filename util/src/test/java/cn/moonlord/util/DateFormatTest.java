package cn.moonlord.util;

import com.alibaba.fastjson.JSON;
import net.minidev.json.JSONArray;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@SpringBootTest
@RunWith(Enclosed.class)
public class DateFormatTest {

    public static Logger logger = LoggerFactory.getLogger(DateFormatTest.class);

    static {
        init();
    }

    public static synchronized void init(){
        // TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }

    public static class parse {
        @Test
        public void success_1() {
            List<String> sources = Arrays.asList(
                    "1970-01-01T12:59:59.999Z",
                    "1970-01-01T12:59:59.999+08",
                    "1970-01-01T12:59:59.999+0800",
                    "1970-01-01T12:59:59.999+08:00",
                    "1970-01-01T12:59:59.999",
                    "1970-01-01T12:59:59+08",
                    "1970-01-01T12:59:59+0800",
                    "1970-01-01T12:59:59+08:00",
                    "1970-01-01T12:59:59",
                    "1970-01-01 12:59:59.999+08",
                    "1970-01-01 12:59:59.999+0800",
                    "1970-01-01 12:59:59.999+08:00",
                    "1970-01-01 12:59:59.999",
                    "1970-01-01 12:59:59+08",
                    "1970-01-01 12:59:59+0800",
                    "1970-01-01 12:59:59+08:00",
                    "1970-01-01 12:59:59",
                    "1970-01-01 12:59",
                    "1970-01-01 12",
                    "1970-01-01",
                    "19700101125959",
                    "19700101"
            );
            for (int i = 0; i < sources.size(); i++) {
                Date date = DateFormat.parse(sources.get(i));
                logger.info("date: {}", date);
            }
            for (int i = 0; i < sources.size(); i++) {
                Date date = DateFormat.parse(sources.get(i));
                logger.info("date UTC: {}", DateFormat.formatUTC(date));
            }
            // Assert.assertEquals("success_1","Thu Jan 01 20:59:59 CST 1970", date1.toString());
        }
    }

}
