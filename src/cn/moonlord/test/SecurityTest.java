package cn.moonlord.test;

import cn.moonlord.log.Logger;
import cn.moonlord.security.*;
import javafx.beans.binding.Bindings;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

public class SecurityTest {

    public static void test1() throws Exception {
        Logger.info("Random.generateBytes(256).length: " + Random.generate(256).length);
        Logger.info("Random.generateBase64String(256): " + Random.generateBase64String(256));

        byte[] source = "12313123123122323125".getBytes(StandardCharsets.UTF_8);
        String sha256 = Hash.sha256(source);
        Logger.info("SHA256: " + sha256.length() + " " + sha256);
        byte[] sha256Bytes = Hex.decode(sha256);
        Logger.info("sha256 Hex.decode: " + sha256Bytes.length + " " + sha256Bytes);
        String sha256Hex = Hex.encode(sha256Bytes);
        Logger.info("sha256Hex: " + sha256Hex.length() + " " + sha256Hex);

        source = "12313123123122323125".getBytes(StandardCharsets.UTF_8);
        String sha512 = Hash.sha512(source);
        Logger.info("SHA512: " + sha512.length() + " " + sha512);
        byte[] sha512Bytes = Hex.decode(sha512);
        Logger.info("sha512Bytes: " + sha512Bytes.length + " " + sha512Bytes);
        String sha512Hex = Hex.encode(sha512Bytes);
        Logger.info("sha512Hex: " + sha512Hex.length() + " " + sha512Hex);

        String sourceString = "0000000000";
        byte[] hexEncode = Hex.decode(sourceString);
        String hexDecode = Hex.encode(hexEncode);
        if(!hexDecode.equals(sourceString)){
            Logger.info("sourceString: " + sourceString.length() + " " + sourceString);
            Logger.info("hexDecode: " + hexDecode.length() + " " + hexDecode);
        }

        sourceString = "ffffffffff";
        hexEncode = Hex.decode(sourceString);
        hexDecode = Hex.encode(hexEncode);
        if(!hexDecode.equals(sourceString)){
            Logger.info("sourceString: " + sourceString.length() + " " + sourceString);
            Logger.info("hexDecode: " + hexDecode.length() + " " + hexDecode);
        }

        for (int i = 0; i < 1024 * 64; i++) {
            sourceString = Hash.sha256(""+ i);
            hexEncode = Hex.decode(sourceString);
            hexDecode = Hex.encode(hexEncode);
            if(!hexDecode.equals(sourceString)){
                Logger.info("sourceString: " + sourceString.length() + " " + sourceString);
                Logger.info("hexDecode: " + hexDecode.length() + " " + hexDecode);
            }
            sourceString = Hash.sha512(""+ i);
            hexEncode = Hex.decode(sourceString);
            hexDecode = Hex.encode(hexEncode);
            if(!hexDecode.equals(sourceString)){
                Logger.info("sourceString: " + sourceString.length() + " " + sourceString);
                Logger.info("hexDecode: " + hexDecode.length() + " " + hexDecode);
            }
        }
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
