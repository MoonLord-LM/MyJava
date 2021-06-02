package cn.moonlord.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Enclosed.class)
public class AesTest {

    public static Logger logger = LoggerFactory.getLogger(AesTest.class);

    public static class generateKey {
        @Test
        public void success_1() {
            byte[] result = Aes.generateKey().getEncoded();
            Assert.assertEquals("success_1", 32, result.length);
        }
    }

    public static class generateKeyBytes {
        @Test
        public void success_1() {
            byte[] result = Aes.generateKeyBytes();
            Assert.assertEquals("success_1", 32, result.length);
        }
    }

    public static class generateKeyBase64String {
        @Test
        public void success_1() {
            byte[] result = Base64.decode(Aes.generateKeyBase64String());
            Assert.assertEquals("success_1", 32, result.length);
        }
    }

    public static class getSecretKey {
        @Test
        public void success_1() {
            Aes.getSecretKey(Aes.generateKeyBytes());
        }

        @Test
        public void success_2() {
            Aes.getSecretKey(Aes.generateKeyBase64String());
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Aes.getSecretKey((byte[]) null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            Aes.getSecretKey(new byte[0]);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            Aes.getSecretKey(new byte[]{(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33});
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_4() {
            Aes.getSecretKey((String) null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_5() {
            Aes.getSecretKey("");
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_6() {
            Aes.getSecretKey("AAAAAAAA");
        }
    }

}
