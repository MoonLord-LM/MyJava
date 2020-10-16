package cn.moonlord.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;

public class Random {

    private final static long MAX_BYTE_LENGTH = Integer.MAX_VALUE;
    private final static long MAX_BIT_LENGTH = MAX_BYTE_LENGTH * (long) Byte.SIZE;

    public static byte[] generateBytes(int byteLength) {
        byte[] buffer = new byte[byteLength];
        SecureRandom random = new SecureRandom();
        random.nextBytes(buffer);
        return buffer;
    }

    public static byte[] generateBytes(long byteLength) throws Exception {
        if(byteLength > MAX_BYTE_LENGTH){
            throw new InvalidAlgorithmParameterException("random generate byte length should not larger than " + MAX_BYTE_LENGTH);
        }
        int byteCount = Long.valueOf(byteLength).intValue();
        byte[] buffer = new byte[byteCount];
        SecureRandom random = new SecureRandom();
        random.nextBytes(buffer);
        return buffer;
    }

    public static byte[] generate(int bitLength) {
        int byteCount = bitLength / Byte.SIZE;
        return generateBytes(byteCount);
    }

    public static byte[] generate(long bitLength) throws Exception {
        if(bitLength > MAX_BIT_LENGTH){
            throw new InvalidAlgorithmParameterException("random generate bit length should not larger than " + MAX_BIT_LENGTH);
        }
        int byteCount = Long.valueOf(bitLength / (long) Byte.SIZE).intValue();
        return generateBytes(byteCount);
    }

    public static String generateBase64String(int bitLength) {
        return Base64.encode(generate(bitLength));
    }

    public static String generateBase64String(long bitLength) throws Exception {
        return Base64.encode(generate(bitLength));
    }

}
