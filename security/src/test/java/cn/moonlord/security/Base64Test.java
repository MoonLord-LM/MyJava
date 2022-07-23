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
            Assert.assertThrows(IllegalArgumentException.class, () -> Base64.encodeMime(null, Base64.MIME_LINE_MAX_LENGTH));
            Assert.assertThrows(IllegalArgumentException.class, () -> Base64.encodeMime(new byte[0], 0));
            Assert.assertThrows(IllegalArgumentException.class, () -> Base64.encodeMime(new byte[0], Base64.MIME_LINE_MAX_LENGTH + 1));
            Assert.assertThrows(IllegalArgumentException.class, () -> Base64.encodeMime(null));

            Assert.assertEquals("", Base64.encode(new byte[0]));
            Assert.assertEquals("", Base64.encodeUrlSafe(new byte[0]));
            Assert.assertEquals("", Base64.encodeMime(new byte[0], Base64.MIME_LINE_MAX_LENGTH));
            Assert.assertEquals("", Base64.encodeMime(new byte[0]));

            Assert.assertEquals("AAAAAAAA", Base64.encode(new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00}));
            Assert.assertEquals("5rWL6K-VQUJDMDE=", Base64.encodeUrlSafe("测试ABC01".getBytes(StandardCharsets.UTF_8)));
            Assert.assertEquals("5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL" + "\r\n" + "6K+V", Base64.encodeMime(String.join("", Collections.nCopies(10, "测试")).getBytes(StandardCharsets.UTF_8), Base64.MIME_LINE_MAX_LENGTH));
            Assert.assertEquals("5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL" + "\r\n" + "6K+V", Base64.encodeMime(String.join("", Collections.nCopies(10, "测试")).getBytes(StandardCharsets.UTF_8)));

            byte[] source = Random.generateBytes(1024);
            Assert.assertEquals(org.apache.commons.codec.binary.Base64.encodeBase64String(source), Base64.encode(source));
            Assert.assertEquals(org.springframework.util.Base64Utils.encodeToString(source), Base64.encode(source));

            Assert.assertEquals(org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(source), Base64.encodeUrlSafe(source).replace("=", ""));
            Assert.assertEquals(org.springframework.util.Base64Utils.encodeToUrlSafeString(source), Base64.encodeUrlSafe(source));
            if (org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(source).length() < Base64.encodeUrlSafe(source).length()) {
                logger.info("org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString remove [ = ] at the end of result");
            }

            Assert.assertEquals(new String(org.apache.commons.codec.binary.Base64.encodeBase64Chunked(source), StandardCharsets.UTF_8).trim(), Base64.encodeMime(source, Base64.MIME_LINE_MAX_LENGTH));
            if (org.apache.commons.codec.binary.Base64.encodeBase64Chunked(source).length == Base64.encodeMime(source, Base64.MIME_LINE_MAX_LENGTH).length() + 2) {
                logger.info("org.apache.commons.codec.binary.Base64.encodeBase64Chunked add [ \\r\\n ] at the end of result");
            }
        }
    }

    public static class DecodeTest {
        @Test
        public void decode() {
            Assert.assertThrows(IllegalArgumentException.class, () -> Base64.decode(null));

            Assert.assertArrayEquals(new byte[0], Base64.decode(""));

            Assert.assertArrayEquals(new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00}, Base64.decode("AAAAAAAA"));
            Assert.assertArrayEquals("测试ABC01".getBytes(StandardCharsets.UTF_8), Base64.decode("5rWL6K-VQUJDMDE="));
            Assert.assertArrayEquals(String.join("", Collections.nCopies(10, "测试")).getBytes(StandardCharsets.UTF_8), Base64.decode("5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL6K+V5rWL" + "\r\n" + "6K+V"));

            byte[] source = Random.generateBytes(1024);
            Assert.assertArrayEquals(source, Base64.decode(Base64.encode(source)));
            Assert.assertArrayEquals(source, Base64.decode(org.apache.commons.codec.binary.Base64.encodeBase64String(source)));
            Assert.assertArrayEquals(source, Base64.decode(org.springframework.util.Base64Utils.encodeToString(source)));

            Assert.assertArrayEquals(source, Base64.decode(Base64.encodeUrlSafe(source)));
            Assert.assertArrayEquals(source, Base64.decode(org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(source)));
            Assert.assertArrayEquals(source, Base64.decode(org.springframework.util.Base64Utils.encodeToUrlSafeString(source)));

            Assert.assertArrayEquals(source, Base64.decode(Base64.encodeMime(source, 1)));
            Assert.assertArrayEquals(source, Base64.decode(Base64.encodeMime(source, Base64.MIME_LINE_MAX_LENGTH - 1)));
            Assert.assertArrayEquals(source, Base64.decode(Base64.encodeMime(source)));
            Assert.assertArrayEquals(source, Base64.decode(new String(org.apache.commons.codec.binary.Base64.encodeBase64Chunked(source), StandardCharsets.UTF_8)));
        }
    }

}
