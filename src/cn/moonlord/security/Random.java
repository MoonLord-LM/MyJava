package cn.moonlord.security;

import java.security.SecureRandom;
import java.util.Base64;

public class Random {

    public static byte[] generateBytes(int byteLength) {
        SecureRandom random = new SecureRandom();
        byte[] buffer = new byte[byteLength];
        random.nextBytes(buffer);
        return buffer;
    }

    public static String generateBase64String(int byteLength) {
        SecureRandom random = new SecureRandom();
        byte buffer[] = new byte[byteLength];
        random.nextBytes(buffer);
        return Base64.getEncoder().encodeToString(buffer);
    }

}
