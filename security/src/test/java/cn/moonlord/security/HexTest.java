package cn.moonlord.security;

import cn.moonlord.test.PerformanceCompareTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest
@RunWith(Enclosed.class)
public class HexTest {

    public static Logger logger = LoggerFactory.getLogger(HexTest.class);

    public static class encode {

        @Test
        public void success_1() {
            byte[] source = new byte[0];
            String result = Hex.encode(source);
            Assert.assertEquals("", result);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0xDD, (byte) 0x11, (byte) 0xEE, (byte) 0x22, (byte) 0xFF};
            String result = Hex.encode(source);
            Assert.assertEquals("00dd11ee22ff", result);
        }

        @Test
        public void success_3() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
            String result = Hex.encode(source);
            Assert.assertEquals("000000000000", result);
        }

        @Test
        public void success_4() {
            byte[] source = "测试".getBytes(StandardCharsets.UTF_8);
            String result = Hex.encode(source);
            Assert.assertEquals("e6b58be8af95", result);
        }

        @Test
        public void performance_1() {
            byte[] source = Random.generateBytes(1024 * 10);
            new PerformanceCompareTest(1024 * 10) {
                @Override
                public void testMethod() {
                    Hex.encode(source);
                }

                @Override
                public void compareMethod() {
                    org.apache.commons.codec.binary.Hex.encodeHexString(source);
                }

                @Override
                public void onFinished() {
                    logger.info("[Hex.encode] cost time: {} ms", getTestMethodTotalRunTime());
                    logger.info("[Hex.encodeHexString] compare time: {} ms", getCompareMethodTotalRunTime());
                    logger.info("[Hex.encode] of this project is {} faster than [Hex.encodeHexString] of Apache Commons Codec", getImprovement());
                    Assert.assertTrue("performance_1", isImproved());
                    Assert.assertTrue("performance_1", getImprovedPercentage() > -10L);
                }
            }.run();
        }

        @Test
        public void performance_2() {
            byte[] source = Random.generateBytes(1024 * 10);
            new PerformanceCompareTest(1024 * 10) {
                @Override
                public void testMethod() {
                    Hex.encode(source);
                }

                @Override
                public void compareMethod() {
                    new String(org.springframework.security.crypto.codec.Hex.encode(source));
                }

                @Override
                public void onFinished() {
                    logger.info("[Hex.encode] cost time: {} ms", getTestMethodTotalRunTime());
                    logger.info("[Hex.encode] compare time: {} ms", getCompareMethodTotalRunTime());
                    logger.info("[Hex.encode] of this project is {} faster than [Hex.encode] of Spring Security", getImprovement());
                    Assert.assertTrue("performance_2", getImprovedPercentage() > -10L);
                }
            }.run();
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hex.encode(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            byte[] source = new byte[Hex.SOURCE_MAX_BYTE_LENGTH + 1];
            Hex.encode(source);
        }
    }

    public static class decode {

        @Test
        public void success_1() {
            byte[] result = Hex.decode("");
            Assert.assertEquals(0, result.length);
        }

        @Test
        public void success_2() {
            byte[] result = Hex.decode("00dd11ee22ff");
            byte[] compare = new byte[]{(byte) 0x00, (byte) 0xDD, (byte) 0x11, (byte) 0xEE, (byte) 0x22, (byte) 0xFF};
            Assert.assertArrayEquals(compare, result);
        }

        @Test
        public void success_3() {
            byte[] result = Hex.decode("000000000000");
            byte[] compare = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
            Assert.assertArrayEquals(compare, result);
        }

        @Test
        public void success_4() {
            byte[] result = Hex.decode("e6b58be8af95");
            String resultString = new String(result, StandardCharsets.UTF_8);
            Assert.assertEquals("测试", resultString);
        }

        @Test
        public void performance_1() {
            String source = Hex.encode(Random.generateBytes(1024 * 10));
            new PerformanceCompareTest(1024 * 10) {
                @Override
                public void testMethod() {
                    Hex.decode(source);
                }

                @Override
                public void compareMethod() throws Exception {
                    org.apache.commons.codec.binary.Hex.decodeHex(source);
                }

                @Override
                public void onFinished() {
                    logger.info("[Hex.decode] cost time: {} ms", getTestMethodTotalRunTime());
                    logger.info("[Hex.decodeHex] compare time: {} ms", getCompareMethodTotalRunTime());
                    logger.info("[Hex.decode] of this project is {} faster than [Hex.decodeHex] of Apache Commons Codec", getImprovement());
                    Assert.assertTrue("performance_1", isImproved());
                    Assert.assertTrue("performance_1", getImprovedPercentage() > -10L);
                }
            }.run();
        }

        @Test
        public void performance_2() {
            String source = Hex.encode(Random.generateBytes(1024 * 10));
            new PerformanceCompareTest(1024 * 10) {
                @Override
                public void testMethod() {
                    Hex.decode(source);
                }

                @Override
                public void compareMethod() {
                    org.springframework.security.crypto.codec.Hex.decode(source);
                }

                @Override
                public void onFinished() {
                    logger.info("[Hex.decode] cost time: {} ms", getTestMethodTotalRunTime());
                    logger.info("[Hex.decode] compare time: {} ms", getCompareMethodTotalRunTime());
                    logger.info("[Hex.decode] of this project is {} faster than [Hex.decode] of Spring Security", getImprovement());
                    Assert.assertTrue("performance_2", isImproved());
                    Assert.assertTrue("performance_2", getImprovedPercentage() > -10L);
                }
            }.run();
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            String source = null;
            Hex.decode(source);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            String source = "123";
            Hex.decode(source);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            String source = "GH";
            Hex.decode(source);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_4() {
            String source = "测试";
            Hex.decode(source);
        }
    }

}
