package cn.moonlord.security;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

@SpringBootTest
@RunWith(Enclosed.class)
public class RsaTest {

    public static class getPublicKey {
        @Test
        public void success_1() throws Exception {
            KeyPair keyPair = Rsa.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            System.out.println(publicKey.getPublicExponent().toString(10)); // 65537
            System.out.println(publicKey.getModulus().toString(10)); // 1233 位
        }
    }

}
