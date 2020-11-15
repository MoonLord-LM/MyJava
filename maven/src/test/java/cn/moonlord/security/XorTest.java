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
        Assert.assertEquals("Xor.sum", (byte) 0xFF, result);
    }
    @Test
    public void test_1001() {
        byte[] source = new byte[] {(byte) 0xFF, (byte) 0x00};
        byte result = Xor.sum(source);
        Assert.assertEquals("Xor.sum", (byte) 0xFF, result);
    }
    @Test
    public void test_1002() {
        byte[] source = new byte[] {(byte) 0xFF, (byte) 0xFF};
        byte result = Xor.sum(source);
        Assert.assertEquals("Xor.sum", (byte) 0x00, result);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_1003() {
        Xor.sum(null);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_1004() {
        byte[] source = new byte[0];
        Xor.sum(source);
    }

    @Test
    public void test_2000() {
        byte[] source1 = new byte[] {(byte) 0xFF};
        byte[] source2 = new byte[] {(byte) 0xFF};
        byte[] result = Xor.merge(source1, source2);
        Assert.assertEquals("Xor.merge", source1.length, result.length);
        Assert.assertEquals("Xor.merge", (byte) 0x00, result[0]);
    }
    @Test
    public void test_2001() {
        byte[] source1 = new byte[] {(byte) 0xFF, (byte) 0x00};
        byte[] source2 = new byte[] {(byte) 0x00, (byte) 0xFF};
        byte[] result = Xor.merge(source1, source2);
        Assert.assertEquals("Xor.merge", source1.length, result.length);
        Assert.assertEquals("Xor.merge", (byte) 0xFF, result[0]);
        Assert.assertEquals("Xor.merge", (byte) 0xFF, result[1]);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_2002() {
        byte[] source2 = new byte[] {(byte) 0xFF};
        Xor.merge(null, source2);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_2003() {
        byte[] source1 = new byte[] {(byte) 0xFF};
        Xor.merge(source1, null);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_2004() {
        byte[] source1 = new byte[0];
        byte[] source2 = new byte[] {(byte) 0x00, (byte) 0xFF};
        Xor.merge(source1, source2);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_2005() {
        byte[] source1 = new byte[] {(byte) 0xFF, (byte) 0x00};
        byte[] source2 = new byte[0];
        Xor.merge(source1, source2);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_2006() {
        byte[] source1 = new byte[] {(byte) 0xFF, (byte) 0x00};
        byte[] source2 = new byte[] {(byte) 0x00, (byte) 0xFF, (byte) 0x00};
        Xor.merge(source1, source2);
    }

}
