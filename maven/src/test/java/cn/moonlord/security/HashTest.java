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
            Assert.assertEquals("success_1", (byte) 0xAA, result[0]);
            Assert.assertEquals("success_1", (byte) 0xFA, result[1]);
            Assert.assertEquals("success_1", (byte) 0x37, result[2]);
            Assert.assertEquals("success_1", (byte) 0xF3, result[29]);
            Assert.assertEquals("success_1", (byte) 0xF2, result[30]);
            Assert.assertEquals("success_1", (byte) 0x5D, result[31]);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha256((byte[]) null);
        }
    }

    public static class sha256_2 {
        @Test
        public void success_1() {
            String source = "0123456789ABC";
            byte[] result = Hash.sha256(source);
            Assert.assertEquals("success_1", 32, result.length);
            Assert.assertEquals("success_1", (byte) 0x2D, result[0]);
            Assert.assertEquals("success_1", (byte) 0xB6, result[1]);
            Assert.assertEquals("success_1", (byte) 0x6F, result[2]);
            Assert.assertEquals("success_1", (byte) 0x1B, result[29]);
            Assert.assertEquals("success_1", (byte) 0xA5, result[30]);
            Assert.assertEquals("success_1", (byte) 0xAB, result[31]);
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
            Assert.assertEquals("success_1", "aafa373bf008a855815ecb37d8bd52f6a8157cb5833c58edde6d530dbcf3f25d", result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha256Hex((byte[]) null);
        }
    }

    public static class sha256Hex_2 {
        @Test
        public void success_1() {
            String source = "0123456789ABC";
            String result = Hash.sha256Hex(source);
            Assert.assertEquals("success_1", 64, result.length());
            Assert.assertEquals("success_1", "2db66fad972737e98a9a1c70a37d4245215b2ac8b7eccf6dac5738f6b31ba5ab", result);
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
            Assert.assertEquals("success_1", (byte) 0xFA, result[0]);
            Assert.assertEquals("success_1", (byte) 0x54, result[1]);
            Assert.assertEquals("success_1", (byte) 0xA4, result[2]);
            Assert.assertEquals("success_1", (byte) 0x1B, result[61]);
            Assert.assertEquals("success_1", (byte) 0x45, result[62]);
            Assert.assertEquals("success_1", (byte) 0x36, result[63]);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha512((byte[]) null);
        }
    }

    public static class sha512_2 {
        @Test
        public void success_1() {
            String source = "0123456789ABC";
            byte[] result = Hash.sha512(source);
            Assert.assertEquals("success_1", 64, result.length);
            Assert.assertEquals("success_1", (byte) 0x0C, result[0]);
            Assert.assertEquals("success_1", (byte) 0x93, result[1]);
            Assert.assertEquals("success_1", (byte) 0x6E, result[2]);
            Assert.assertEquals("success_1", (byte) 0x7B, result[61]);
            Assert.assertEquals("success_1", (byte) 0xC9, result[62]);
            Assert.assertEquals("success_1", (byte) 0x82, result[63]);
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
            Assert.assertEquals("success_1", "fa54a45b24841a39ca6a3bab95d29ffdba8561b93d94f06f734a7a09c2370b79b2dd3b4efaf01eb3899206594e41cb5844031410d8ceee5ca4aa79c5461b4536", result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha512Hex((byte[]) null);
        }
    }

    public static class sha512Hex_2 {
        @Test
        public void success_1() {
            String source = "0123456789ABC";
            String result = Hash.sha512Hex(source);
            Assert.assertEquals("success_1", 128, result.length());
            Assert.assertEquals("success_1", "0c936e896d9165d8b8933f3ee6559807af286ba1b473421b83fc0f6412d9964ae4fb3da0e306eff80fb017af9ab4eec42b0422c7e368753c43f4f16a977bc982", result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha512Hex((String) null);
        }
    }

}
