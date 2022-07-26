package cn.moonlord.security;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
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
    }

    public static class showAesCiphers {
        @Test()
        public void success_2() throws NoSuchPaddingException, NoSuchAlgorithmException {
            Provider.init();
            logger.info(Cipher.getInstance("AES/CBC/PKCS7Padding").toString());
            logger.info(Cipher.getInstance("AES/CBC/PKCS7Padding").getAlgorithm());
            logger.info(Cipher.getInstance("AES/CBC/PKCS7Padding").getProvider().getName());
            logger.info(Cipher.getInstance("AES/CBC/PKCS7Padding").getProvider().getInfo());
        }
    }

    public static class showChaChaCiphers {
        @Test()
        public void success_2() throws NoSuchPaddingException, NoSuchAlgorithmException {
            Provider.init();
            logger.info(Cipher.getInstance("ChaCha20-Poly1305").toString());
            logger.info(Cipher.getInstance("ChaCha20-Poly1305").getAlgorithm());
            logger.info(Cipher.getInstance("ChaCha20-Poly1305").getProvider().getName());
            logger.info(Cipher.getInstance("ChaCha20-Poly1305").getProvider().getInfo());
        }

        @Test(expected = NoSuchAlgorithmException.class)
        public void error_3() throws NoSuchPaddingException, NoSuchAlgorithmException {
            Provider.init();
            logger.info(Cipher.getInstance("ChaCha20-IETF-Poly1305").getAlgorithm());
        }

        @Test(expected = NoSuchAlgorithmException.class)
        public void error_4() throws NoSuchPaddingException, NoSuchAlgorithmException {
            Provider.init();
            logger.info(Cipher.getInstance("XChaCha20-IETF-Poly1305").getAlgorithm());
        }
    }

}
