package cn.moonlord.security;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RandomTest {

    @Test
    public void test1(){
        Random.generate(-1);
        //Random.generate(-1000);
    }

}
