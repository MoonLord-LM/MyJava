package cn.moonlord.security;

import java.security.SecureRandom;

public class Random {

    public static byte[] generateBytes(int byteLength) {
        if(byteLength <= 0){
            throw new IllegalArgumentException("Random generateBytes error, byteLength must be larger than 0");
        }
        byte[] buffer = new byte[byteLength];
        SecureRandom random = new SecureRandom();
        random.nextBytes(buffer);
        return buffer;
    }

    public static byte[] generateBytes(long byteLength) {
        if(byteLength > Integer.MAX_VALUE){
            throw new IllegalArgumentException("Random generateBytes error, byteLength must not be larger than " + Integer.MAX_VALUE);
        }
        int byteCount = Long.valueOf(byteLength).intValue();
        return generateBytes(byteCount);
    }

    public static byte[] generate(int bitLength) {
        if(bitLength % Byte.SIZE != 0){
            throw new IllegalArgumentException("Random generate error, bitLength must be a multiple of " + Byte.SIZE);
        }
        int byteCount = bitLength / Byte.SIZE;
        return generateBytes(byteCount);
    }

    public static byte[] generate(long bitLength) {
        if(bitLength > (long) Integer.MAX_VALUE * (long) Byte.SIZE){
            throw new IllegalArgumentException("Random generate error, bitLength must not be larger than " + ((long) Integer.MAX_VALUE * (long) Byte.SIZE));
        }
        int bitCount = Long.valueOf(bitLength).intValue();
        return generate(bitCount);
    }

    public static String generateBase64String(int bitLength) {
        return Base64.encode(generate(bitLength));
    }

    public static String generateBase64String(long bitLength) {
        return Base64.encode(generate(bitLength));
    }

}
