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

    public static class GenerateKeyTest {
        @Test
        public void generateKey() {
            Assert.assertEquals(32, Aes.generateKey().getEncoded().length);
            Assert.assertEquals(32, Aes.generateKeyBytes().length);
            Assert.assertEquals(32, Base64.decode(Aes.generateKeyBase64String()).length);
        }
    }

    public static class GetSecretKeyTest {
        @Test
        public void getSecretKey() {
            Assert.assertThrows(IllegalArgumentException.class, () -> Aes.getSecretKey((byte[]) null));
            Assert.assertThrows(IllegalArgumentException.class, () -> Aes.getSecretKey(new byte[0]));
            Assert.assertThrows(IllegalArgumentException.class, () -> Aes.getSecretKey(Random.generate(128)));
            Assert.assertEquals(32, Aes.getSecretKey(Aes.generateKeyBytes()).getEncoded().length);
            Assert.assertThrows(IllegalArgumentException.class, () -> Aes.getSecretKey((String) null));
            Assert.assertThrows(IllegalArgumentException.class, () -> Aes.getSecretKey(""));
            Assert.assertThrows(IllegalArgumentException.class, () -> Aes.getSecretKey(Base64.encode(Random.generate(128))));
            Assert.assertEquals(32, Aes.getSecretKey(Aes.generateKeyBase64String()).getEncoded().length);
        }
    }

    public static class EncryptTest {
        @Test
        public void encrypt() {
            Assert.assertThrows(IllegalArgumentException.class, () -> Aes.encrypt((byte[]) null, Aes.generateKey()));
            Assert.assertThrows(IllegalArgumentException.class, () -> Aes.encrypt(new byte[0], (SecretKeySpec) null));
            Assert.assertThrows(IllegalArgumentException.class, () -> Aes.encrypt(new byte[0], new SecretKeySpec(Random.generate(128), Aes.AES_KEY_ALGORITHM)));
            Assert.assertEquals(Aes.ENCRYPTED_MIN_BYTE_LENGTH, Aes.encrypt(new byte[0], Aes.generateKey()).length);
            byte[] source = Random.generateBytes(1024);
            for (int i = 0; i <= source.length; i++) {
                byte[] result = Aes.encrypt(Arrays.copyOfRange(source, 0, i), Aes.generateKeyBytes());
                if (i % 256 == 0) {
                    logger.info("Aes encrypt " + " [ " + i + " ] bytes to " + " [ " + result.length + " ]  bytes length");
                }
                Assert.assertEquals((Aes.ENCRYPTED_MIN_BYTE_LENGTH + i), result.length);
            }
        }
    }

    public static class DecryptTest {
        @Test
        public void decrypt() {
            Assert.assertThrows(IllegalArgumentException.class, () -> Aes.decrypt(null, Aes.generateKey()));
            Assert.assertThrows(IllegalArgumentException.class, () -> Aes.decrypt(new byte[0], Aes.generateKey()));
            Assert.assertThrows(IllegalArgumentException.class, () -> Aes.decrypt(new byte[Aes.ENCRYPTED_MIN_BYTE_LENGTH - 1], Aes.generateKey()));
            Assert.assertThrows(IllegalArgumentException.class, () -> Aes.decrypt(new byte[32], (SecretKeySpec) null));
            Assert.assertThrows(IllegalArgumentException.class, () -> Aes.decrypt(new byte[32], new SecretKeySpec(Random.generate(128), Aes.AES_KEY_ALGORITHM)));
            SecretKeySpec key1 = Aes.generateKey();
            byte[] key2 = Aes.generateKeyBytes();
            String key3 = Aes.generateKeyBase64String();
            byte[] source1 = new byte[0];
            Assert.assertArrayEquals(source1, Aes.decrypt(Aes.encrypt(source1, key1), key1));
            Assert.assertArrayEquals(source1, Aes.decrypt(Aes.encrypt(source1, key2), key2));
            Assert.assertArrayEquals(source1, Aes.decrypt(Aes.encrypt(source1, key3), key3));
            String source2 = "测试";
            Assert.assertEquals(source2, Aes.decryptString(Aes.encrypt(source2, key1), key1));
            Assert.assertEquals(source2, Aes.decryptString(Aes.encrypt(source2, key2), key2));
            Assert.assertEquals(source2, Aes.decryptString(Aes.encrypt(source2, key3), key3));
            byte[] source3 = Random.generateBytes(1024);
            Assert.assertArrayEquals(source3, Aes.decrypt(Aes.encrypt(source3, key1), key1));
            Assert.assertArrayEquals(source3, Aes.decrypt(Aes.encrypt(source3, key2), key2));
            Assert.assertArrayEquals(source3, Aes.decrypt(Aes.encrypt(source3, key3), key3));
        }
    }

}
