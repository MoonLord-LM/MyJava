package cn.moonlord.common.pdf;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Enclosed.class)
public class PdfXssTest {

    public static class CheckContainsJavaScript {
        @Test
        public void checkContainsJavaScript() {
            PdfXss.createJavaScriptFile("/tmp/tmp-xss-test.pdf");
            Assert.assertTrue(PdfXss.checkContainsJavaScript("/tmp/tmp-xss-test.pdf"));
            PdfXss.createNormalFile("/tmp/tmp-xss-test.pdf");
            Assert.assertFalse(PdfXss.checkContainsJavaScript("/tmp/tmp-xss-test.pdf"));
        }
    }

}
