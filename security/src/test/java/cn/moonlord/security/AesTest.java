package cn.moonlord.security;

import com.alibaba.fastjson.JSON;
import net.minidev.json.JSONObject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.Cipher;
import java.security.Provider;
import java.security.Security;
import java.util.Enumeration;
import java.util.Locale;

@SpringBootTest
@RunWith(Enclosed.class)
public class AesTest {

    public static Logger logger = LoggerFactory.getLogger(AesTest.class);

    public static class encryption {
        @Test
        public void success_1() {
            logger.info(Aes.generateKeyBase64String());
        }

        @Test
        public void test() throws Exception {
            Security.addProvider(new BouncyCastleProvider());
            Provider[] providers = Security.getProviders();
            for (String s : Security.getAlgorithms("MessageDigest")) {
                //System.out.println("MessageDigest：" + s);
            }
            for (String s : Security.getAlgorithms("Signature")) {
                //System.out.println("Signature：" + s);
            }
            for (String s : Security.getAlgorithms("Cipher")) {
                //System.out.println("Cipher：" + s);
            }

            Cipher cipher1 = Cipher.getInstance("XChaCha20-IETF-Poly1305");
            logger.info("test：" + cipher1.getAlgorithm());
            Cipher cipher2 = Cipher.getInstance("AES/CBC/PKCS7Padding");
            logger.info("test：" + cipher2.getAlgorithm());
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() throws Exception {
            Aes.encrypt((byte[])null, (byte[])null);
        }
    }

}
