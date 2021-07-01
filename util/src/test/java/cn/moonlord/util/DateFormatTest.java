package cn.moonlord.util;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Date;

@SpringBootTest
@RunWith(Enclosed.class)
public class DateFormatTest {

    public static Logger logger = LoggerFactory.getLogger(DateFormatTest.class);

    public static class parse {
        @Test
        public void success_1() {
            String source1 = "1970-01-01";
            String source2 = "1970-01-01T23:59:59";
            String source3 = "1970-01-01T23:59:59.999";
            String source4 = "1970-01-01T23:59:59-0800";
            String source5 = "1970-01-01T23:59:59-08:00";
            String source6 = "1970-01-01T23:59:59.999-0800";
            String source7 = "1970-01-01T23:59:59.999-08:00";
            Date date1 = DateFormat.parse(source1);
            Date date2 = DateFormat.parse(source2);
            Date date3 = DateFormat.parse(source3);
            Date date4 = DateFormat.parse(source4);
            Date date5 = DateFormat.parse(source5);
            Date date6 = DateFormat.parse(source6);
            Date date7 = DateFormat.parse(source7);
            logger.info("source1: {}", new Timestamp(date1.getTime()));
            logger.info("source2: {}", DateFormat.format(date2));
            logger.info("source3: {}", DateFormat.format(date3));
            logger.info("source4: {}", DateFormat.format(date4));
            logger.info("source5: {}", DateFormat.format(date5));
            logger.info("source6: {}", DateFormat.format(date6));
            logger.info("source7: {}", DateFormat.format(date7));
        }
    }

}
