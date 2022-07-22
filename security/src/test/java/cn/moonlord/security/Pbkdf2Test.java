package cn.moonlord.security;

import cn.moonlord.test.PerformanceTest;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Enclosed.class)
public class Pbkdf2Test {

    public static Logger logger = LoggerFactory.getLogger(Pbkdf2Test.class);

    public static class generate {
        @Test
        public void success_1() {
            byte[] component1 = Random.generate(512);
            byte[] component2 = Random.generate(512);
            byte[] password = Xor.merge(component1, component2);
            byte[] salt = Random.generate(512);
            byte[] result = Pbkdf2.generate(Hex.encode(password), salt, 10000, 512);
            logger.info("component1 byte length: [ " + component1.length + " ], base64: [ " + Base64.encode(component1) + " ]");
            logger.info("component2 byte length: [ " + component2.length + " ], base64: [ " + Base64.encode(component2) + " ]");
            logger.info("password byte length: [ " + password.length + " ], base64: [ " + Base64.encode(password) + " ]");
            logger.info("salt byte length: [ " + salt.length + " ], base64: [ " + Base64.encode(salt) + " ]");
            logger.info("result byte length: [ " + result.length + " ], base64: [ " + Base64.encode(result) + " ]");
        }

        @Test
        public void performance_1() {
            byte[] component1 = Random.generate(512);
            byte[] component2 = Random.generate(512);
            byte[] password = Xor.merge(component1, component2);
            byte[] salt = Random.generate(512);
            int iterationCount = 10000;
            int keyLength = 512;
            byte[] tmp = new byte[64];
            new PerformanceTest(100) {
                @Override
                public void onStarted() {
                    logger.info("begin generate");
                }

                @Override
                public void testMethod() {
                    Xor.merge(tmp, Pbkdf2.generate(password, salt, iterationCount, keyLength));
                }

                @Override
                public void onFinished() {
                    logger.info("iterationCount: {}, run cycle: {}, cost total time: {} ms, average time: {} ms", iterationCount, getCycleOfRuns(), getTestMethodTotalRunTime(), getTestMethodAverageRunTime());
                }
            }.run();
        }

        @Test
        public void performance_2() {
            byte[] component1 = Random.generate(512);
            byte[] component2 = Random.generate(512);
            byte[] password = Xor.merge(component1, component2);
            byte[] salt = Random.generate(512);
            int iterationCount = 10000 * 10;
            int keyLength = 512;
            byte[] tmp = new byte[64];
            new PerformanceTest(10) {
                @Override
                public void onStarted() {
                    logger.info("begin generate");
                }

                @Override
                public void testMethod() {
                    Xor.merge(tmp, Pbkdf2.generate(password, salt, iterationCount, keyLength));
                }

                @Override
                public void onFinished() {
                    logger.info("iterationCount: {}, run cycle: {}, cost total time: {} ms, average time: {} ms", iterationCount, getCycleOfRuns(), getTestMethodTotalRunTime(), getTestMethodAverageRunTime());
                }
            }.run();
        }

        @Test
        public void performance_3() {
            byte[] component1 = Random.generate(512);
            byte[] component2 = Random.generate(512);
            byte[] password = Xor.merge(component1, component2);
            byte[] salt = Random.generate(512);
            int iterationCount = 10000 * 100;
            int keyLength = 512;
            byte[] tmp = new byte[64];
            new PerformanceTest(1) {
                @Override
                public void onStarted() {
                    logger.info("begin generate");
                }

                @Override
                public void testMethod() {
                    Xor.merge(tmp, Pbkdf2.generate(password, salt, iterationCount, keyLength));
                }

                @Override
                public void onFinished() {
                    logger.info("iterationCount: {}, run cycle: {}, cost total time: {} ms, average time: {} ms", iterationCount, getCycleOfRuns(), getTestMethodTotalRunTime(), getTestMethodAverageRunTime());
                }
            }.run();
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Pbkdf2.generate((char[]) null, new byte[Pbkdf2.SALT_MIN_BYTE_LENGTH], Pbkdf2.ITERATION_MIN_COUNT, Pbkdf2.OUTPUT_KEY_MIN_BYTE_LENGTH);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            Pbkdf2.generate(new char[0], new byte[Pbkdf2.SALT_MIN_BYTE_LENGTH], Pbkdf2.ITERATION_MIN_COUNT, Pbkdf2.OUTPUT_KEY_MIN_BYTE_LENGTH);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            Pbkdf2.generate("测试".toCharArray(), null, Pbkdf2.ITERATION_MIN_COUNT, Pbkdf2.OUTPUT_KEY_MIN_BYTE_LENGTH);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_4() {
            Pbkdf2.generate("测试".toCharArray(), new byte[Pbkdf2.SALT_MIN_BYTE_LENGTH - 1], Pbkdf2.ITERATION_MIN_COUNT, Pbkdf2.OUTPUT_KEY_MIN_BYTE_LENGTH);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_5() {
            Pbkdf2.generate("测试".toCharArray(), new byte[Pbkdf2.SALT_MIN_BYTE_LENGTH], Pbkdf2.ITERATION_MIN_COUNT - 1, Pbkdf2.OUTPUT_KEY_MIN_BYTE_LENGTH);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_6() {
            Pbkdf2.generate("测试".toCharArray(), new byte[Pbkdf2.SALT_MIN_BYTE_LENGTH], Pbkdf2.ITERATION_MIN_COUNT, Pbkdf2.OUTPUT_KEY_MIN_BYTE_LENGTH - 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_7() {
            Pbkdf2.generate((String) null, new byte[64], Pbkdf2.ITERATION_MIN_COUNT, Pbkdf2.OUTPUT_KEY_MIN_BYTE_LENGTH);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_8() {
            Pbkdf2.generate((byte[]) null, new byte[64], Pbkdf2.ITERATION_MIN_COUNT, Pbkdf2.OUTPUT_KEY_MIN_BYTE_LENGTH);
        }
    }

}
