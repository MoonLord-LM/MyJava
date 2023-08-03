package cn.moonlord.common.pdf;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;

@SpringBootTest
@RunWith(Enclosed.class)
public class PdfXssTest {

    public static class CreateFileTest{
        @Test
        public void createFile(){
            PdfXss.createFile("tmp-xss-test.pdf");
        }
    }

    public static class CheckFileTest{
        @Test
        public void checkFile(){
            PdfXss.checkFile("tmp-xss-test.pdf");
        }
    }

}
