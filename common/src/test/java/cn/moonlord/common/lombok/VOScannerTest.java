package cn.moonlord.common.lombok;

import cn.moonlord.common.maven.DependenciesAnalysis;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Enclosed.class)
public class VOScannerTest {

    public static Logger logger = LoggerFactory.getLogger(cn.moonlord.common.maven.DependenciesAnalysisTest.class);

    public static class run {
        @Test
        public void success_1() {
            (new VOScanner(
                ""
            )).run();
        }
    }

}
