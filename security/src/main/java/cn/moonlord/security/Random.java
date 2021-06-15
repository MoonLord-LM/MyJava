package cn.moonlord.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class Random {

    public static final List<String> SECURITY_RANDOM_ALGORITHMS = Arrays.asList(
            "DRBG", // sun.security.provider.DRBG（>= JDK 9）
            "NonceAndIV", // org.bouncycastle.jcajce.provider.drbg.DRBG$NonceAndIV
            "Default", // org.bouncycastle.jcajce.provider.drbg.DRBG$Default
            "Windows-PRNG", // sun.security.mscapi.PRNG
            "NativePRNGBlocking", // sun.security.provider.NativePRNG$Blocking
            "NativePRNG", // sun.security.provider.NativePRNG
            "NativePRNGNonBlocking", // sun.security.provider.NativePRNG$NonBlocking
            "SHA1PRNG" // sun.security.provider.SecureRandom
    );

    public static final List<String> DRBG_ALGORITHMS = Arrays.asList(
            "DRBG", // sun.security.provider.DRBG（>= JDK 9）
            "NonceAndIV", // org.bouncycastle.jcajce.provider.drbg.DRBG$NonceAndIV
            "Default" // org.bouncycastle.jcajce.provider.drbg.DRBG$Default
    );

    public static final int DRBG_MAX_BYTE_SIZE = 262144 / Byte.SIZE;

    private static volatile SecureRandom instance;

    static {
        init();
    }

    public static SecureRandom getInstance() {
        return instance;
    }

    public synchronized static void init(){
        Provider.init();
        for (String algorithm : SECURITY_RANDOM_ALGORITHMS) {
            try {
                instance = SecureRandom.getInstance(algorithm);
                break;
            }
            catch (NoSuchAlgorithmException ignore) { }
        }
        if(instance == null) {
            throw new IllegalArgumentException("Random init error, none of the algorithms can be found: " + SECURITY_RANDOM_ALGORITHMS);
        }
    }

    public static byte[] generate(int bitLength) {
        if(bitLength <= 0){
            throw new IllegalArgumentException("Random generate error, bitLength [" + bitLength + "] must be larger than 0");
        }
        if(bitLength % Byte.SIZE != 0){
            throw new IllegalArgumentException("Random generate error, bitLength [" + bitLength + "] must be a multiple of " + Byte.SIZE);
        }
        int byteLength = bitLength / Byte.SIZE;
        return generateBytes(byteLength);
    }

    public static byte[] generateBytes(int byteLength) {
        if(byteLength <= 0){
            throw new IllegalArgumentException("Random generateBytes error, byteLength [" + byteLength + "] must be larger than 0");
        }
        byte[] result = new byte[byteLength];
        if(byteLength > DRBG_MAX_BYTE_SIZE && DRBG_ALGORITHMS.contains(getInstance().getAlgorithm())) {
            for (int i = 0; i <= byteLength / DRBG_MAX_BYTE_SIZE; i++) {
                byte[] buffer = new byte[DRBG_MAX_BYTE_SIZE];
                getInstance().nextBytes(buffer);
                int fillPosition = i * DRBG_MAX_BYTE_SIZE;
                int fillLength = Math.min(byteLength - fillPosition, DRBG_MAX_BYTE_SIZE);
                System.arraycopy(buffer, 0, result, fillPosition, fillLength);
            }
        }
        else {
            getInstance().nextBytes(result);
        }
        return result;
    }

}
