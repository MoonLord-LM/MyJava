package cn.moonlord.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class Random {

    private static final List<String> SECURITY_RANDOM_ALGORITHMS = Arrays.asList(
            "DRBG", // sun.security.provider.DRBG（>= JDK 9）
            "NonceAndIV", // org.bouncycastle.jcajce.provider.drbg.DRBG$NonceAndIV
            "Default", // org.bouncycastle.jcajce.provider.drbg.DRBG$Default
            "Windows-PRNG", // sun.security.mscapi.PRNG
            "NativePRNGBlocking",
            "NativePRNG",
            "NativePRNGNonBlocking",
            "SHA1PRNG" // sun.security.provider.SecureRandom
    );

    // TODO：Number of bits per request limited to 262144
    // TODO：不超过2^48请求，补种 888 bit

    private static volatile SecureRandom instance = null;

    static {
        init();
    }

    public synchronized static void init(){
        Provider.init();
    }

    public static SecureRandom getInstance() {
        if(instance == null) {
            for (String algorithm : SECURITY_RANDOM_ALGORITHMS) {
                try {
                    instance = SecureRandom.getInstance(algorithm);
                    break;
                }
                catch (NoSuchAlgorithmException ignore) { }
            }
        }
        if(instance == null) {
            throw new IllegalArgumentException("Random getInstance error, algorithms can not be found: " + SECURITY_RANDOM_ALGORITHMS);
        }
        return instance;
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
