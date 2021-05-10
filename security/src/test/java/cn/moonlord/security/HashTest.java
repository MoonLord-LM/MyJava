package cn.moonlord.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Enclosed.class)
public class HashTest {

    public static Logger logger = LoggerFactory.getLogger(HashTest.class);

    public static class sha256 {
        @Test
        public void success_1() {
            byte[] source = new byte[0];
            byte[] result = Hash.sha256(source);
            byte[] compare = DigestUtils.sha256(source);
            Assert.assertEquals("success_1", 32, result.length);
            Assert.assertArrayEquals("success_1", compare, result);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33};
            byte[] result = Hash.sha256(source);
            byte[] compare = DigestUtils.sha256(source);
            Assert.assertEquals("success_2", 32, result.length);
            Assert.assertArrayEquals("success_2", compare, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha256((byte[]) null);
        }
    }

    public static class sha256Hex {
        @Test
        public void success_1() {
            byte[] source = new byte[0];
            String result = Hash.sha256Hex(source);
            String compare = DigestUtils.sha256Hex(source);
            Assert.assertEquals("success_1", 64, result.length());
            Assert.assertEquals("success_1", "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", result);
            Assert.assertEquals("success_1", compare, result);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33};
            String result = Hash.sha256Hex(source);
            String compare = DigestUtils.sha256Hex(source);
            Assert.assertEquals("success_2", 64, result.length());
            Assert.assertEquals("success_2", "aafa373bf008a855815ecb37d8bd52f6a8157cb5833c58edde6d530dbcf3f25d", result);
            Assert.assertEquals("success_2", compare, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha256Hex((byte[]) null);
        }
    }

    public static class sha512 {
        @Test
        public void success_1() {
            byte[] source = new byte[0];
            byte[] result = Hash.sha512(source);
            byte[] compare = DigestUtils.sha512(source);
            Assert.assertEquals("success_1", 64, result.length);
            Assert.assertArrayEquals("success_1", compare, result);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33};
            byte[] result = Hash.sha512(source);
            byte[] compare = DigestUtils.sha512(source);
            Assert.assertEquals("success_2", 64, result.length);
            Assert.assertArrayEquals("success_2", compare, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha512((byte[]) null);
        }
    }

    public static class sha512Hex {
        @Test
        public void success_1() {
            byte[] source = new byte[0];
            String result = Hash.sha512Hex(source);
            String compare = DigestUtils.sha512Hex(source);
            Assert.assertEquals("success_1", 128, result.length());
            Assert.assertEquals("success_1", "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e", result);
            Assert.assertEquals("success_1", compare, result);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33};
            String result = Hash.sha512Hex(source);
            String compare = DigestUtils.sha512Hex(source);
            Assert.assertEquals("success_2", 128, result.length());
            Assert.assertEquals("success_2", "fa54a45b24841a39ca6a3bab95d29ffdba8561b93d94f06f734a7a09c2370b79b2dd3b4efaf01eb3899206594e41cb5844031410d8ceee5ca4aa79c5461b4536", result);
            Assert.assertEquals("success_2", compare, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha512Hex((byte[]) null);
        }
    }

}
