package cn.moonlord.test;

import cn.moonlord.log.Logger;
import cn.moonlord.security.*;
import java.security.KeyPair;

public class SecurityTest {

    public static void test1() throws Exception {
        Logger.info("Random.generateBytes(256).length: " + Random.generate(256).length);
        Logger.info("Random.generateBase64String(256): " + Random.generateBase64String(256));

        byte[] source = new byte[] { 0x00, 0x01, 0x10, 0x11 };
        Logger.info("SHA256: " + Hash.SHA256(source));
        Logger.info("SHA512: " + Hash.SHA512(source));
    }

    public static void test2() throws Exception {
        byte[] source = new byte[] { 0x00, 0x01, 0x10, 0x11 };
        Logger.info("source: " + source.length);
        String key = Aes.generateKeyBase64String();
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

    public static void test3() throws Exception {
        String source = "This is a secret";
        Logger.info("source: " + source);
        KeyPair keyPair = Rsa.generateKeyPair();
        byte[] privateKey = keyPair.getPrivate().getEncoded();
        byte[] publicKey = keyPair.getPublic().getEncoded();
        Logger.info("privateKey: " + privateKey.length);
        Logger.info("publicKey: " + publicKey.length);
        byte[] encryptedByPrivateKey = Rsa.encrypt(source.getBytes(), Rsa.getPrivateKey(privateKey));
        Logger.info("encryptedByPrivateKey: " + encryptedByPrivateKey.length);
        byte[] decryptedByPublicKey = Rsa.decrypt(encryptedByPrivateKey, Rsa.getPublicKey(publicKey));
        Logger.info("decryptedByPublicKey: " + new String(decryptedByPublicKey));
    }

}
