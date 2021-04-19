package cn.moonlord.security;

import java.security.SecureRandom;

public class Random {

    public static byte[] generate(int bitLength) {
        if(bitLength <= 0){
            throw new IllegalArgumentException("Random generate error, bitLength [" + bitLength + "] must be larger than 0");
        }
        if(bitLength % Byte.SIZE != 0){
            throw new IllegalArgumentException("Random generate error, bitLength [" + bitLength + "] must be a multiple of " + Byte.SIZE);
        }
        int byteLength = bitLength / Byte.SIZE;
        byte[] buffer = new byte[byteLength];
        SecureRandom random = new SecureRandom();
        random.nextBytes(buffer);
        return buffer;
    }

    public static byte[] generateBytes(int byteLength) {
        if(byteLength <= 0){
            throw new IllegalArgumentException("Random generateBytes error, byteLength [" + byteLength + "] must be larger than 0");
        }
        byte[] buffer = new byte[byteLength];
        SecureRandom random = new SecureRandom();
        random.nextBytes(buffer);
        return buffer;
    }

}
