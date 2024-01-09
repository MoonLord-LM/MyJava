package cn.moonlord.test;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Enclosed.class)
public class PerformanceTestTest {

    public static Logger logger = LoggerFactory.getLogger(PerformanceTestTest.class);

    public static class run {
        @Test
        public void success_1() {
            PerformanceTest test = new PerformanceTest() {
                @Override
                public void test() {
                    long totalMemory = Runtime.getRuntime().totalMemory();
                    long freeMemory = Runtime.getRuntime().freeMemory();
                    long maxMemory = Runtime.getRuntime().maxMemory();
                    logger.info(
                        "totalMemory: {}, freeMemory: {}, maxMemory: {}",
                        totalMemory / 1024 / 1024 + " MB",
                        freeMemory / 1024 / 1024 + " MB",
                        maxMemory / 1024 / 1024 + " MB"
                    );
                    logger.info("used memory: {} MB", (totalMemory - freeMemory) / 1024 / 1024);
                }
            }.run();
        }

        @Test
        public void success_2() {
            PerformanceTest test = new PerformanceTest() {
                @Override
                public void test() {
                    int tmp = 0;
                    for (int i = 0; i < 1024 * 1024; i++) {
                        tmp += i;
                    }
                    for (int i = 0; i < 1024 * 1024; i++) {
                        tmp -= i;
                    }
                    if (tmp != 0) {
                        throw new RuntimeException("error");
                    }
                }
            }.setCycleOfRuns(10).run();
            logger.info("used total time: {} ms", test.getTotalRunTimeMs());
            logger.info("used average time: {} ms", test.getAverageRunTimeMs());
        }

        @Test(expected = RuntimeException.class)
        public void error_1() {
            PerformanceTest test = new PerformanceTest() {
                @Override
                public void test() {
                    throw new RuntimeException("error");
                }
            }.run();
        }

        @Test(expected = RuntimeException.class)
        public void error_2() {
            PerformanceTest test = new PerformanceTest() {
                @Override
                public void test() {
                    throw new RuntimeException("error");
                }
            };
            test.setCycleOfRuns(0);
        }
    }

}
