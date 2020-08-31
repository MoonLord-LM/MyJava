package cn.moonlord.test;

import cn.moonlord.log.Logger;
import cn.moonlord.security.Random;

public class SecurityTest {

    public static void test1(){
        Logger.info("Random.generateBytes(256).length: " + Random.generateBytes(256).length);
        Logger.info("Random.generateBase64String(256): " + Random.generateBase64String(256));
    }

}
