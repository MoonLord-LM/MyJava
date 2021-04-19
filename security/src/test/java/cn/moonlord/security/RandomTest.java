package cn.moonlord.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Enclosed.class)
public class RandomTest {

    public static class generate {
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

    public static class generateBytes {
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

}
