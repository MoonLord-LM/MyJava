package cn.moonlord.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.PrivateKey;
import java.security.PublicKey;

@SpringBootTest
@RunWith(Enclosed.class)
public class PemTest {

    public static Logger logger = LoggerFactory.getLogger(PemTest.class);

    private static final PublicKey publicKey = Rsa.getPublicKey("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");

    private static final PrivateKey privateKey = Rsa.getPrivateKey("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");

    public static class encodePublicKey {
        @Test
        public void success_1() {
            String result = Pem.encodePublicKey(publicKey);
            logger.info("encodePublicKey" + "\r\n" + result);
        }
    }

    public static class encodePrivateKey {
        @Test
        public void success_1() {
            String result = Pem.encodePrivateKey(privateKey);
            logger.info("encodePrivateKey" + "\r\n" + result);
        }
    }

    public static class encodeRsaPublicKey {
        @Test
        public void success_1() {
            String result = Pem.encodeRsaPublicKey(publicKey);
            logger.info("encodeRsaPublicKey" + "\r\n" + result);
        }
    }

    public static class encodeRsaPrivateKey {
        @Test
        public void success_1() {
            String result = Pem.encodeRsaPrivateKey(privateKey);
            logger.info("encodeRsaPrivateKey" + "\r\n" + result);
        }
    }

    public static class decodeRsaPublicKey {
        @Test
        public void success_1() {
            PublicKey result = Pem.decodeRsaPublicKey(Pem.encodePublicKey(publicKey));
            logger.info("decodeRsaPublicKey" + "\r\n" + result);
            Assert.assertArrayEquals("success_1", publicKey.getEncoded(), result.getEncoded());
        }

        @Test
        public void success_2() {
            PublicKey result = Pem.decodeRsaPublicKey(Pem.encodeRsaPublicKey(publicKey));
            logger.info("decodeRsaPublicKey" + "\r\n" + result);
            Assert.assertArrayEquals("success_2", publicKey.getEncoded(), result.getEncoded());
        }
    }

    public static class decodeRsaPrivateKey {
        @Test
        public void success_1() {
            PrivateKey result = Pem.decodeRsaPrivateKey(Pem.encodePrivateKey(privateKey));
            logger.info("decodeRsaPrivateKey" + "\r\n" + result);
            Assert.assertArrayEquals("success_1", privateKey.getEncoded(), result.getEncoded());
        }

        @Test
        public void success_2() {
            PrivateKey result = Pem.decodeRsaPrivateKey(Pem.encodeRsaPrivateKey(privateKey));
            logger.info("decodeRsaPrivateKey" + "\r\n" + result);
            Assert.assertArrayEquals("success_2", privateKey.getEncoded(), result.getEncoded());
        }
    }

}
