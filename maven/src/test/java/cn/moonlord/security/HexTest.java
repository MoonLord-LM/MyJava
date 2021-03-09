package cn.moonlord.security;

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

    public static class encode {

        public static Logger logger = LoggerFactory.getLogger(encode.class);

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
            byte[] source = new byte[1024 * 1024 * 16];
            long beginTime = System.currentTimeMillis();
            for (int i = 0; i < 16; i++) {
                Hex.encode(source);
            }
            long endTime = System.currentTimeMillis();
            long costTime = endTime - beginTime;
            logger.info("[Hex.encode] cost time: {} ms", costTime);
            beginTime = System.currentTimeMillis();
            for (int i = 0; i < 16; i++) {
                org.apache.commons.codec.binary.Hex.encodeHexString(source);
            }
            endTime = System.currentTimeMillis();
            long compareTime = endTime - beginTime;
            logger.info("[Hex.encodeHexString] compare time: {} ms", compareTime);
            Assert.assertTrue("performance_1", costTime < compareTime);
            double ratio = ( ( 1 / (double) costTime ) -  ( 1 / (double) compareTime ) ) / ( 1 / (double) compareTime );
            String improvement = Math.round(ratio * 100) + "%";
            logger.info("[Hex.encode] of this project is {} faster than [Hex.encodeHexString] of Apache Commons Codec", improvement);
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

        public static Logger logger = LoggerFactory.getLogger(decode.class);

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
            String source = Hex.encode(new byte[1024 * 1024 * 16]);
            long beginTime = System.currentTimeMillis();
            for (int i = 0; i < 16; i++) {
                Hex.decode(source);
            }
            long endTime = System.currentTimeMillis();
            long costTime = endTime - beginTime;
            logger.info("[Hex.decode] cost time: {} ms", costTime);
            beginTime = System.currentTimeMillis();
            for (int i = 0; i < 16; i++) {
                org.apache.commons.codec.binary.Hex.decodeHex(source);
            }
            endTime = System.currentTimeMillis();
            long compareTime = endTime - beginTime;
            logger.info("[Hex.decodeHex] compare time: {} ms", compareTime);
            Assert.assertTrue("performance_1", costTime < compareTime);
            double ratio = ( ( 1 / (double) costTime ) -  ( 1 / (double) compareTime ) ) / ( 1 / (double) compareTime );
            String improvement = Math.round(ratio * 100) + "%";
            logger.info("[Hex.decode] of this project is {} faster than [Hex.decodeHex] of Apache Commons Codec", improvement);
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
            String source = "!@#$";
            Hex.decode(source);
        }
    }

}
