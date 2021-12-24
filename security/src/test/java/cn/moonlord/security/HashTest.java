package cn.moonlord.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

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
            Assert.assertEquals(32, result.length);
            Assert.assertArrayEquals(compare, result);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33};
            byte[] result = Hash.sha256(source);
            byte[] compare = DigestUtils.sha256(source);
            Assert.assertEquals(32, result.length);
            Assert.assertArrayEquals(compare, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha256(null);
        }
    }

    public static class sha256Hex {
        @Test
        public void success_1() {
            byte[] source = new byte[0];
            String result = Hash.sha256Hex(source);
            String compare = DigestUtils.sha256Hex(source);
            Assert.assertEquals(64, result.length());
            Assert.assertEquals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", result);
            Assert.assertEquals(compare, result);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33};
            String result = Hash.sha256Hex(source);
            String compare = DigestUtils.sha256Hex(source);
            Assert.assertEquals(64, result.length());
            Assert.assertEquals("aafa373bf008a855815ecb37d8bd52f6a8157cb5833c58edde6d530dbcf3f25d", result);
            Assert.assertEquals(compare, result);
        }

        @Test
        public void success_3() {
            byte[] source = "测试".getBytes(StandardCharsets.UTF_8);
            String result = Hash.sha256Hex(source);
            String compare = DigestUtils.sha256Hex(source);
            Assert.assertEquals(64, result.length());
            Assert.assertEquals("6aa8f49cc992dfd75a114269ed26de0ad6d4e7d7a70d9c8afb3d7a57a88a73ed", result);
            Assert.assertEquals(compare, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha256Hex(null);
        }
    }

    public static class sha512 {
        @Test
        public void success_1() {
            byte[] source = new byte[0];
            byte[] result = Hash.sha512(source);
            byte[] compare = DigestUtils.sha512(source);
            Assert.assertEquals(64, result.length);
            Assert.assertArrayEquals(compare, result);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33};
            byte[] result = Hash.sha512(source);
            byte[] compare = DigestUtils.sha512(source);
            Assert.assertEquals(64, result.length);
            Assert.assertArrayEquals(compare, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha512(null);
        }
    }

    public static class sha512Hex {
        @Test
        public void success_1() {
            byte[] source = new byte[0];
            String result = Hash.sha512Hex(source);
            String compare = DigestUtils.sha512Hex(source);
            Assert.assertEquals(128, result.length());
            Assert.assertEquals("cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e", result);
            Assert.assertEquals(compare, result);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33};
            String result = Hash.sha512Hex(source);
            String compare = DigestUtils.sha512Hex(source);
            Assert.assertEquals(128, result.length());
            Assert.assertEquals("fa54a45b24841a39ca6a3bab95d29ffdba8561b93d94f06f734a7a09c2370b79b2dd3b4efaf01eb3899206594e41cb5844031410d8ceee5ca4aa79c5461b4536", result);
            Assert.assertEquals(compare, result);
        }

        @Test
        public void success_3() {
            byte[] source = "测试".getBytes(StandardCharsets.UTF_8);
            String result = Hash.sha512Hex(source);
            String compare = DigestUtils.sha512Hex(source);
            Assert.assertEquals(128, result.length());
            Assert.assertEquals("98fb26ea83ce0f08918c967392a26ab298740aff3c18d032983b88bcee2e16d152ef372778259ebd529ed01701ff01ac4c95ed94e3a1ab9272ab98daf11f076c", result);
            Assert.assertEquals(compare, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Hash.sha512Hex(null);
        }
    }

}
