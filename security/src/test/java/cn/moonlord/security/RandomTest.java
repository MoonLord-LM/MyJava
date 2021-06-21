package cn.moonlord.security;

import cn.moonlord.test.PerformanceCompare;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;

@SpringBootTest
@RunWith(Enclosed.class)
public class RandomTest {

    public static Logger logger = LoggerFactory.getLogger(RandomTest.class);

    public static class getInstance {
        @Test
        public void success_1() throws Exception {
            SecureRandom instance = Random.getInstance();
            logger.info("instance: " + instance);
            logger.info("instance algorithm: " + instance.getAlgorithm());
            logger.info("instance provider: " + instance.getProvider().getName());
            SecureRandom defaultInstance = new SecureRandom();
            logger.info("default instance: " + defaultInstance);
            logger.info("default instance algorithm: " + defaultInstance.getAlgorithm());
            logger.info("default instance provider: " + defaultInstance.getProvider().getName());
            SecureRandom strongInstance = SecureRandom.getInstanceStrong();
            logger.info("strong instance: " + strongInstance);
            logger.info("strong instance algorithm: " + strongInstance.getAlgorithm());
            logger.info("strong instance provider: " + strongInstance.getProvider().getName());
        }

        @Test
        public void performance_1() {
            SecureRandom instance = Random.getInstance();
            SecureRandom defaultInstance = new SecureRandom();
            new PerformanceCompare(256) {
                @Override
                public void testMethod() {
                    instance.nextBytes(new byte[4096]);
                }
                @Override
                public void compareMethod() {
                    defaultInstance.nextBytes(new byte[4096]);
                }
                @Override
                public void onCompleted() {
                    logger.info("[instance] cost time: {} ms", getTestMethodRunTime());
                    logger.info("[defaultInstance] compare time: {} ms", getCompareMethodRunTime());
                    logger.info("[instance] is {} faster than [defaultInstance]", getImprovement());
                    Assert.assertTrue("performance_1", getImprovementPercentage() > -10);
                }
            }.run();
        }

        @Test
        public void performance_2() throws Exception {
            SecureRandom instance = Random.getInstance();
            SecureRandom strongInstance = SecureRandom.getInstanceStrong();
            new PerformanceCompare(256) {
                @Override
                public void testMethod() {
                    instance.nextBytes(new byte[4096]);
                }
                @Override
                public void compareMethod() {
                    strongInstance.nextBytes(new byte[4096]);
                }
                @Override
                public void onCompleted() {
                    logger.info("[instance] cost time: {} ms", getTestMethodRunTime());
                    logger.info("[strongInstance] compare time: {} ms", getCompareMethodRunTime());
                    logger.info("[instance] is {} faster than [strongInstance]", getImprovement());
                    Assert.assertTrue("performance_1", isImproved());
                }
            }.run();
        }
    }

    public static class generate {
        @Test
        public void success_1() {
            byte[] result = Random.generate(8);
            Assert.assertEquals("success_1", 1, result.length);
        }

        @Test
        public void success_2() {
            byte[] result = Random.generate(8 * 1024);
            Assert.assertEquals("success_2", 1024, result.length);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Random.generate(0);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            Random.generate(-1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            Random.generate(1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_4() {
            Random.generate(1024 + 1);
        }
    }

    public static class generateBytes {
        @Test
        public void success_1() {
            byte[] result = Random.generateBytes(1);
            Assert.assertEquals("success_1", 1, result.length);
        }

        @Test
        public void success_2() {
            byte[] result = Random.generateBytes(1024);
            Assert.assertEquals("success_2", 1024, result.length);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Random.generateBytes(0);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            Random.generateBytes(-1);
        }
    }

}
