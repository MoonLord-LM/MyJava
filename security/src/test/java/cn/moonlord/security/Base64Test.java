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

    public static class EncodeTest {
        @Test
        public void encode() {
            Assert.assertThrows(IllegalArgumentException.class, () -> Base64.encode(null));
            Assert.assertThrows(IllegalArgumentException.class, () -> Base64.encodeUrlSafe(null));
            Assert.assertThrows(IllegalArgumentException.class, () -> Base64.encodeMime(null, 0));
            Assert.assertThrows(IllegalArgumentException.class, () -> Base64.encodeMime(null, Base64.MIME_CHUNK_MAX_LENGTH + 1));
            Assert.assertThrows(IllegalArgumentException.class, () -> Base64.encodeMime(null));

            Assert.assertEquals("", Base64.encode(new byte[0]));
            Assert.assertEquals("", Base64.encodeUrlSafe(new byte[0]));
            Assert.assertEquals("", Base64.encodeMime(new byte[0],Base64.MIME_CHUNK_MAX_LENGTH));

            Assert.assertEquals("AAAAAAAA", Base64.encode(new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00}));
            Assert.assertEquals("5rWL6K-VQUJDMDE=", Base64.encodeUrlSafe("测试ABC01".getBytes(StandardCharsets.UTF_8)));
            Assert.assertEquals("5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL" + "\r\n" + "6K+V", Base64.encodeMime(String.join("", Collections.nCopies(10, "测试")).getBytes(StandardCharsets.UTF_8),Base64.MIME_CHUNK_MAX_LENGTH));

            byte[] source = Random.generateBytes(1024);
            Assert.assertEquals(org.apache.commons.codec.binary.Base64.encodeBase64String(source), Base64.encode(source));
            Assert.assertEquals(org.springframework.util.Base64Utils.encodeToString(source), Base64.encode(source));

            Assert.assertEquals(org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(source), Base64.encodeUrlSafe(source).replace("=", ""));
            Assert.assertEquals(org.springframework.util.Base64Utils.encodeToUrlSafeString(source), Base64.encodeUrlSafe(source));
            if (org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(source).length() > Base64.encodeUrlSafe(source).length()) {
                logger.info("org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString remove [ = ] at the end of result");
            }

            Assert.assertEquals(new String(org.apache.commons.codec.binary.Base64.encodeBase64Chunked(source),StandardCharsets.UTF_8).trim(), Base64.encodeMime(source, Base64.MIME_CHUNK_MAX_LENGTH));
            if (org.apache.commons.codec.binary.Base64.encodeBase64Chunked(source).length == Base64.encodeMime(source, Base64.MIME_CHUNK_MAX_LENGTH).length() + 2) {
                logger.info("org.apache.commons.codec.binary.Base64.encodeBase64Chunked add [ \\r\\n ] at the end of result");
            }
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
