package cn.moonlord.common.git;

import cn.moonlord.common.lombok.VOScanner;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Enclosed.class)
public class SingleFileCommitterTest {

    public static Logger logger = LoggerFactory.getLogger(cn.moonlord.common.git.SingleFileCommitterTest.class);

    public static class run {
        @Test
        public void success_1() {
            (new SingleFileCommitter("")).run();
        }
    }

}
