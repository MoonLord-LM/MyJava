package cn.moonlord.test;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Enclosed.class)
public class ExampleTest {

    public static Logger logger = LoggerFactory.getLogger(ExampleTest.class);

    public static class ExceptionRunnableTest {
        @Test
        public void success_1() {
            Runnable result = () -> {
                long totalMemory = Runtime.getRuntime().totalMemory();
                long freeMemory = Runtime.getRuntime().freeMemory();
                long maxMemory = Runtime.getRuntime().maxMemory();
                logger.info(
                        "totalMemory: {}, freeMemory: {}, maxMemory: {}",
                        totalMemory / 1024 / 1024 + " MB",
                        freeMemory / 1024 / 1024 + " MB",
                        maxMemory / 1024 / 1024 + " MB"
                );
                logger.info("used memory: {}", (totalMemory - freeMemory) / 1024 / 1024 + " MB");
            };
        }
    }

    public static class PerformanceTestTest {
        @Test
        public void success_1() {
            PerformanceTest result = new PerformanceTest(10, new ExceptionRunnable() {
                @Override
                public void run() {
                    int sum = 0;
                    for (int i = 0; i < 10000; i++) {
                        sum += i;
                    }
                    logger.info("sum: {}", sum);
                }
            });
            logger.info("used time: " + result.getAverageRunTime());
        }
    }

}
