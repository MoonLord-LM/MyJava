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
    public void test_1001() throws Exception {
        Random.generateBytes(0);
    }

    @Test
    public void test_2000() {
        Random.generateBytes(1024);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_2001() throws Exception {
        Random.generateBytes((long) Integer.MAX_VALUE + 1);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_2002() throws Exception {
        Random.generateBytes(0);
    }

    @Test
    public void test_3000() {
        Random.generate(1024);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_3001() throws Exception {
        Random.generate(1);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_3003() throws Exception {
        Random.generate(0);
    }

    @Test
    public void test_4000() {
        Random.generate(8L);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_4001() throws Exception {
        Random.generate(1L);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_4002() throws Exception {
        Random.generate((long) Integer.MAX_VALUE * (long) Byte.SIZE + 1);
    }
    @Test(expected= IllegalArgumentException.class)
    public void test_4003() throws Exception {
        Random.generate(0L);
    }

}
