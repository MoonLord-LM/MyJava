package cn.moonlord.common.time;

import org.junit.Assert;
import org.junit.BeforeClass;
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

    @BeforeClass
    public static synchronized void init() {
        logger.info("init getDefaultTimeZone: {}", TimeZone.getDefault());
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        logger.info("init setDefaultTimeZone: {}", TimeZone.getDefault());
    }

    public static class parse {
        @Test
        public void success_1() {
            List<String> sources = Arrays.asList(
                    "1970-01-01T12:59:59.999Z",
                    "1971-01-01T12:59:59.888+08",
                    "1971-01-01T12:59:59.777+0800",
                    "1971-01-01T12:59:59.666+08:00",
                    "1971-01-01T12:59:59.555",
                    "1972-01-01T12:59:59+08",
                    "1972-01-01T12:59:59+0800",
                    "1972-01-01T12:59:59+08:00",
                    "1972-01-01T12:59:59",
                    "1973-01-01 12:59:59.444+08",
                    "1973-01-01 12:59:59.333+0800",
                    "1973-01-01 12:59:59.222+08:00",
                    "1973-01-01 12:59:59.111",
                    "1974-01-01 12:59:59+08",
                    "1974-01-01 12:59:59+0800",
                    "1974-01-01 12:59:59+08:00",
                    "1974-01-01 12:59:59",
                    "1975-01-01 12:59+08",
                    "1975-01-01 12:59+0800",
                    "1975-01-01 12:59+08:00",
                    "1975-01-01 12:59",
                    "1976-01-01 12+08",
                    "1976-01-01 12+0800",
                    "1976-01-01 12+08:00",
                    "1976-01-01 12",
                    "1977-01-02",
                    "1977/01/02",
                    "1977.01.02",
                    "19780101125959123",
                    "19780101125959",
                    "197801011259",
                    "1978010112",
                    "19780102",
                    "Thu Jan 01 12:59:59 GMT+08:00 1979"
            );
            List<String> dates = Arrays.asList(
                    "1970-01-01T12:59:59.999Z",
                    "1971-01-01T04:59:59.888Z",
                    "1971-01-01T04:59:59.777Z",
                    "1971-01-01T04:59:59.666Z",
                    "1971-01-01T04:59:59.555Z",
                    "1972-01-01T04:59:59.000Z",
                    "1972-01-01T04:59:59.000Z",
                    "1972-01-01T04:59:59.000Z",
                    "1972-01-01T04:59:59.000Z",
                    "1973-01-01T04:59:59.444Z",
                    "1973-01-01T04:59:59.333Z",
                    "1973-01-01T04:59:59.222Z",
                    "1973-01-01T04:59:59.111Z",
                    "1974-01-01T04:59:59.000Z",
                    "1974-01-01T04:59:59.000Z",
                    "1974-01-01T04:59:59.000Z",
                    "1974-01-01T04:59:59.000Z",
                    "1975-01-01T04:59:00.000Z",
                    "1975-01-01T04:59:00.000Z",
                    "1975-01-01T04:59:00.000Z",
                    "1975-01-01T04:59:00.000Z",
                    "1976-01-01T04:00:00.000Z",
                    "1976-01-01T04:00:00.000Z",
                    "1976-01-01T04:00:00.000Z",
                    "1976-01-01T04:00:00.000Z",
                    "1977-01-01T16:00:00.000Z",
                    "1977-01-01T16:00:00.000Z",
                    "1977-01-01T16:00:00.000Z",
                    "1978-01-01T04:59:59.123Z",
                    "1978-01-01T04:59:59.000Z",
                    "1978-01-01T04:59:00.000Z",
                    "1978-01-01T04:00:00.000Z",
                    "1978-01-01T16:00:00.000Z",
                    "1979-01-01T04:59:59.000Z"
            );
            Assert.assertEquals(sources.size(), dates.size());
            for (int i = 0; i < sources.size(); i++) {
                Date date = DateFormat.parse(sources.get(i));
                String utcDate = DateFormat.formatUTC(date);
                if(!utcDate.equals(dates.get(i))) {
                    logger.info("source: {}, UTC: {}, expect: {}", sources.get(i), utcDate, dates.get(i));
                }
                Assert.assertEquals(utcDate, dates.get(i));
            }
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            String source = null;
            DateFormat.parse(source);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            String source = "";
            DateFormat.parse(source);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            String source = "1970年1月1日";
            Date date = DateFormat.parse(source);
        }
    }

    public static class format {
        @Test
        public void success_1() {
            Date source = DateFormat.parse("1999-12-31T04:00:00.000Z");
            Assert.assertEquals("source","Fri Dec 31 12:00:00 GMT+08:00 1999", source.toString());
            Assert.assertEquals("format","1999-12-31 12:00:00", DateFormat.format(source));
            Assert.assertEquals("formatUTC","1999-12-31T04:00:00.000Z", DateFormat.formatUTC(source));
            Assert.assertEquals("formatJava","Fri Dec 31 12:00:00 GMT+08:00 1999", DateFormat.formatJava(source));
            Assert.assertEquals("formatSimple","1999-12-31", DateFormat.formatSimple(source));
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            DateFormat.format(null, "yyyy年mm月dd日");
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            DateFormat.format(new Date(), null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            DateFormat.format(new Date(), "");
        }
    }

}
