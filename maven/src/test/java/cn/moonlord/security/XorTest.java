package cn.moonlord.security;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class XorTest {

    @Test
    public void test_1000() {
        byte[] source = new byte[] {(byte) 0xFF};
        byte result = Xor.sum(source);
        Assert.assertEquals("Xor.sum", 0xFF, result);
    }
    @Test
    public void test_1001() {
        byte[] source = new byte[] {(byte) 0xFF, (byte) 0x00};
        byte result = Xor.sum(source);
        Assert.assertEquals("Xor.sum", 0xFF, result);
    }
    @Test
    public void test_1002() {
        byte[] source = new byte[] {(byte) 0xFF, (byte) 0xFF};
        byte result = Xor.sum(source);
        Assert.assertEquals("Xor.sum", 0x00, result);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_1003() {
        byte[] source = null;
        Xor.sum(source);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_1004() {
        byte[] source = new byte[] { 0x01 };
        Xor.sum(source);
    }

}
