package cn.moonlord.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class Random {

    public static final String ALGORITHM_DRBG = "DRBG"; // sun.security.provider.DRBG（>= JDK 9）

    public static final String ALGORITHM_NONCE_AND_IV = "NonceAndIV"; // org.bouncycastle.jcajce.provider.drbg.DRBG$NonceAndIV

    public static final String ALGORITHM_DEFAULT = "Default"; // org.bouncycastle.jcajce.provider.drbg.DRBG$Default

    public static final String ALGORITHM_WINDOWS_PRNG = "Windows-PRNG"; // sun.security.mscapi.PRNG

    public static final String ALGORITHM_NATIVE_PRNG_BLOCKING = "NativePRNGBlocking"; // sun.security.provider.NativePRNG$Blocking

    /*
      These algorithms are not strong enough：
          public static final String ALGORITHM_NATIVE_PRNG = "NativePRNG"; // sun.security.provider.NativePRNG
          public static final String ALGORITHM_NATIVE_PRNG_NONBLOCKING = "NativePRNGNonBlocking"; // sun.security.provider.NativePRNG$NonBlocking
          public static final String ALGORITHM_SHA1_PRNG = "SHA1PRNG"; // sun.security.provider.SecureRandom
     */

    public static final List<String> SECURITY_RANDOM_ALGORITHMS = Arrays.asList(
            ALGORITHM_DRBG,
            ALGORITHM_NONCE_AND_IV,
            ALGORITHM_DEFAULT,
            ALGORITHM_WINDOWS_PRNG,
            ALGORITHM_NATIVE_PRNG_BLOCKING
    );

    public static final List<String> DRBG_ALGORITHMS = Arrays.asList(
            ALGORITHM_DRBG,
            ALGORITHM_NONCE_AND_IV,
            ALGORITHM_DEFAULT
    );

    public static final int DRBG_MAX_BYTE_SIZE = 262144 / Byte.SIZE;

    private static volatile SecureRandom instance;

    static {
        init();
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

    public static SecureRandom getInstance() {
        return instance;
    }

    public static byte[] generate(int bitLength) {
        if(bitLength <= 0){
            throw new IllegalArgumentException("Random generate error, bitLength [ " + bitLength + " ] must be larger than 0");
        }
        if(bitLength % Byte.SIZE != 0){
            throw new IllegalArgumentException("Random generate error, bitLength [ " + bitLength + " ] must be a multiple of " + Byte.SIZE);
        }
        int byteLength = bitLength / Byte.SIZE;
        return generateBytes(byteLength);
    }

    public static byte[] generateBytes(int byteLength) {
        if(byteLength <= 0){
            throw new IllegalArgumentException("Random generateBytes error, byteLength [ " + byteLength + " ] must be larger than 0");
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

    public static <T> T select(T[] array) {
        if(array == null){
            throw new IllegalArgumentException("Random select error, array must not be null");
        }
        if(array.length <= 0){
            throw new IllegalArgumentException("Random select error, the length of array [ " + array.length + " ] must be larger than 0");
        }
        int index = Random.getInstance().nextInt(array.length);
        return array[index];
    }

}
