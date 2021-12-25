package cn.moonlord.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@SpringBootTest
@RunWith(Enclosed.class)
public class Base64Test {

    public static Logger logger = LoggerFactory.getLogger(Base64Test.class);

    public static class encode {
        @Test
        public void success_1() {
            byte[] source = new byte[0];
            String expected = "";
            String result = Base64.encode(source);
            String compare1 = org.apache.commons.codec.binary.Base64.encodeBase64String(source);
            String compare2 = org.springframework.util.Base64Utils.encodeToString(source);
            Assert.assertEquals(expected, result);
            Assert.assertEquals(compare1, result);
            Assert.assertEquals(compare2, result);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
            String expected = "AAAAAAAA";
            String result = Base64.encode(source);
            String compare1 = org.apache.commons.codec.binary.Base64.encodeBase64String(source);
            String compare2 = org.springframework.util.Base64Utils.encodeToString(source);
            Assert.assertEquals(expected, result);
            Assert.assertEquals(compare1, result);
            Assert.assertEquals(compare2, result);
        }

        @Test
        public void success_3() {
            byte[] source = "测试ABC01".getBytes(StandardCharsets.UTF_8);
            String expected = "5rWL6K+VQUJDMDE=";
            String result = Base64.encode(source);
            String compare1 = org.apache.commons.codec.binary.Base64.encodeBase64String(source);
            String compare2 = org.springframework.util.Base64Utils.encodeToString(source);
            Assert.assertEquals(expected, result);
            Assert.assertEquals(compare1, result);
            Assert.assertEquals(compare2, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Base64.encode(null);
        }
    }

    public static class encodeUrlSafe {
        @Test
        public void success_1() {
            byte[] source = new byte[0];
            String expected = "";
            String result = Base64.encodeUrlSafe(source);
            String compare1 = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(source);
            String compare2 = org.springframework.util.Base64Utils.encodeToUrlSafeString(source);
            Assert.assertEquals(expected, result);
            Assert.assertEquals(compare1, result);
            Assert.assertEquals(compare2, result);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
            String expected = "AAAAAAAA";
            String result = Base64.encodeUrlSafe(source);
            String compare1 = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(source);
            String compare2 = org.springframework.util.Base64Utils.encodeToUrlSafeString(source);
            Assert.assertEquals(expected, result);
            Assert.assertEquals(compare1, result);
            Assert.assertEquals(compare2, result);
        }

        @Test
        public void success_3() {
            byte[] source = "测试ABC01".getBytes(StandardCharsets.UTF_8);
            String expected = "5rWL6K-VQUJDMDE=";
            String result = Base64.encodeUrlSafe(source);
            String compare1 = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(source);
            String compare2 = org.springframework.util.Base64Utils.encodeToUrlSafeString(source);
            logger.info("Base64.encodeUrlSafe: {}", result);
            logger.info("org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString: {}", compare1);
            logger.info("org.springframework.util.Base64Utils.encodeToUrlSafeString: {}", compare2);
            Assert.assertEquals(expected, result);
            Assert.assertEquals(compare1.replace("=", ""), result.replace("=", ""));
            Assert.assertEquals(compare2.replace("=", ""), result.replace("=", ""));
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Base64.encodeUrlSafe(null);
        }
    }

    public static class encodeMime {
        @Test
        public void success_1() {
            byte[] source = String.join("", Collections.nCopies(10, "测试")).getBytes(StandardCharsets.UTF_8);
            String expected = "5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL" + "\r\n" + "6K+V";
            String result = Base64.encodeMime(source, Base64.MIME_CHUNK_MAX_LENGTH);
            Assert.assertEquals(expected, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Base64.encodeMime(null, Base64.MIME_CHUNK_MAX_LENGTH);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            Base64.encodeMime(new byte[0], 0);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            Base64.encodeMime(new byte[0], Base64.MIME_CHUNK_MAX_LENGTH + 1);
        }
    }

    public static class decode {
        @Test
        public void success_1() {
            String source = "";
            byte[] expected = new byte[0];
            byte[] result = Base64.decode(source);
            byte[] compare1 = org.apache.commons.codec.binary.Base64.decodeBase64(source);
            byte[] compare2 = org.springframework.util.Base64Utils.decodeFromString(source);
            Assert.assertArrayEquals(expected, result);
            Assert.assertArrayEquals(compare1, result);
            Assert.assertArrayEquals(compare2, result);
        }

        @Test
        public void success_2() {
            String source = "AAAAAAAA";
            byte[] expected = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
            byte[] result = Base64.decode(source);
            byte[] compare1 = org.apache.commons.codec.binary.Base64.decodeBase64(source);
            byte[] compare2 = org.springframework.util.Base64Utils.decodeFromString(source);
            Assert.assertArrayEquals(expected, result);
            Assert.assertArrayEquals(compare1, result);
            Assert.assertArrayEquals(compare2, result);
        }

        @Test
        public void success_3() {
            String source = "5rWL6K+VQUJDMDE=";
            byte[] expected = "测试ABC01".getBytes(StandardCharsets.UTF_8);
            byte[] result = Base64.decode(source);
            byte[] compare1 = org.apache.commons.codec.binary.Base64.decodeBase64(source);
            byte[] compare2 = org.springframework.util.Base64Utils.decodeFromString(source);
            Assert.assertArrayEquals(expected, result);
            Assert.assertArrayEquals(compare1, result);
            Assert.assertArrayEquals(compare2, result);
            source = source.replace("=", "");
            result = Base64.decode(source);
            compare1 = org.apache.commons.codec.binary.Base64.decodeBase64(source);
            compare2 = org.springframework.util.Base64Utils.decodeFromString(source);
            Assert.assertArrayEquals(expected, result);
            Assert.assertArrayEquals(compare1, result);
            Assert.assertArrayEquals(compare2, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Base64.decode(null);
        }
    }

    public static class decodeUrlSafe {
        @Test
        public void success_1() {
            String source = "";
            byte[] expected = new byte[0];
            byte[] result = Base64.decodeUrlSafe(source);
            byte[] compare1 = org.apache.commons.codec.binary.Base64.decodeBase64(source);
            byte[] compare2 = org.springframework.util.Base64Utils.decodeFromUrlSafeString(source);
            Assert.assertArrayEquals(expected, result);
            Assert.assertArrayEquals(compare1, result);
            Assert.assertArrayEquals(compare2, result);
        }

        @Test
        public void success_2() {
            String source = "AAAAAAAA";
            byte[] expected = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
            byte[] result = Base64.decodeUrlSafe(source);
            byte[] compare1 = org.apache.commons.codec.binary.Base64.decodeBase64(source);
            byte[] compare2 = org.springframework.util.Base64Utils.decodeFromUrlSafeString(source);
            Assert.assertArrayEquals(expected, result);
            Assert.assertArrayEquals(compare1, result);
            Assert.assertArrayEquals(compare2, result);
        }

        @Test
        public void success_3() {
            String source = "5rWL6K-VQUJDMDE=";
            byte[] expected = "测试ABC01".getBytes(StandardCharsets.UTF_8);
            byte[] result = Base64.decodeUrlSafe(source);
            byte[] compare1 = org.apache.commons.codec.binary.Base64.decodeBase64(source);
            byte[] compare2 = org.springframework.util.Base64Utils.decodeFromUrlSafeString(source);
            Assert.assertArrayEquals(expected, result);
            Assert.assertArrayEquals(compare1, result);
            Assert.assertArrayEquals(compare2, result);
            source = source.replace("=", "");
            result = Base64.decodeUrlSafe(source);
            compare1 = org.apache.commons.codec.binary.Base64.decodeBase64(source);
            compare2 = org.springframework.util.Base64Utils.decodeFromUrlSafeString(source);
            Assert.assertArrayEquals(expected, result);
            Assert.assertArrayEquals(compare1, result);
            Assert.assertArrayEquals(compare2, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Base64.decodeUrlSafe(null);
        }
    }

    public static class decodeMime {
        @Test
        public void success_1() {
            byte[] source = String.join("", Collections.nCopies(100, "测试")).getBytes(StandardCharsets.UTF_8);
            byte[] result = Base64.decodeMime(Base64.encodeMime(source, Base64.MIME_CHUNK_MAX_LENGTH));
            Assert.assertArrayEquals(source, result);
            result = Base64.decodeMime(Base64.encodeMime(source, Base64.MIME_CHUNK_MAX_LENGTH).replace("\r\n", "\n"));
            Assert.assertArrayEquals(source, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Base64.decodeMime(null);
        }
    }

}
