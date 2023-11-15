package cn.moonlord.test;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Enclosed.class)
public class Example {

    public static Logger logger = LoggerFactory.getLogger(Example.class);

    public static class TestRunnableExample {
        @Test
        public void success_1() throws Exception {
            TestRunnable result = () -> {
                logger.info("freeMemory: " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + " MB");
                logger.info("totalMemory: " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + " MB");
                logger.info("maxMemory: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + " MB");
                return (double) Runtime.getRuntime().freeMemory() / (double) Runtime.getRuntime().maxMemory();
            };
            logger.info("freeMemory / maxMemory: " + result.run());
        }
    }

    public static class PerformanceTestExample {
        @Test
        public void success_1() throws Exception {
            PerformanceTest result = new PerformanceTest() {
                @Override
                public void testMethod() {
                    logger.info("freeMemory: " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + " MB");
                    logger.info("totalMemory: " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + " MB");
                    logger.info("maxMemory: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + " MB");
                }
            };
            logger.info("getTestMethodTotalRunTime: " + result.getTestMethodTotalRunTime());
        }
    }

    public static class PerformanceCompareTestExample {
        @Test
        public void success_1() throws Exception {
            PerformanceCompareTest result = new PerformanceCompareTest(1,
                new TestRunnable() {
                    @Override
                    public Object run() throws Exception {
                        logger.info("freeMemory: " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + " MB");
                        logger.info("totalMemory: " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + " MB");
                        logger.info("maxMemory: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + " MB");
                        return null;
                    }
                },
                new TestRunnable() {
                    @Override
                    public Object run() throws Exception {
                        logger.info("freeMemory: " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + " MB");
                        logger.info("totalMemory: " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + " MB");
                        logger.info("maxMemory: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + " MB");
                        return null;
                    }
                }) {
            };
            result.run();
            logger.info("getImprovement: " + result.getImprovement());
        }
    }

}
