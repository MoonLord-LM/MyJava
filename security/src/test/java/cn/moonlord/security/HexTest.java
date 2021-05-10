package cn.moonlord.security;

import cn.moonlord.test.PerformanceCompare;
import org.apache.commons.codec.DecoderException;
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
            byte[] source = new byte[]{(byte) 0x00, (byte) 0xDD, (byte) 0x11, (byte) 0xEE, (byte) 0x22, (byte) 0xFF};
            String result = Hex.encode(source);
            Assert.assertEquals("success_1", "00dd11ee22ff", result);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
            String result = Hex.encode(source);
            Assert.assertEquals("success_2", "000000000000", result);
        }

        @Test
        public void success_3() {
            byte[] source = "测试".getBytes(StandardCharsets.UTF_8);
            String result = Hex.encode(source);
            Assert.assertEquals("success_3", "e6b58be8af95", result);
        }

        @Test
        public void performance_1() {
            byte[] source = Random.generateBytes(1024 * 1024 * 16);
            new PerformanceCompare(32) {
                @Override
                public void testMethod() {
                    Hex.encode(source);
                }
                @Override
                public void compareMethod() {
                    org.apache.commons.codec.binary.Hex.encodeHexString(source);
                }
                @Override
                public void onCompleted() {
                    logger.info("[Hex.encode] cost time: {} ms", getTestMethodRunTime());
                    logger.info("[Hex.encodeHexString] compare time: {} ms", getCompareMethodRunTime());
                    logger.info("[Hex.encode] of this project is {} faster than [Hex.encodeHexString] of Apache Commons Codec", getImprovementRadio());
                    Assert.assertTrue("performance_1", getTestMethodRunTime() < getCompareMethodRunTime());
                }
            }.run();
        }

        @Test
        public void performance_2() {
            byte[] source = Random.generateBytes(1024 * 1024 * 16);
            new PerformanceCompare(32) {
                @Override
                public void testMethod() {
                    Hex.encode(source);
                }
                @Override
                public void compareMethod() {
                    new String(org.springframework.security.crypto.codec.Hex.encode(source));
                }
                @Override
                public void onCompleted() {
                    logger.info("[Hex.encode] cost time: {} ms", getTestMethodRunTime());
                    logger.info("[Hex.encode] compare time: {} ms", getCompareMethodRunTime());
                    logger.info("[Hex.encode] of this project is {} faster than [Hex.encode] of Spring Security", getImprovementRadio());
                    Assert.assertTrue("performance_2", getTestMethodRunTime() < getCompareMethodRunTime());
                }
            }.run();
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hex.encode(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            byte[] source = new byte[0];
            Hex.encode(source);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            byte[] source = new byte[Integer.MAX_VALUE / 2 + 1];
            Hex.encode(source);
        }
    }

    public static class decode {

        @Test
        public void success_1() {
            byte[] result = Hex.decode("00dd11ee22ff");
            Assert.assertEquals("success_1", 6, result.length);
            Assert.assertEquals("success_1", (byte) 0x00, result[0]);
            Assert.assertEquals("success_1", (byte) 0xDD, result[1]);
            Assert.assertEquals("success_1", (byte) 0x11, result[2]);
            Assert.assertEquals("success_1", (byte) 0xEE, result[3]);
            Assert.assertEquals("success_1", (byte) 0x22, result[4]);
            Assert.assertEquals("success_1", (byte) 0xFF, result[5]);
        }

        @Test
        public void success_2() {
            byte[] result = Hex.decode("000000000000");
            Assert.assertEquals("success_2", 6, result.length);
            Assert.assertEquals("success_2", (byte) 0x00, result[0]);
            Assert.assertEquals("success_2", (byte) 0x00, result[1]);
            Assert.assertEquals("success_2", (byte) 0x00, result[2]);
            Assert.assertEquals("success_2", (byte) 0x00, result[3]);
            Assert.assertEquals("success_2", (byte) 0x00, result[4]);
            Assert.assertEquals("success_2", (byte) 0x00, result[5]);
        }

        @Test
        public void success_3() {
            byte[] result = Hex.decode("e6b58be8af95");
            String resultString = new String(result, StandardCharsets.UTF_8);
            Assert.assertEquals("success_3", "测试", resultString);
        }

        @Test
        public void performance_1() throws Exception {
            String source = Hex.encode(Random.generateBytes(1024 * 1024 * 16));
            new PerformanceCompare(16) {
                @Override
                public void testMethod() {
                    Hex.decode(source);
                }
                @Override
                public void compareMethod() throws DecoderException {
                    org.apache.commons.codec.binary.Hex.decodeHex(source);
                }
                @Override
                public void onCompleted() {
                    logger.info("[Hex.decode] cost time: {} ms", getTestMethodRunTime());
                    logger.info("[Hex.decodeHex] compare time: {} ms", getCompareMethodRunTime());
                    logger.info("[Hex.decode] of this project is {} faster than [Hex.decodeHex] of Apache Commons Codec", getImprovementRadio());
                    Assert.assertTrue("performance_1", getTestMethodRunTime() < getCompareMethodRunTime());
                }
            }.run();
        }

        @Test
        public void performance_2() throws Exception {
            String source = Hex.encode(Random.generateBytes(1024 * 1024 * 16));
            new PerformanceCompare(16) {
                @Override
                public void testMethod() {
                    Hex.decode(source);
                }
                @Override
                public void compareMethod() throws DecoderException {
                    org.springframework.security.crypto.codec.Hex.decode(source);
                }
                @Override
                public void onCompleted() {
                    logger.info("[Hex.decode] cost time: {} ms", getTestMethodRunTime());
                    logger.info("[Hex.decode] compare time: {} ms", getCompareMethodRunTime());
                    logger.info("[Hex.decode] of this project is {} faster than [Hex.decode] of Spring Security", getImprovementRadio());
                    Assert.assertTrue("performance_2", getTestMethodRunTime() < getCompareMethodRunTime());
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
            String source = "";
            Hex.decode(source);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            String source = "123";
            Hex.decode(source);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_4() {
            String source = "GH";
            Hex.decode(source);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_5() {
            String source = "A!@#$";
            Hex.decode(source);
        }
    }

}
