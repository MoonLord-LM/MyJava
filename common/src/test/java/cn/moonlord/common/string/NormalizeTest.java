package cn.moonlord.common.string;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.net.URI;

@SpringBootTest
@RunWith(Enclosed.class)
public class NormalizeTest {

    public static Logger logger = LoggerFactory.getLogger(NormalizeTest.class);

    public static class getString {
        @Test
        public void success_1() {
            String source = "\uFe64" + "﹤尖括号1﹥" + "\uFe65" + " " + "\u003C" + "<尖括号2>" + "\u003E";
            String expected = "<<尖括号1>> <<尖括号2>>";
            String result = Normalize.getString(source);
            Assert.assertEquals(expected, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Normalize.getString(null);
        }
    }

    public static class getUriString {
        @Test
        public void success_1() {
            String source = "/test/../test/test/../../index.html";
            String expected = "/index.html";
            String result = Normalize.getUriString(source);
            Assert.assertEquals(expected, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Normalize.getUriString((URI) null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            Normalize.getUriString((String) null);
        }
    }

    public static class getFilePath {
        @Test
        public void success_1() {
            String source = "test/test/../../test/test/../../测试.txt";
            String expected = new File("测试.txt").getAbsolutePath();
            String result = Normalize.getFilePath(source);
            Assert.assertEquals(expected, result);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_1() {
            Normalize.getFilePath((File) null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void error_2() {
            Normalize.getFilePath((String) null);
        }
    }

}
