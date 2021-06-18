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

@SpringBootTest
@RunWith(Enclosed.class)
public class ProviderTest {

    public static Logger logger = LoggerFactory.getLogger(ProviderTest.class);

    public static class showAllProviders {
        @Test
        public void success_1() {
            logger.info("showAllProviders" + "\r\n" + Provider.showAllProviders() + "\r\n");
        }
    }

    public static class showAllElements {
        @Test
        public void success_1() {
            logger.info("showAllElements" + "\r\n" + Provider.showAllElements() + "\r\n");
        }
    }

    public static class showMessageDigests {
        @Test
        public void success_1() {
            logger.info("showMessageDigests" + "\r\n" + Provider.showMessageDigests() + "\r\n");
        }
    }

    public static class showSignatures {
        @Test
        public void success_1() {
            logger.info("showSignatures" + "\r\n" + Provider.showSignatures() + "\r\n");
        }
    }

    public static class showSecretKeyFactorys {
        @Test
        public void success_1() {
            logger.info("showSecretKeyFactorys" + "\r\n" + Provider.showSecretKeyFactorys() + "\r\n");
        }
    }

    public static class showPBKDF2s {
        @Test
        public void success_1() {
            logger.info("showPBKDF2s" + "\r\n" + Provider.showPBKDF2s() + "\r\n");
        }
    }

    public static class showSecureRandoms {
        @Test
        public void success_1() {
            logger.info("showSecureRandoms" + "\r\n" + Provider.showSecureRandoms() + "\r\n");
        }
    }

    public static class showCiphers {
        @Test
        public void success_1() {
            logger.info("showCiphers" + "\r\n" + Provider.showCiphers() + "\r\n");
        }
    }

    public static class showAesCiphers {
        @Test
        public void success_1() {
            logger.info("showAesCiphers" + "\r\n" + Provider.showAesCiphers() + "\r\n");
        }

        @Test()
        public void success_2() throws NoSuchPaddingException, NoSuchAlgorithmException {
            Provider.init();
            logger.info(Cipher.getInstance("AES/CBC/PKCS7Padding").toString());
            logger.info(Cipher.getInstance("AES/CBC/PKCS7Padding").getAlgorithm());
            logger.info(Cipher.getInstance("AES/CBC/PKCS7Padding").getProvider().getName());
            logger.info(Cipher.getInstance("AES/CBC/PKCS7Padding").getProvider().getInfo());
        }

        @Test(expected = NoSuchAlgorithmException.class)
        public void error_1() throws NoSuchPaddingException, NoSuchAlgorithmException {
            Provider.destroy();
            logger.info(Cipher.getInstance("AES/CBC/PKCS7Padding").getAlgorithm());
        }
    }

    public static class showChaChaCiphers {
        @Test
        public void success_1() {
            logger.info("showChaChaCiphers" + "\r\n" + Provider.showChaChaCiphers() + "\r\n");
        }

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
