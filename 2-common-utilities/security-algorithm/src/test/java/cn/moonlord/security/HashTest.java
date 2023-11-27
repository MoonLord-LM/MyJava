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

    public static class Sha256Test {
        @Test
        public void sha256() {
            Assert.assertThrows(IllegalArgumentException.class, () -> Hash.sha256(null));
            Assert.assertThrows(IllegalArgumentException.class, () -> Hash.sha256Hex(null));

            Hash.sha256(new byte[0]);
            Hash.sha256Hex(new byte[0]);
            Provider.removeSunProvider();
            Provider.removeBouncyCastleProvider();
            Assert.assertThrows(IllegalArgumentException.class, () -> Hash.sha256(new byte[0]));
            Assert.assertThrows(IllegalArgumentException.class, () -> Hash.sha256Hex(new byte[0]));
            Provider.addSunProvider();
            Provider.addBouncyCastleProvider();
            Hash.sha256(new byte[0]);
            Hash.sha256Hex(new byte[0]);

            byte[] source1 = new byte[0];
            Assert.assertArrayEquals(DigestUtils.sha256(source1), Hash.sha256(source1));
            Assert.assertEquals(DigestUtils.sha256Hex(source1), Hash.sha256Hex(source1));
            Assert.assertEquals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", Hash.sha256Hex(source1));

            byte[] source2 = "测试".getBytes(StandardCharsets.UTF_8);
            Assert.assertArrayEquals(DigestUtils.sha256(source2), Hash.sha256(source2));
            Assert.assertEquals(DigestUtils.sha256Hex(source2), Hash.sha256Hex(source2));
            Assert.assertEquals("6aa8f49cc992dfd75a114269ed26de0ad6d4e7d7a70d9c8afb3d7a57a88a73ed", Hash.sha256Hex(source2));

            byte[] source3 = Random.generateBytes(1024);
            Assert.assertArrayEquals(DigestUtils.sha256(source3), Hash.sha256(source3));
        }
    }

    public static class Sha512Test {
        @Test
        public void sha512() {
            Assert.assertThrows(IllegalArgumentException.class, () -> Hash.sha512(null));
            Assert.assertThrows(IllegalArgumentException.class, () -> Hash.sha512Hex(null));

            Hash.sha512(new byte[0]);
            Hash.sha512Hex(new byte[0]);
            Provider.removeSunProvider();
            Provider.removeBouncyCastleProvider();
            Assert.assertThrows(IllegalArgumentException.class, () -> Hash.sha512(new byte[0]));
            Assert.assertThrows(IllegalArgumentException.class, () -> Hash.sha512Hex(new byte[0]));
            Provider.addSunProvider();
            Provider.addBouncyCastleProvider();
            Hash.sha512(new byte[0]);
            Hash.sha512Hex(new byte[0]);

            byte[] source1 = new byte[0];
            Assert.assertArrayEquals(DigestUtils.sha512(source1), Hash.sha512(source1));
            Assert.assertEquals(DigestUtils.sha512Hex(source1), Hash.sha512Hex(source1));
            Assert.assertEquals("cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e", Hash.sha512Hex(source1));

            byte[] source2 = "测试".getBytes(StandardCharsets.UTF_8);
            Assert.assertArrayEquals(DigestUtils.sha512(source2), Hash.sha512(source2));
            Assert.assertEquals(DigestUtils.sha512Hex(source2), Hash.sha512Hex(source2));
            Assert.assertEquals("98fb26ea83ce0f08918c967392a26ab298740aff3c18d032983b88bcee2e16d152ef372778259ebd529ed01701ff01ac4c95ed94e3a1ab9272ab98daf11f076c", Hash.sha512Hex(source2));

            byte[] source3 = Random.generateBytes(1024);
            Assert.assertArrayEquals(DigestUtils.sha512(source3), Hash.sha512(source3));
        }
    }

}
