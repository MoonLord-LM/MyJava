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
                long totalMemory = Runtime.getRuntime().totalMemory();
                long freeMemory = Runtime.getRuntime().freeMemory();
                long maxMemory = Runtime.getRuntime().maxMemory();
                logger.info(
                    "TestRunnableExample totalMemory: {}, freeMemory: {}, maxMemory: {}",
                    totalMemory / 1024 / 1024 + " MB",
                    freeMemory / 1024 / 1024 + " MB",
                    maxMemory / 1024 / 1024 + " MB"
                );
                return (double) (totalMemory - freeMemory) / (double) maxMemory;
            };
            logger.info("TestRunnableExample used memory: " + result.run());
        }
    }

    public static class PerformanceTestExample {
        @Test
        public void success_1() throws Exception {
            PerformanceTest result = new PerformanceTest() {
                @Override
                public void testMethod() {
                    logger.info("PerformanceTestExample freeMemory: " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + " MB");
                    logger.info("PerformanceTestExample totalMemory: " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + " MB");
                    logger.info("PerformanceTestExample maxMemory: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + " MB");
                }
            };
            logger.info("PerformanceTestExample getTestMethodTotalRunTime: " + result.getTestMethodAverageRunTime());
            logger.info("PerformanceTestExample getTestMethodTotalRunTime: " + result.getTestMethodTotalRunTime());
        }
    }

    public static class PerformanceCompareTestExample {
        @Test
        public void success_1() throws Exception {
            PerformanceCompareTest result = new PerformanceCompareTest(10,
                new TestRunnable() {
                    @Override
                    public Object run() throws Exception {
                        logger.info("PerformanceCompareTestExample freeMemory: " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + " MB");
                        logger.info("PerformanceCompareTestExample totalMemory: " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + " MB");
                        logger.info("PerformanceCompareTestExample maxMemory: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + " MB");
                        return null;
                    }
                },
                new TestRunnable() {
                    @Override
                    public Object run() throws Exception {
                        logger.info("PerformanceCompareTestExample freeMemory: " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + " MB");
                        logger.info("PerformanceCompareTestExample totalMemory: " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + " MB");
                        logger.info("PerformanceCompareTestExample maxMemory: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + " MB");
                        return null;
                    }
                }) {
            };
            result.run();
            logger.info("PerformanceCompareTestExample isImproved: " + result.isImproved());
            logger.info("PerformanceCompareTestExample getImprovement: " + result.getImprovement());
        }
    }

}
