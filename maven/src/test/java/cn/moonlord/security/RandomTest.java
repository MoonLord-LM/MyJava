package cn.moonlord.security;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RandomTest {

    @Test
    public void test_1000() {
        Random.generateBytes(1);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_1001() {
        Random.generateBytes(0);
    }

    @Test
    public void test_2000() {
        Random.generateBytes(1024);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_2001() {
        Random.generateBytes((long) Integer.MAX_VALUE + 1);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_2002() {
        Random.generateBytes(0);
    }

    @Test
    public void test_3000() {
        Random.generate(1024);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_3001() {
        Random.generate(1);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_3003() {
        Random.generate(0);
    }

    @Test
    public void test_4000() {
        Random.generate(8L);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_4001() {
        Random.generate(1L);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_4002() {
        Random.generate((long) Integer.MAX_VALUE * (long) Byte.SIZE + 1);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_4003() {
        Random.generate(0L);
    }

    @Test
    public void test_5000() {
        Random.generateBase64String(1024);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_5001() {
        Random.generateBase64String(1);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_5003() {
        Random.generateBase64String(0);
    }

    @Test
    public void test_6000() {
        Random.generateBase64String(8L);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_6001() {
        Random.generateBase64String(1L);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_6002() {
        Random.generateBase64String((long) Integer.MAX_VALUE * (long) Byte.SIZE + 1);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_6003() {
        Random.generateBase64String(0L);
    }

}
