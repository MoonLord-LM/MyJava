package cn.moonlord.security;

import java.security.SecureRandom;

public class Random {

    public static byte[] generate(int bitLength) {
        byte[] buffer = new byte[bitLength / Byte.SIZE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(buffer);
        return buffer;
    }

    public static String generateBase64String(int bitLength) {
        byte buffer[] = new byte[bitLength / Byte.SIZE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(buffer);
        return Base64.encode(buffer);
    }

}
