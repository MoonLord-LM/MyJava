package cn.moonlord.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;

@SpringBootTest
@RunWith(Enclosed.class)
public class ExceptionInfoTest {

    public static Logger logger = LoggerFactory.getLogger(ExceptionInfoTest.class);

    public static class getSafeMessage {
        @Test
        public void success_1() {
            Exception throwable = new IllegalArgumentException();
            String message = ExceptionInfo.getSafeMessage(throwable);
            Assert.assertEquals("success_1", "", message);
        }

        @Test
        public void success_2() {
            Exception throwable = new IllegalArgumentException("测试异常");
            String message = ExceptionInfo.getSafeMessage(throwable);
            Assert.assertEquals("success_2", "测试异常", message);
        }

        @Test
        public void success_3() {
            Exception throwable = new FileNotFoundException("敏感异常");
            String message = ExceptionInfo.getSafeMessage(throwable);
            Assert.assertEquals("success_3", ExceptionInfo.SENSITIVE_EXCEPTION_MESSAGE, message);
        }
    }

    public static class getFullSafeMessage {
        @Test
        public void success_1() {
            Exception throwable = new IllegalArgumentException();
            String message = ExceptionInfo.getFullSafeMessage(throwable);
            Assert.assertEquals("success_1", "", message);
        }

        @Test
        public void success_2() {
            Exception throwable1 = new IllegalArgumentException("测试异常1");
            Exception throwable2 = new IllegalArgumentException("测试异常2", throwable1);
            Exception throwable3 = new IllegalArgumentException("测试异常3", throwable2);
            String message = ExceptionInfo.getFullSafeMessage(throwable3);
            Assert.assertEquals("success_2", "测试异常3 / 测试异常2 / 测试异常1", message);
        }

        @Test
        public void success_3() {
            Exception throwable1 = new FileNotFoundException("敏感异常1");
            Exception throwable2 = new IllegalArgumentException("测试异常2", throwable1);
            Exception throwable3 = new IllegalArgumentException("测试异常3", throwable2);
            String message = ExceptionInfo.getFullSafeMessage(throwable3);
            Assert.assertEquals("success_3", "测试异常3 / 测试异常2 / " + ExceptionInfo.SENSITIVE_EXCEPTION_MESSAGE, message);
        }
    }

    public static class getStackTrace {
        @Test
        public void success_1() {
            Exception throwable = new IllegalArgumentException();
            String trace = ExceptionInfo.getStackTrace(throwable);
            logger.info("success_1 trace: {}", trace);
        }

        @Test
        public void success_2() {
            Exception throwable1 = new IllegalArgumentException("测试异常1");
            Exception throwable2 = new IllegalArgumentException("测试异常2", throwable1);
            Exception throwable3 = new IllegalArgumentException("测试异常3", throwable2);
            String trace = ExceptionInfo.getStackTrace(throwable3);
            logger.info("success_2 trace: {}", trace);
        }
    }

}
