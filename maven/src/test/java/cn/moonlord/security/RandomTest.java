package cn.moonlord.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Enclosed.class)
public class RandomTest {

    public static class generateBytes_1 {
        @Test
        public void success_1() {
            byte[] result = Random.generateBytes(1);
            Assert.assertEquals("success_1", 1, result.length);
        }

        @Test
        public void success_2() {
            byte[] result = Random.generateBytes(1024);
            Assert.assertEquals("success_2", 1024, result.length);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Random.generateBytes(0);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            Random.generateBytes(-1);
        }
    }

    public static class generateBytes_2 {
        @Test
        public void success_1() {
            byte[] result = Random.generateBytes(1L);
            Assert.assertEquals("success_1", 1L, result.length);
        }

        @Test
        public void success_2() {
            byte[] result = Random.generateBytes(1024L);
            Assert.assertEquals("success_2", 1024L, result.length);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Random.generateBytes(0L);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            Random.generateBytes(-1L);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            Random.generateBytes((long) Integer.MAX_VALUE + 1);
        }
    }

    public static class generate_1 {
        @Test
        public void success_1() {
            byte[] result = Random.generate(8);
            Assert.assertEquals("success_1", 1, result.length);
        }

        @Test
        public void success_2() {
            byte[] result = Random.generate(8 * 1024);
            Assert.assertEquals("success_2", 1024, result.length);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Random.generate(0);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            Random.generate(-1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            Random.generate(1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_4() {
            Random.generate(1024 + 1);
        }
    }

    public static class generate_2 {
        @Test
        public void success_1() {
            byte[] result = Random.generate(8L);
            Assert.assertEquals("success_1", 1L, result.length);
        }

        @Test
        public void success_2() {
            byte[] result = Random.generate(8L * 1024L);
            Assert.assertEquals("success_2", 1024L, result.length);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Random.generate(0L);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            Random.generate(-1L);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            Random.generate(1L);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_4() {
            Random.generate(1024L + 1L);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_5() {
            Random.generate((long) Integer.MAX_VALUE * (long) Byte.SIZE + 1L);
        }
    }

    public static class generateBase64String_1 {
        @Test
        public void success_1() {
            String result = Random.generateBase64String(8);
            Assert.assertEquals("success_1", 4, result.length());
        }

        @Test
        public void success_2() {
            String result = Random.generateBase64String(8 * 1024);
            Assert.assertEquals("success_2", 1368, result.length());
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Random.generateBase64String(0);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            Random.generateBase64String(-1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            Random.generateBase64String(1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_4() {
            Random.generateBase64String(1024 + 1);
        }
    }

    public static class generateBase64String_2 {
        @Test
        public void success_1() {
            String result = Random.generateBase64String(8L);
            Assert.assertEquals("success_1", 4L, result.length());
        }

        @Test
        public void success_2() {
            String result = Random.generateBase64String(8L * 1024L);
            Assert.assertEquals("success_2", 1368L, result.length());
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Random.generateBase64String(0L);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            Random.generateBase64String(-1L);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            Random.generateBase64String(1L);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_4() {
            Random.generateBase64String(1024L + 1L);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_5() {
            Random.generateBase64String((long) Integer.MAX_VALUE * (long) Byte.SIZE + 1L);
        }
    }

}
