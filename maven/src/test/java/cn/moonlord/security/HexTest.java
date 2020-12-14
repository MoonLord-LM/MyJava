package cn.moonlord.security;

import org.apache.commons.codec.DecoderException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Enclosed.class)
public class HexTest {

    public static Logger logger = LoggerFactory.getLogger(HexTest.class);

    public static class encode {
        @Test
        public void success_1() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0xDD, (byte) 0x11, (byte) 0xEE, (byte) 0x22, (byte) 0xFF};
            String result = Hex.encode(source);
            Assert.assertEquals("success_1", "00dd11ee22ff", result);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
            String result = Hex.encode(source);
            Assert.assertEquals("success_2", "000000000000", result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hex.encode(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            byte[] source = new byte[0];
            Hex.encode(source);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            byte[] source = new byte[Integer.MAX_VALUE / 2 + 1];
            Hex.encode(source);
        }
    }

    public static class decode {
        @Test
        public void performance_1() throws DecoderException {
            String source = Hex.encode(new byte[1024 * 1024 * 100]);
            logger.info("begin decode");
            for (int i = 0; i < 5; i++) {
                Hex.decode(source);
            }
            logger.info("end decode");
            logger.info("begin codec");
            for (int i = 0; i < 5; i++) {
                org.apache.commons.codec.binary.Hex.decodeHex(source);
            }
            logger.info("end codec");
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            String source = null;
            Hex.decode(source);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            String source = "";
            Hex.decode(source);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            String source = "123";
            Hex.decode(source);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_4() {
            String source = "GH";
            byte[] result = Hex.decode(source);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_5() {
            String source = "!@";
            byte[] result = Hex.decode(source);
        }
    }

}
