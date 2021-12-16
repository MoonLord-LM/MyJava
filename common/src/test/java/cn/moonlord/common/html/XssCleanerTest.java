package cn.moonlord.common.html;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Enclosed.class)
public class XssCleanerTest {

    public static Logger logger = LoggerFactory.getLogger(XssCleanerTest.class);

    public static class clean {
        @Test
        public void success_1() {
            String source1 = "<div><p><a href='javascript:void(0)' onclick='stealCookies()'>Link</a></p></div>";
            String source2 = "<div><p><a>Link</a></p></div>";
            String result1 = XssCleaner.clean(source1);
            String result2 = XssCleaner.clean(source2);
            logger.info("clean success_1 result1: {}", result1);
            logger.info("clean success_1 result2: {}", result2);
            Assert.assertEquals(source2, result1);
            Assert.assertEquals(source2, result2);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            XssCleaner.clean(null);
        }
    }

    public static class isValid {
        @Test
        public void success_1() {
            String source1 = "<div><p><a href='javascript:void(0)' onclick='stealCookies()'>Link</a></p></div>";
            String source2 = "<div><p><a>Link</a></p></div>";
            boolean result1 = ! ( XssCleaner.isValid(source1) );
            boolean result2 = XssCleaner.isValid(source2);
            Assert.assertTrue("success_1", result1);
            Assert.assertTrue("success_1", result2);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            XssCleaner.isValid(null);
        }
    }

}
