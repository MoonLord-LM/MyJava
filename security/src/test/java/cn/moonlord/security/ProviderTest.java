package cn.moonlord.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.*;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

@SpringBootTest
@RunWith(Enclosed.class)
public class ProviderTest {

    public static Logger logger = LoggerFactory.getLogger(ProviderTest.class);

    public static class BouncyCastleProviderTest {
        @Test
        public void show() {
            Provider.addBouncyCastleProvider();
            java.security.Provider[] providers = Security.getProviders();
            logger.info("Provider:");
            for (int i = 0; i < providers.length; i++) {
                java.security.Provider provider = providers[i];
                logger.info("Provider  [ " + i + " ]  [ " + provider.getName() + " ] " + " [ version: " + provider.getVersion() + " ]  [ size: " + provider.size() + " ] " + provider.getInfo());
            }
            List<String> types = Arrays.asList("MessageDigest", "Signature", "SecretKeyFactory", "SecureRandom", "Cipher");
            for (String type : types) {
                logger.info("");
                logger.info(type + ":");
                for (int i = 0; i < providers.length; i++) {
                    java.security.Provider provider = providers[i];
                    for (Enumeration<Object> e = provider.keys(); e.hasMoreElements(); ) {
                        String key = (String) e.nextElement();
                        if (key.toUpperCase(Locale.ROOT).startsWith((type + ".").toUpperCase(Locale.ROOT))) {
                            logger.info(type + " [ " + i + " ]  [ " + provider.getName() + " ] " + key.substring((type + ".").length()) + "  ->  " + provider.get(key));
                        }
                    }
                }
            }
        }

        @Test
        public void test() throws NoSuchPaddingException, NoSuchAlgorithmException {
            Provider.removeBouncyCastleProvider();

            // Random
            Assert.assertThrows(NoSuchAlgorithmException.class, () -> SecureRandom.getInstance(Random.ALGORITHM_NONCE_AND_IV));
            Assert.assertThrows(NoSuchAlgorithmException.class, () -> SecureRandom.getInstance(Random.ALGORITHM_DEFAULT));
            // Hash
            MessageDigest.getInstance(Hash.SHA256);
            MessageDigest.getInstance(Hash.SHA512);
            // Aes
            Cipher.getInstance(Aes.AES_CIPHER_INSTANCE);
            KeyGenerator.getInstance(Aes.AES_KEY_ALGORITHM);
            Assert.assertThrows(NoSuchAlgorithmException.class, () -> Cipher.getInstance("AES/CBC/PKCS7Padding"));
            // Rsa
            Assert.assertThrows(NoSuchAlgorithmException.class, () -> Cipher.getInstance(Rsa.RSA_CIPHER_INSTANCE));
            KeyPairGenerator.getInstance(Rsa.RSA_KEY_ALGORITHM);
            // Pbkdf2
            SecretKeyFactory.getInstance(Pbkdf2.PBKDF2_ALGORITHM);

            Provider.addBouncyCastleProvider();

            // Random
            SecureRandom.getInstance(Random.ALGORITHM_NONCE_AND_IV);
            SecureRandom.getInstance(Random.ALGORITHM_DEFAULT);
            // Hash
            MessageDigest.getInstance(Hash.SHA256);
            MessageDigest.getInstance(Hash.SHA512);
            // Aes
            KeyGenerator.getInstance(Aes.AES_KEY_ALGORITHM);
            Cipher.getInstance(Aes.AES_CIPHER_INSTANCE);
            Cipher.getInstance("AES/CBC/PKCS7Padding");
            // Rsa
            KeyPairGenerator.getInstance(Rsa.RSA_KEY_ALGORITHM);
            Cipher.getInstance(Rsa.RSA_CIPHER_INSTANCE);
            // Pbkdf2
            SecretKeyFactory.getInstance(Pbkdf2.PBKDF2_ALGORITHM);

            // ChaCha20Poly1305
            Cipher.getInstance("ChaCha20-Poly1305");
            Assert.assertThrows(NoSuchAlgorithmException.class, () -> Cipher.getInstance("ChaCha20-IETF-Poly1305"));
            Assert.assertThrows(NoSuchAlgorithmException.class, () -> Cipher.getInstance("XChaCha20-IETF-Poly1305"));
        }

        @Test
        public void exception() {
            try {
                Field field = Provider.PROVIDER_BOUNCY_CASTLE.getClass().getDeclaredField("PROVIDER_NAME");
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                field.setAccessible(true);
                field.set(Provider.PROVIDER_BOUNCY_CASTLE.getClass(), "UNKNOWN");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Provider.addBouncyCastleProvider(); //TODO
            Provider.removeBouncyCastleProvider(); //TODO
            Provider.addBouncyCastleProvider(); //TODO
        }
    }

}
