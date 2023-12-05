package cn.moonlord.maven;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Enclosed.class)
public class DependenciesAnalysisTest {

    public static Logger logger = LoggerFactory.getLogger(DependenciesAnalysisTest.class);

    public static class run {
        @Test
        public void success_1() {
            (new DependenciesAnalysis(
                    "../../0-maven-management/parent/pom.xml", "../maven-test-dependencies/pom.xml"
            )).run();
        }
    }

}
