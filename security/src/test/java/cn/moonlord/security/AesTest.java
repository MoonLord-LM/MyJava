package cn.moonlord.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@SpringBootTest
@RunWith(Enclosed.class)
public class AesTest {

    public static Logger logger = LoggerFactory.getLogger(AesTest.class);

    public static class generateKey {
        @Test
        public void success_1() {
            byte[] result = Aes.generateKey().getEncoded();
            Assert.assertEquals("success_1", 32, result.length);
        }
    }

    public static class generateKeyBytes {
        @Test
        public void success_1() {
            byte[] result = Aes.generateKeyBytes();
            Assert.assertEquals("success_1", 32, result.length);
        }
    }

    public static class generateKeyBase64String {
        @Test
        public void success_1() {
            byte[] result = Base64.decode(Aes.generateKeyBase64String());
            Assert.assertEquals("success_1", 32, result.length);
        }
    }

    public static class getSecretKey {
        @Test
        public void success_1() {
            Aes.getSecretKey(Aes.generateKeyBytes());
        }

        @Test
        public void success_2() {
            Aes.getSecretKey(Aes.generateKeyBase64String());
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Aes.getSecretKey((byte[]) null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            Aes.getSecretKey(new byte[0]);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            Aes.getSecretKey(new byte[]{(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33});
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_4() {
            Aes.getSecretKey((String) null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_5() {
            Aes.getSecretKey("");
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_6() {
            Aes.getSecretKey("AAAAAAAA");
        }
    }

    public static class encrypt {
        @Test
        public void success_1() {
            byte[] source = new byte[0];
            byte[] key = Aes.generateKeyBytes();
            byte[] result = Aes.encrypt(source, key);
            logger.info("source" + " [ " + source.length + " ] " + " [ " + Base64.encode(source) + " ] ");
            logger.info("key" + " [ " + key.length + " ] " + " [ " + Base64.encode(key) + " ] ");
            logger.info("result" + " [ " + result.length + " ] " + " [ " + Base64.encode(result) + " ] ");
            Assert.assertEquals("success_1", 28, result.length);
        }

        @Test
        public void success_2() {
            byte[] source = "测试".getBytes(StandardCharsets.UTF_8);
            byte[] key = Aes.generateKeyBytes();
            byte[] result = Aes.encrypt(source, key);
            logger.info("source" + " [ " + source.length + " ] " + " [ " + Base64.encode(source) + " ] ");
            logger.info("key" + " [ " + key.length + " ] " + " [ " + Base64.encode(key) + " ] ");
            logger.info("result" + " [ " + result.length + " ] " + " [ " + Base64.encode(result) + " ] ");
            Assert.assertEquals("success_2", 34, result.length);
        }

        @Test
        public void success_3() {
            byte[] source = new byte[1024];
            for (int i = 0; i <= source.length; i++) {
                byte[] result = Aes.encrypt(Arrays.copyOfRange(source, 0, i), Aes.generateKeyBytes());
                logger.info("encrypt " + " [ " + i + " ] bytes to " + " [ " + result.length + " ]  bytes length");
                Assert.assertEquals("success_3", (i + 28), result.length);
            }
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Aes.encrypt((byte[]) null, Aes.generateKey());
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            Aes.encrypt(new byte[0], (SecretKeySpec) null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            Aes.encrypt(new byte[0], new SecretKeySpec(Random.generate(0), Aes.AES_KEY_ALGORITHM));
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_4() {
            Aes.encrypt(new byte[0], new SecretKeySpec(Random.generate(128), Aes.AES_KEY_ALGORITHM));
        }
    }

    public static class decrypt {
        @Test
        public void success_1() {
            String source = "AAAAAAAAAAAAAAAAUw+K+8dFNrmpY7TxxMtziw==";
            String key = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=";
            byte[] result = Aes.decrypt(Base64.decode(source), key);
            Assert.assertEquals("success_1", 0, result.length);
        }

        @Test
        public void success_2() {
            String source = "mXAvu1G1+boW/U11+cCYc9RwTABpEsbCGzb9zFZy8vJiuw==";
            String key = "UpBpV8RICneB0uuBTOTUCP13u7OuQ2Q+tsdi5dpAYiY=";
            byte[] result = Aes.decrypt(Base64.decode(source), key);
            String resultString = new String(result, StandardCharsets.UTF_8);
            Assert.assertEquals("success_2", 6, result.length);
            Assert.assertEquals("success_2", "测试", resultString);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Aes.decrypt(null, Aes.generateKey());
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            Aes.decrypt(new byte[0], (SecretKeySpec) null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            Aes.decrypt(new byte[0], new SecretKeySpec(Random.generate(0), Aes.AES_KEY_ALGORITHM));
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_4() {
            Aes.decrypt(new byte[0], new SecretKeySpec(Random.generate(128), Aes.AES_KEY_ALGORITHM));
        }
    }

}
