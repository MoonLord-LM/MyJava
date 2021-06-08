package cn.moonlord.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class Random {

    private static final List<String> SECURITY_RANDOM_INSTANCE_NAMES = Arrays.asList(
            "HASH_DRBG", "HMAC_DRBG", "CTR_DRBG", "DRBG", "NONCEANDIV", "DEFAULT"
    );

    static {
        init();
    }

    public synchronized static void init(){
        Provider.init();
    }

    public static SecureRandom getInstance() {
        for (String instanceName : SECURITY_RANDOM_INSTANCE_NAMES) {
            try {
                return SecureRandom.getInstance(instanceName);
            } catch (NoSuchAlgorithmException ignore) { }
        }
        throw new IllegalArgumentException("Random getInstance error, instance can not be found: " + SECURITY_RANDOM_INSTANCE_NAMES);
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
