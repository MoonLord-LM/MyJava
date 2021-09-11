package cn.moonlord.time;

import org.junit.Assert;
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
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }

    public static class parse {
        @Test
        public void success_1() {
            List<String> sources = Arrays.asList(
                    "1970-01-01T12:59:59.999Z",
                    "1971-01-01T12:59:59.999+08",
                    "1971-01-01T12:59:59.999+0800",
                    "1971-01-01T12:59:59.999+08:00",
                    "1971-01-01T12:59:59.999",
                    "1972-01-01T12:59:59+08",
                    "1972-01-01T12:59:59+0800",
                    "1972-01-01T12:59:59+08:00",
                    "1972-01-01T12:59:59",
                    "1973-01-01 12:59:59.999+08",
                    "1973-01-01 12:59:59.999+0800",
                    "1973-01-01 12:59:59.999+08:00",
                    "1973-01-01 12:59:59.999",
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
                    "1977-01-01",
                    "1977/01/01",
                    "1977.01.01",
                    "19780101125959999",
                    "19780101125959",
                    "197801011259",
                    "1978010112",
                    "19780101",
                    "Thu Jan 01 20:59:59 GMT+08:00 1979"
            );
            List<String> dates = Arrays.asList(
                    "1970-01-01T20:59:59.999Z",
                    "1971-01-01T12:59:59.999Z",
                    "1971-01-01T12:59:59.999Z",
                    "1971-01-01T12:59:59.999Z",
                    "1971-01-01T12:59:59.999Z",
                    "1972-01-01T12:59:59.000Z",
                    "1972-01-01T12:59:59.000Z",
                    "1972-01-01T12:59:59.000Z",
                    "1972-01-01T12:59:59.000Z",
                    "1973-01-01T12:59:59.999Z",
                    "1973-01-01T12:59:59.999Z",
                    "1973-01-01T12:59:59.999Z",
                    "1973-01-01T12:59:59.999Z",
                    "1974-01-01T12:59:59.000Z",
                    "1974-01-01T12:59:59.000Z",
                    "1974-01-01T12:59:59.000Z",
                    "1974-01-01T12:59:59.000Z",
                    "1975-01-01T12:59:00.000Z",
                    "1975-01-01T12:59:00.000Z",
                    "1975-01-01T12:59:00.000Z",
                    "1975-01-01T12:59:00.000Z",
                    "1976-01-01T12:00:00.000Z",
                    "1976-01-01T12:00:00.000Z",
                    "1976-01-01T12:00:00.000Z",
                    "1976-01-01T12:00:00.000Z",
                    "1977-01-01T00:00:00.000Z",
                    "1977-01-01T00:00:00.000Z",
                    "1977-01-01T00:00:00.000Z",
                    "1978-01-01T12:59:59.999Z",
                    "1978-01-01T12:59:59.000Z",
                    "1978-01-01T12:59:00.000Z",
                    "1978-01-01T12:00:00.000Z",
                    "1978-01-01T00:00:00.000Z",
                    "1979-01-01T12:59:59.000Z"
            );
            Assert.assertEquals("success_1",sources.size(), dates.size());
            for (int i = 0; i < sources.size(); i++) {
                Date date = DateFormat.parse(sources.get(i));
                String utcDate = DateFormat.formatUTC(date);
                logger.info("date UTC: {}", utcDate);
                Assert.assertEquals("success_1",utcDate, dates.get(i));
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
            String source = "1970-01-01T12:59:59.99Z";
            DateFormat.parse(source);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_4() {
            String source = "1970-01-01T12:59:59.9999Z";
            DateFormat.parse(source);
        }
    }

}
