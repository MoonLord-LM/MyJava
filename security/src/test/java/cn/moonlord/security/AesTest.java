package cn.moonlord.security;

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

    public static class encryption {
        @Test
        public void success_1() {
            logger.info(Aes.generateKeyBase64String());
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() throws Exception {
            Aes.encrypt((byte[])null, (byte[])null);
        }
    }

}
