package cn.moonlord.common.exception;

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
            logger.info("getSafeMessage success_1 message: {}", message);
            Assert.assertEquals("success_1", "", message);
        }

        @Test
        public void success_2() {
            Exception throwable = new IllegalArgumentException("测试异常");
            String message = ExceptionInfo.getSafeMessage(throwable);
            logger.info("getSafeMessage success_2 message: {}", message);
            Assert.assertEquals("success_2", "测试异常", message);
        }

        @Test
        public void success_3() {
            Exception throwable = new FileNotFoundException("敏感异常");
            String message = ExceptionInfo.getSafeMessage(throwable);
            logger.info("getSafeMessage success_3 message: {}", message);
            Assert.assertEquals("success_3", ExceptionInfo.SENSITIVE_EXCEPTION_MESSAGE, message);
        }
    }

    public static class getFullSafeMessage {
        @Test
        public void success_1() {
            Exception throwable = new IllegalArgumentException();
            String message = ExceptionInfo.getFullSafeMessage(throwable);
            logger.info("getFullSafeMessage success_1 message: {}", message);
            Assert.assertEquals("success_1", "", message);
        }

        @Test
        public void success_2() {
            Exception throwable1 = new IllegalArgumentException("测试异常1");
            Exception throwable2 = new IllegalArgumentException("测试异常2", throwable1);
            Exception throwable3 = new IllegalArgumentException("测试异常3", throwable2);
            String message = ExceptionInfo.getFullSafeMessage(throwable3);
            logger.info("getFullSafeMessage success_2 message: {}", message);
            Assert.assertEquals("success_2", "测试异常3 / 测试异常2 / 测试异常1", message);
        }

        @Test
        public void success_3() {
            Exception throwable1 = new FileNotFoundException("敏感异常1");
            Exception throwable2 = new IllegalArgumentException("测试异常2", throwable1);
            Exception throwable3 = new IllegalArgumentException("测试异常3", throwable2);
            String message = ExceptionInfo.getFullSafeMessage(throwable3);
            logger.info("getFullSafeMessage success_3 message: {}", message);
            Assert.assertEquals("success_3", "测试异常3 / 测试异常2 / " + ExceptionInfo.SENSITIVE_EXCEPTION_MESSAGE, message);
        }
    }

    public static class getStackTrace {
        @Test
        public void success_1() {
            Exception throwable = new IllegalArgumentException();
            String trace = ExceptionInfo.getStackTrace(throwable);
            logger.info("getStackTrace success_1 trace: \n{}", trace);
        }

        @Test
        public void success_2() {
            Exception throwable1 = new IllegalArgumentException("测试异常1");
            Exception throwable2 = new IllegalArgumentException("测试异常2", throwable1);
            Exception throwable3 = new IllegalArgumentException("测试异常3", throwable2);
            String trace = ExceptionInfo.getStackTrace(throwable3);
            logger.info("getStackTrace success_2 trace: \n{}", trace);
        }

        @Test
        public void success_3() {
            Exception throwable1 = new FileNotFoundException("敏感异常1");
            Exception throwable2 = new IllegalArgumentException("测试异常2", throwable1);
            Exception throwable3 = new IllegalArgumentException("测试异常3", throwable2);
            String trace = ExceptionInfo.getStackTrace(throwable3);
            logger.info("getStackTrace success_3 trace: \n{}", trace);
        }
    }

    public static class getFullStackTrace {
        @Test
        public void success_1() {
            Exception throwable = new IllegalArgumentException();
            String trace = ExceptionInfo.getFullStackTrace(throwable);
            logger.info("getFullStackTrace success_1 trace: \n{}", trace);
        }

        @Test
        public void success_2() {
            Exception throwable1 = new IllegalArgumentException("测试异常1");
            Exception throwable2 = new IllegalArgumentException("测试异常2", throwable1);
            Exception throwable3 = new IllegalArgumentException("测试异常3", throwable2);
            String trace = ExceptionInfo.getFullStackTrace(throwable3);
            logger.info("getFullStackTrace success_2 trace: \n{}", trace);
        }

        @Test
        public void success_3() {
            Exception throwable1 = new FileNotFoundException("敏感异常1");
            Exception throwable2 = new IllegalArgumentException("测试异常2", throwable1);
            Exception throwable3 = new IllegalArgumentException("测试异常3", throwable2);
            String trace = ExceptionInfo.getFullStackTrace(throwable3);
            logger.info("getFullStackTrace success_3 trace: \n{}", trace);
        }
    }

}
