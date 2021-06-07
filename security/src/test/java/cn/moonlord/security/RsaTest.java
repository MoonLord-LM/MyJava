package cn.moonlord.security;

import cn.moonlord.test.Performance;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

@SpringBootTest
@RunWith(Enclosed.class)
public class RsaTest {

    public static Logger logger = LoggerFactory.getLogger(RsaTest.class);

    private static volatile KeyPair keyPair = null;

    private static volatile PublicKey publicKey = null;

    private static volatile PrivateKey privateKey = null;

    static {
        init();
    }

    public static synchronized void init(){
    }

    public static class generateKeyPair {
        @Test
        public void success_1() {
            new Performance(32) {
                @Override
                public void onStarted() {
                    logger.info("begin generateKeyPair");
                }
                @Override
                public void testMethod() {
                    keyPair = Rsa.generateKeyPair();
                }
                @Override
                public void onCompleted() {
                    logger.info("end generateKeyPair, cost time: {} ms", getTestMethodRunTime());
                }
            }.run();

            privateKey = Rsa.getPrivateKey(keyPair);
            publicKey = Rsa.getPublicKey(keyPair);
            logger.info("privateKey: " + privateKey.getAlgorithm() + " length [ "+ privateKey.getEncoded().length + " ] format [ " + privateKey.getFormat() + " ]");
            logger.info("publicKey: " + publicKey.getAlgorithm() + " length [ " + publicKey.getEncoded().length + " ] format [ " + publicKey.getFormat() + " ]");
            logger.info("privateKey: " + Arrays.toString(privateKey.getEncoded()));
            logger.info("publicKey: " + Arrays.toString(publicKey.getEncoded()));
            logger.info("privateKey: " + Base64.encode(privateKey.getEncoded()));
            logger.info("publicKey: " + Base64.encode(publicKey.getEncoded()));
            // Assert.assertEquals("success_1", 1958, publicKey.getEncoded().length);
            // TODO Assert.assertEquals("success_1", 8712, privateKey.getEncoded().length);
            // 8709、8712、8710
            byte[] result = Rsa.encrypt("", publicKey);
            logger.info("result" + Base64.encode(result));
            byte[] source = Rsa.decrypt(result, privateKey);
            logger.info("source" + Base64.encode(source));
        }
    }

}
