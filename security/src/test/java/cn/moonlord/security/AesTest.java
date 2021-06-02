package cn.moonlord.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.spec.SecretKeySpec;
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
            logger.info("source" + " [ " + source.length + " ] " + " [ " + Arrays.toString(source) + " ] ");
            logger.info("key" + " [ " + key.length + " ] " + " [ " + Base64.encode(key) + " ] ");
            logger.info("result" + " [ " + result.length + " ] " + " [ " + Base64.encode(result) + " ] ");
            Assert.assertEquals("success_1", 28, result.length);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[1024];
            for (int i = 0; i <= source.length; i++) {
                byte[] result = Aes.encrypt(Arrays.copyOfRange(source, 0, i), Aes.generateKeyBytes());
                logger.info("encrypt " + " [ " + i + " ] bytes to " + " [ " + result.length + " ]  bytes length " + " [ " + Base64.encode(result) + " ] ");
                Assert.assertEquals("success_2", (i + 28), result.length);
            }
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() throws Exception {
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
            String source = "Bq3mORrbjFvklrGN0p8tk56t/Q8nPxrwWvccsg==";
            String key = "FA2AWG9YgCE8k+ypI+EDuUcJEi4H3bp3BQbQHkc0tJQ=";
            byte[] result = Aes.decrypt(Base64.decode(source), key);
            Assert.assertEquals("success_1", 0, result.length);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() throws Exception {
            Aes.decrypt((byte[]) null, Aes.generateKey());
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
