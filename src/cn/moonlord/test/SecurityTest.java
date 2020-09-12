package cn.moonlord.test;

import cn.moonlord.log.Logger;
import cn.moonlord.security.Aes;
import cn.moonlord.security.Base64;
import cn.moonlord.security.Random;

public class SecurityTest {

    public static void test1(){
        Logger.info("Random.generateBytes(256).length: " + Random.generate(256).length);
        Logger.info("Random.generateBase64String(256): " + Random.generateBase64String(256));
    }

    public static void test2() throws Exception {
        byte[] source = new byte[] { 0x00, 0x01, 0x10, 0x11 };
        Logger.info("source: " + source.length);
        String key = Aes.generateKeyString();
        Logger.info("key: " + key);
        byte[] encrypted1 = Aes.encrypt(source, key);
        Logger.info("encrypted1: " + Base64.encode(encrypted1));
        String encrypted2 = Aes.encryptBase64String(source, key);
        Logger.info("encrypted2: " + encrypted2);
        byte[] result1 = Aes.decrypt(encrypted1, key);
        Logger.info("result1: " + result1.length);
        byte[] result2 = Aes.decryptBase64String(encrypted2, key);
        Logger.info("result2: " + result2.length);
    }

}
