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

    public static class EncodeTest {
        @Test
        public void encode() {
            Assert.assertThrows(IllegalArgumentException.class, () -> Hex.encode(null));
            Assert.assertThrows(IllegalArgumentException.class, () -> Hex.encode(new byte[Hex.SOURCE_MAX_BYTE_LENGTH + 1]));

            Assert.assertEquals("", Hex.encode(new byte[0]));
            Assert.assertEquals("00dd11ee22ff", Hex.encode(new byte[]{(byte) 0x00, (byte) 0xDD, (byte) 0x11, (byte) 0xEE, (byte) 0x22, (byte) 0xFF}));
            Assert.assertEquals("e6b58be8af95", Hex.encode("测试".getBytes(StandardCharsets.UTF_8)));

            byte[] source = Random.generateBytes(1024);
            Assert.assertEquals(org.bouncycastle.util.encoders.Hex.toHexString(source), Hex.encode(source));
            Assert.assertEquals(org.apache.commons.codec.binary.Hex.encodeHexString(source), Hex.encode(source));
            Assert.assertEquals(new String(org.springframework.security.crypto.codec.Hex.encode(source)), Hex.encode(source));
        }

        @Test
        public void performance() {
            byte[] source = Random.generateBytes(1024 * 4);
            new PerformanceCompareTest(1024 * 4, () -> Hex.encode(source), () -> org.bouncycastle.util.encoders.Hex.toHexString(source)) {
                @Override
                public void onFinished() {
                    logger.info("[Hex.encode] cost time: {} ms", getTestMethodTotalRunTime());
                    logger.info("[Hex.toHexString] compare time: {} ms", getCompareMethodTotalRunTime());
                    logger.info("[Hex.encode] of this project is {} faster than [Hex.toHexString] of Bouncy Castle", getImprovement());
                    Assert.assertTrue(isImproved());
                }
            }.run();
            new PerformanceCompareTest(1024 * 4, () -> Hex.encode(source), () -> org.apache.commons.codec.binary.Hex.encodeHexString(source)) {
                @Override
                public void onFinished() {
                    logger.info("[Hex.encode] cost time: {} ms", getTestMethodTotalRunTime());
                    logger.info("[Hex.encodeHexString] compare time: {} ms", getCompareMethodTotalRunTime());
                    logger.info("[Hex.encode] of this project is {} faster than [Hex.encodeHexString] of Apache Commons Codec", getImprovement());
                    Assert.assertTrue(isImproved());
                }
            }.run();
            new PerformanceCompareTest(1024 * 4, () -> Hex.encode(source), () -> new String(org.springframework.security.crypto.codec.Hex.encode(source))) {
                @Override
                public void onFinished() {
                    logger.info("[Hex.encode] cost time: {} ms", getTestMethodTotalRunTime());
                    logger.info("[Hex.encode] compare time: {} ms", getCompareMethodTotalRunTime());
                    logger.info("[Hex.encode] of this project is {} faster than [Hex.encode] of Spring Security", getImprovement());
                    Assert.assertTrue(getImprovedPercentage() > 50L);
                }
            }.run();
        }
    }

    public static class DecodeTest {
        @Test
        public void decode() {
            Assert.assertThrows(IllegalArgumentException.class, () -> Hex.decode(null));
            Assert.assertThrows(IllegalArgumentException.class, () -> Hex.decode("123"));
            Assert.assertThrows(IllegalArgumentException.class, () -> Hex.decode("FG"));
            Assert.assertThrows(IllegalArgumentException.class, () -> Hex.decode("GH"));
            Assert.assertThrows(IllegalArgumentException.class, () -> Hex.decode("0!"));
            Assert.assertThrows(IllegalArgumentException.class, () -> Hex.decode("!@"));
            Assert.assertThrows(IllegalArgumentException.class, () -> Hex.decode("测试"));
            Assert.assertThrows(IllegalArgumentException.class, () -> Hex.decode("ABC测试"));

            Assert.assertArrayEquals(new byte[0], Hex.decode(""));
            Assert.assertArrayEquals(new byte[]{(byte) 0x00, (byte) 0xDD, (byte) 0x11, (byte) 0xEE, (byte) 0x22, (byte) 0xFF}, Hex.decode("00dd11ee22ff"));
            Assert.assertEquals("测试", new String(Hex.decode("e6b58be8af95"), StandardCharsets.UTF_8));
        }

        @Test
        public void performance() {
            String source = Hex.encode(Random.generateBytes(1024 * 4));
            new PerformanceCompareTest(1024 * 4, () -> Hex.decode(source), () -> org.bouncycastle.util.encoders.Hex.decode(source)) {
                @Override
                public void onFinished() {
                    logger.info("[Hex.decode] cost time: {} ms", getTestMethodTotalRunTime());
                    logger.info("[Hex.decode] compare time: {} ms", getCompareMethodTotalRunTime());
                    logger.info("[Hex.decode] of this project is {} faster than [Hex.decode] of Bouncy Castle", getImprovement());
                    Assert.assertTrue(isImproved());
                }
            }.run();
            new PerformanceCompareTest(1024 * 4, () -> Hex.decode(source), () -> org.apache.commons.codec.binary.Hex.decodeHex(source)) {
                @Override
                public void onFinished() {
                    logger.info("[Hex.decode] cost time: {} ms", getTestMethodTotalRunTime());
                    logger.info("[Hex.decodeHex] compare time: {} ms", getCompareMethodTotalRunTime());
                    logger.info("[Hex.decode] of this project is {} faster than [Hex.decodeHex] of Apache Commons Codec", getImprovement());
                    Assert.assertTrue(isImproved());
                }
            }.run();
            new PerformanceCompareTest(1024 * 4, () -> Hex.decode(source), () -> new String(org.springframework.security.crypto.codec.Hex.decode(source))) {
                @Override
                public void onFinished() {
                    logger.info("[Hex.decode] cost time: {} ms", getTestMethodTotalRunTime());
                    logger.info("[Hex.decode] compare time: {} ms", getCompareMethodTotalRunTime());
                    logger.info("[Hex.decode] of this project is {} faster than [Hex.decode] of Spring Security", getImprovement());
                    Assert.assertTrue(isImproved());
                }
            }.run();
        }
    }

}
