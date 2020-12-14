package cn.moonlord.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Enclosed.class)
public class HashTest {

    public static class sha256_1 {
        @Test
        public void success_1() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33};
            byte[] result = Hash.sha256(source);
            Assert.assertEquals("success_1", 32, result.length);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha256((byte[]) null);
        }
    }

    public static class sha256_2 {
        @Test
        public void success_1() {
            String source = "00112233";
            byte[] result = Hash.sha256(source);
            Assert.assertEquals("success_1", 32, result.length);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha256((String) null);
        }
    }

    public static class sha256Hex_1 {
        @Test
        public void success_1() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33};
            String result = Hash.sha256Hex(source);
            Assert.assertEquals("success_1", 64, result.length());
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha256Hex((byte[]) null);
        }
    }

    public static class sha256Hex_2 {
        @Test
        public void success_1() {
            String source = "00112233";
            String result = Hash.sha256Hex(source);
            Assert.assertEquals("success_1", 64, result.length());
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha256Hex((String) null);
        }
    }

    public static class sha512_1 {
        @Test
        public void success_1() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33};
            byte[] result = Hash.sha512(source);
            Assert.assertEquals("success_1", 64, result.length);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha512((byte[]) null);
        }
    }

    public static class sha512_2 {
        @Test
        public void success_1() {
            String source = "00112233";
            byte[] result = Hash.sha512(source);
            Assert.assertEquals("success_1", 64, result.length);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha512((String) null);
        }
    }

    public static class sha512Hex_1 {
        @Test
        public void success_1() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33};
            String result = Hash.sha512Hex(source);
            Assert.assertEquals("success_1", 128, result.length());
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha512Hex((byte[]) null);
        }
    }

    public static class sha512Hex_2 {
        @Test
        public void success_1() {
            String source = "00112233";
            String result = Hash.sha512Hex(source);
            Assert.assertEquals("success_1", 128, result.length());
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha512Hex((String) null);
        }
    }

}
