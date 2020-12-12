package cn.moonlord.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Enclosed.class)
public class XorTest {

    public static class sum {
        @Test
        public void success_1() {
            byte[] source = new byte[]{(byte) 0xFF};
            byte result = Xor.sum(source);
            Assert.assertEquals("success_1", (byte) 0xFF, result);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0xFF};
            byte result = Xor.sum(source);
            Assert.assertEquals("success_2", (byte) 0xFF, result);
        }

        @Test
        public void success_3() {
            byte[] source = new byte[]{(byte) 0xFF, (byte) 0xFF};
            byte result = Xor.sum(source);
            Assert.assertEquals("success_3", (byte) 0x00, result);
        }

        @Test
        public void success_4() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0xFF};
            byte result = Xor.sum(source);
            Assert.assertEquals("success_4", (byte) 0xFF, result);
        }

        @Test
        public void success_5() {
            byte[] source = new byte[]{(byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0xFF};
            byte result = Xor.sum(source);
            Assert.assertEquals("success_5", (byte) 0x00, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Xor.sum(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            byte[] source = new byte[0];
            Xor.sum(source);
        }
    }

    public static class merge {
        @Test
        public void success_1() {
            byte[] source1 = new byte[]{(byte) 0xFF};
            byte[] source2 = new byte[]{(byte) 0xFF};
            byte[] result = Xor.merge(source1, source2);
            Assert.assertEquals("success_1", source1.length, result.length);
            Assert.assertEquals("success_1", (byte) 0x00, result[0]);
        }

        @Test
        public void success_2() {
            byte[] source1 = new byte[]{(byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0xFF};
            byte[] source2 = new byte[]{(byte) 0x00, (byte) 0xFF, (byte) 0x00, (byte) 0xFF};
            byte[] result = Xor.merge(source1, source2);
            Assert.assertEquals("success_2", source1.length, result.length);
            Assert.assertEquals("success_2", (byte) 0xFF, result[0]);
            Assert.assertEquals("success_2", (byte) 0xFF, result[1]);
            Assert.assertEquals("success_2", (byte) 0x00, result[2]);
            Assert.assertEquals("success_2", (byte) 0x00, result[3]);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            byte[] source2 = new byte[1];
            Xor.merge(null, source2);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            byte[] source1 = new byte[1];
            Xor.merge(source1, null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            byte[] source1 = new byte[0];
            byte[] source2 = new byte[1];
            Xor.merge(source1, source2);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_4() {
            byte[] source1 = new byte[1];
            byte[] source2 = new byte[0];
            Xor.merge(source1, source2);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_5() {
            byte[] source1 = new byte[1];
            byte[] source2 = new byte[2];
            Xor.merge(source1, source2);
        }
    }

}
