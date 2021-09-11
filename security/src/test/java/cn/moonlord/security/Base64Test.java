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
            Assert.assertEquals("success_1", expected, result);
            Assert.assertEquals("success_1", compare1, result);
            Assert.assertEquals("success_1", compare2, result);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
            String expected = "AAAAAAAA";
            String result = Base64.encode(source);
            String compare1 = org.apache.commons.codec.binary.Base64.encodeBase64String(source);
            String compare2 = org.springframework.util.Base64Utils.encodeToString(source);
            Assert.assertEquals("success_2", expected, result);
            Assert.assertEquals("success_2", compare1, result);
            Assert.assertEquals("success_2", compare2, result);
        }

        @Test
        public void success_3() {
            byte[] source = "测试".getBytes(StandardCharsets.UTF_8);
            String expected = "5rWL6K+V";
            String result = Base64.encode(source);
            String compare1 = org.apache.commons.codec.binary.Base64.encodeBase64String(source);
            String compare2 = org.springframework.util.Base64Utils.encodeToString(source);
            Assert.assertEquals("success_3", expected, result);
            Assert.assertEquals("success_3", compare1, result);
            Assert.assertEquals("success_3", compare2, result);
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
            Assert.assertEquals("success_1", expected, result);
            Assert.assertEquals("success_1", compare1, result);
            Assert.assertEquals("success_1", compare2, result);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
            String expected = "AAAAAAAA";
            String result = Base64.encodeUrlSafe(source);
            String compare1 = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(source);
            String compare2 = org.springframework.util.Base64Utils.encodeToUrlSafeString(source);
            Assert.assertEquals("success_2", expected, result);
            Assert.assertEquals("success_2", compare1, result);
            Assert.assertEquals("success_2", compare2, result);
        }

        @Test
        public void success_3() {
            byte[] source = "测试".getBytes(StandardCharsets.UTF_8);
            String expected = "5rWL6K-V";
            String result = Base64.encodeUrlSafe(source);
            String compare1 = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(source);
            String compare2 = org.springframework.util.Base64Utils.encodeToUrlSafeString(source);
            Assert.assertEquals("success_3", expected, result);
            Assert.assertEquals("success_3", compare1, result);
            Assert.assertEquals("success_3", compare2, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Base64.encodeUrlSafe(null);
        }
    }

    public static class encodeMime {
        @Test
        public void success_1() {
            byte[] source = String.join("", Collections.nCopies(100, "测试")).getBytes(StandardCharsets.UTF_8);
            String result = Base64.encodeMime(source, Base64.MIME_CHUNK_SIZE);
            logger.info("encodeMime result: \r\n{}", result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Base64.encodeMime(null, Base64.MIME_CHUNK_SIZE);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            Base64.encodeMime(new byte[0], 0);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            Base64.encodeMime(new byte[0], Base64.MIME_CHUNK_SIZE + 1);
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
            Assert.assertArrayEquals("success_1", expected, result);
            Assert.assertArrayEquals("success_1", compare1, result);
            Assert.assertArrayEquals("success_1", compare2, result);
        }

        @Test
        public void success_2() {
            String source = "AAAAAAAA";
            byte[] expected = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
            byte[] result = Base64.decode(source);
            byte[] compare1 = org.apache.commons.codec.binary.Base64.decodeBase64(source);
            byte[] compare2 = org.springframework.util.Base64Utils.decodeFromString(source);
            Assert.assertArrayEquals("success_2", expected, result);
            Assert.assertArrayEquals("success_2", compare1, result);
            Assert.assertArrayEquals("success_2", compare2, result);
        }

        @Test
        public void success_3() {
            String source = "5rWL6K+V";
            byte[] expected = "测试".getBytes(StandardCharsets.UTF_8);
            byte[] result = Base64.decode(source);
            byte[] compare1 = org.apache.commons.codec.binary.Base64.decodeBase64(source);
            byte[] compare2 = org.springframework.util.Base64Utils.decodeFromString(source);
            Assert.assertArrayEquals("success_3", expected, result);
            Assert.assertArrayEquals("success_3", compare1, result);
            Assert.assertArrayEquals("success_3", compare2, result);
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
            Assert.assertArrayEquals("success_1", expected, result);
            Assert.assertArrayEquals("success_1", compare1, result);
            Assert.assertArrayEquals("success_1", compare2, result);
        }

        @Test
        public void success_2() {
            String source = "AAAAAAAA";
            byte[] expected = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
            byte[] result = Base64.decodeUrlSafe(source);
            byte[] compare1 = org.apache.commons.codec.binary.Base64.decodeBase64(source);
            byte[] compare2 = org.springframework.util.Base64Utils.decodeFromUrlSafeString(source);
            Assert.assertArrayEquals("success_2", expected, result);
            Assert.assertArrayEquals("success_2", compare1, result);
            Assert.assertArrayEquals("success_2", compare2, result);
        }

        @Test
        public void success_3() {
            String source = "5rWL6K-V";
            byte[] expected = "测试".getBytes(StandardCharsets.UTF_8);
            byte[] result = Base64.decodeUrlSafe(source);
            byte[] compare1 = org.apache.commons.codec.binary.Base64.decodeBase64(source);
            byte[] compare2 = org.springframework.util.Base64Utils.decodeFromUrlSafeString(source);
            Assert.assertArrayEquals("success_3", expected, result);
            Assert.assertArrayEquals("success_3", compare1, result);
            Assert.assertArrayEquals("success_3", compare2, result);
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
            byte[] result = Base64.decodeMime(Base64.encodeMime(source, Base64.MIME_CHUNK_SIZE));
            Assert.assertArrayEquals("success_1", source, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Base64.decodeMime(null);
        }
    }

}
