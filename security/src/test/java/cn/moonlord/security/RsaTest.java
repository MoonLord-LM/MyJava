package cn.moonlord.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@SpringBootTest
@RunWith(Enclosed.class)
public class RsaTest {

    public static Logger logger = LoggerFactory.getLogger(RsaTest.class);

    public static class generateKeyPair {
        @Test
        public void success_1() {
            KeyPair keyPair = Rsa.generateKeyPair();
            PublicKey publicKey = Rsa.getPublicKey(keyPair);
            PrivateKey privateKey = Rsa.getPrivateKey(keyPair);
            logger.info("publicKey: " + publicKey.getAlgorithm() + " length [ " + publicKey.getEncoded().length + " ] format " + publicKey.getFormat());
            logger.info("privateKey: " + privateKey.getAlgorithm() + " length [ "+ privateKey.getEncoded().length + " ] format " + privateKey.getFormat());
            Assert.assertEquals("success_1", 1958, publicKey.getEncoded().length);
            // TODO Assert.assertEquals("success_1", 8712, privateKey.getEncoded().length);
            // 8709、8712
        }
    }

}
