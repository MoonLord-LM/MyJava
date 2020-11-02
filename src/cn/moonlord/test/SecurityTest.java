package cn.moonlord.test;

import cn.moonlord.log.Logger;
import cn.moonlord.security.*;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

public class SecurityTest {

    public static void test1() throws Exception {
        Logger.info("Random.generateBytes(256).length: " + Random.generate(256).length);
        Logger.info("Random.generateBase64String(256): " + Random.generateBase64String(256));

        byte[] source = new byte[] { 0x00, 0x01, 0x10, 0x11, 0x00 };
        Logger.info("SHA256: " + Hash.sha256(source));
        Logger.info("SHA512: " + Hash.sha512(source));
    }

    public static void test2() throws Exception {
        byte[] source = new byte[10000];
        Logger.info("source: " + source.length);
        String key = Aes.generateKeyBase64String();
        Logger.info("key: " + key);
        byte[] encrypted1 = Aes.encrypt(source, key);
        Logger.info("encrypted1: " + encrypted1.length + " " + Base64.encode(encrypted1));
        String encrypted2 = Aes.encryptToBase64String(source, key);
        Logger.info("encrypted2: " + encrypted2.length() + " " + encrypted2);
        byte[] result1 = Aes.decrypt(encrypted1, key);
        Logger.info("result1: " + result1.length);
        byte[] result2 = Aes.decryptFromBase64String(encrypted2, key);
        Logger.info("result2: " + result2.length);
    }

    public static void test3() throws Exception {
        String source1 = "";
        String source2 = "This is a secret" + Arrays.toString(new byte[10000]);
        Logger.info("source1: " + source1.length() + " " + source1);
        Logger.info("source2: " + source2.length() + " " + source2);
        KeyPair keyPair = Rsa.generateKeyPair();
        PrivateKey privateKey = Rsa.getPrivateKey(keyPair.getPrivate().getEncoded());
        PublicKey publicKey = Rsa.getPublicKey(keyPair.getPublic().getEncoded());
        Logger.info("privateKey: " + privateKey.getEncoded().length);
        Logger.info("publicKey: " + publicKey.getEncoded().length);
        Logger.info("((RSAPublicKey)publicKey).getPublicExponent(): " + ((RSAPublicKey)publicKey).getPublicExponent().toString());
        byte[] encrypted1 = Rsa.encrypt(source1.getBytes(), publicKey);
        Logger.info("encrypted1: " + encrypted1.length + " " + encrypted1);
        byte[] encrypted2 = Rsa.encrypt(source2.getBytes(), publicKey);
        Logger.info("encrypted2: " + encrypted2.length + " " + encrypted2);
        byte[] result1 = Rsa.decrypt(encrypted1, privateKey);
        Logger.info("result1: " + result1.length);
        byte[] result2 = Rsa.decrypt(encrypted2, privateKey);
        Logger.info("result2: " + result2.length);
    }

    public static void test4() throws Exception {
        String source = "This is a secret" + Arrays.toString(new byte[512]);
        Logger.info("source: " + source);
        long startTime = System.currentTimeMillis();
        byte[] key1 = Pbkdf2.generate(source, new byte[64]);
        long endTime = System.currentTimeMillis();
        Logger.info("key:1 " + key1.length + " " + key1 + " time: " + (endTime - startTime));
        startTime = System.currentTimeMillis();
        byte[] key2 = Pbkdf2.generate(source, new byte[64], 10000);
        endTime = System.currentTimeMillis();
        Logger.info("key2: " + key2.length + " " + key2 + " time: " + (endTime - startTime));
        startTime = System.currentTimeMillis();
        byte[] key3 = Pbkdf2.generate(source, new byte[32], 100000);
        endTime = System.currentTimeMillis();
        Logger.info("key3: " + key3.length + " " + key3 + " time: " + (endTime - startTime));
    }

    public static void test5() throws Exception {
        byte[] component1 = Random.generate(512);
        System.out.println("component1: " + Base64.encode(component1));
        String hash = Hash.sha512(component1);
        System.out.println("hash: " + hash);
        byte[] component2 = Random.generate(512);
        System.out.println("component2: " + Base64.encode(component2));
        byte[] salt = Random.generate(512);
        System.out.println("salt: " + Base64.encode(salt));
        byte[] password = Xor.compute(component1, component2);
        System.out.println("password: " + Base64.encode(password));
        byte[] rootKey = Pbkdf2.generate(Hex.encode(password), salt, 10000);
        System.out.println("rootKey: " + Base64.encode(rootKey));
    }

}
