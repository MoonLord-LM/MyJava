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

    public static class showAllMessageDigests {
        @Test
        public void success_1() {
            logger.info("showAllMessageDigests" + "\r\n" + Provider.showAllMessageDigests() + "\r\n");
        }
    }

    public static class showAllSignatures {
        @Test
        public void success_1() {
            logger.info("showAllSignatures" + "\r\n" + Provider.showAllSignatures() + "\r\n");
        }
    }

    public static class showAllCiphers {
        @Test
        public void success_1() {
            logger.info("showAllCiphers" + "\r\n" + Provider.showAllCiphers() + "\r\n");
        }
    }

    public static class showAesCiphers {
        @Test
        public void success_1() {
            logger.info("showAesCiphers" + "\r\n" + Provider.showAesCiphers() + "\r\n");
        }

        @Test(expected = NoSuchAlgorithmException.class)
        public void error_1() throws NoSuchPaddingException, NoSuchAlgorithmException {
            Provider.init();
            Cipher cipher2 = Cipher.getInstance("AES/CBC/PKCS7Padding");
            logger.info("test：" + cipher2.getAlgorithm());
        }

        @Test(expected = NoSuchAlgorithmException.class)
        public void error_2() throws NoSuchPaddingException, NoSuchAlgorithmException {
            Provider.init();
            Cipher cipher1 = Cipher.getInstance("XChaCha20-IETF-Poly1305");
            logger.info("test：" + cipher1.getAlgorithm());
        }
    }

}
