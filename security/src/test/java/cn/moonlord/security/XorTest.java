package cn.moonlord.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Enclosed.class)
public class XorTest {

    public static Logger logger = LoggerFactory.getLogger(XorTest.class);

    public static class merge {
        @Test
        public void success_1() {
            byte[] source1 = new byte[]{(byte) 0xFF};
            byte[] source2 = new byte[]{(byte) 0xFF};
            byte[] result = Xor.merge(source1, source2);
            Assert.assertArrayEquals( new byte[]{ (byte) 0x00 }, result);
        }

        @Test
        public void success_2() {
            byte[] source1 = new byte[]{(byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0xFF, (byte) 0x00};
            byte[] source2 = new byte[]{(byte) 0x00, (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0xAA};
            byte[] result = Xor.merge(source1, source2);
            Assert.assertArrayEquals(new byte[]{ (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0xAA }, result);
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
            byte[] source1 = new byte[2];
            byte[] source2 = new byte[3];
            Xor.merge(source1, source2);
        }
    }

    public static class fold {
        @Test
        public void success_1() {
            byte[] source = new byte[]{(byte) 0xFF, (byte) 0xFF};
            byte[] result = Xor.fold(source);
            Assert.assertArrayEquals( new byte[]{ (byte) 0x00 }, result);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0xAA};
            byte[] result = Xor.fold(source);
            Assert.assertArrayEquals( new byte[]{ (byte) 0xAA }, result);
        }

        @Test
        public void success_3() {
            byte[] source = new byte[]{
                    (byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0x00,
                    (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0xFF
            };
            byte[] result = Xor.fold(source);
            Assert.assertArrayEquals( new byte[]{ (byte) 0x00, (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0xFF }, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Xor.fold(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            Xor.fold(new byte[0]);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_3() {
            Xor.fold(new byte[3]);
        }
    }

    public static class sum {
        @Test
        public void success_1() {
            byte[] source = new byte[]{(byte) 0xFF};
            byte result = Xor.sum(source);
            Assert.assertEquals((byte) 0xFF, result);
        }

        @Test
        public void success_2() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0xFF};
            byte result = Xor.sum(source);
            Assert.assertEquals((byte) 0xFF, result);
        }

        @Test
        public void success_3() {
            byte[] source = new byte[]{(byte) 0xFF, (byte) 0xFF};
            byte result = Xor.sum(source);
            Assert.assertEquals((byte) 0x00, result);
        }

        @Test
        public void success_4() {
            byte[] source = new byte[]{(byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0xAA};
            byte result = Xor.sum(source);
            Assert.assertEquals((byte) 0xAA, result);
        }

        @Test
        public void success_5() {
            byte[] source = new byte[]{(byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0xFF};
            byte result = Xor.sum(source);
            Assert.assertEquals((byte) 0x00, result);
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

}
