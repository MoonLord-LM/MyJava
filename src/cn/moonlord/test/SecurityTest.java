package cn.moonlord.test;

import cn.moonlord.log.Logger;
import cn.moonlord.security.*;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

public class SecurityTest {

    public static void test1() throws Exception {
        Logger.info("Random.generateBytes(256).length: " + Random.generate(256).length);
        Logger.info("Random.generateBase64String(256): " + Random.generateBase64String(256));

        byte[] source = new byte[] { 0x00, 0x01, 0x10, 0x11, 0x00 };
        Logger.info("SHA256: " + Hash.SHA256(source));
        Logger.info("SHA512: " + Hash.SHA512(source));
    }

    public static void test2() throws Exception {
        byte[] source = new byte[10000];
        Logger.info("source: " + source.length);
        String key = Aes.generateKeyBase64String();
        Logger.info("key: " + key);
        byte[] encrypted1 = Aes.encrypt(source, key);
        Logger.info("encrypted1: " + Base64.encode(encrypted1).length() + " " + Base64.encode(encrypted1));
        String encrypted2 = Aes.encryptToBase64String(source, key);
        Logger.info("encrypted2: " + encrypted2.length() + " " + encrypted2);
        byte[] result1 = Aes.decrypt(encrypted1, key);
        Logger.info("result1: " + result1.length);
        byte[] result2 = Aes.decryptFromBase64String(encrypted2, key);
        Logger.info("result2: " + result2.length);
    }

    public static void test3() throws Exception {
        String source = "This is a secret" + Arrays.toString(new byte[100]);
        Logger.info("source: " + source);
        KeyPair keyPair = Rsa.generateKeyPair();
        PrivateKey privateKey = Rsa.getPrivateKey(keyPair.getPrivate().getEncoded());
        PublicKey publicKey = Rsa.getPublicKey(keyPair.getPublic().getEncoded());
        Logger.info("privateKey: " + privateKey.getEncoded().length);
        Logger.info("publicKey: " + publicKey.getEncoded().length);
        byte[] encrypted1 = Rsa.encrypt(source.getBytes(), publicKey);
        Logger.info("encrypted1: " + encrypted1.length + " " + encrypted1);
        byte[] encrypted2 = Rsa.encrypt(source.getBytes(), publicKey);
        Logger.info("encrypted2: " + encrypted2.length + " " + encrypted2);
        byte[] result1 = Rsa.decrypt(encrypted1, privateKey);
        Logger.info("result1: " + result1.length);
        byte[] result2 = Rsa.decrypt(encrypted2, privateKey);
        Logger.info("result2: " + result1.length);
    }

}
