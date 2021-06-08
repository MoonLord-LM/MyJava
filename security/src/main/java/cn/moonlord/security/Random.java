package cn.moonlord.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class Random {

    private static final List<String> SECURITY_RANDOM_ALGORITHMS = Arrays.asList(
            "Hash_DRBG", "Hmac_DRBG", "Ctr_DRBG", "DRBG", "NonceAndIV", "Default"
    );

    static {
        init();
    }

    public synchronized static void init(){
        Provider.init();
    }

    public static SecureRandom getInstance() {
        for (String algorithm : SECURITY_RANDOM_ALGORITHMS) {
            try {
                return SecureRandom.getInstance(algorithm);
            } catch (NoSuchAlgorithmException ignore) { }
        }
        throw new IllegalArgumentException("Random getInstance error, algorithms can not be found: " + SECURITY_RANDOM_ALGORITHMS);
    }

    public static byte[] generate(int bitLength) {
        if(bitLength <= 0){
            throw new IllegalArgumentException("Random generate error, bitLength [" + bitLength + "] must be larger than 0");
        }
        if(bitLength % Byte.SIZE != 0){
            throw new IllegalArgumentException("Random generate error, bitLength [" + bitLength + "] must be a multiple of " + Byte.SIZE);
        }
        int byteLength = bitLength / Byte.SIZE;
        byte[] buffer = new byte[byteLength];
        getInstance().nextBytes(buffer);
        return buffer;
    }

    public static byte[] generateBytes(int byteLength) {
        if(byteLength <= 0){
            throw new IllegalArgumentException("Random generateBytes error, byteLength [" + byteLength + "] must be larger than 0");
        }
        byte[] buffer = new byte[byteLength];
        getInstance().nextBytes(buffer);
        return buffer;
    }

}
