package cn.moonlord.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.KeySpec;

public class Pbkdf2 {

    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA512";

    private static final int OUTPUT_KEY_MIN_LENGTH = 256;
    private static final int SALT_MIN_LENGTH = 256;
    private static final int ITERATION_MIN_COUNT = 10000;

    public static byte[] generate(char[] password, byte[] salt, int iterationCount, int outputKeyLength) throws Exception {
        if(outputKeyLength < OUTPUT_KEY_MIN_LENGTH){
            throw new InvalidAlgorithmParameterException("output key length is not long enough, the length should be " + OUTPUT_KEY_MIN_LENGTH + "at least");
        }
        if(salt.length < SALT_MIN_LENGTH  / Byte.SIZE){
            throw new InvalidAlgorithmParameterException("salt length is not long enough, the length should be " + SALT_MIN_LENGTH + "at least");
        }
        if(iterationCount < ITERATION_MIN_COUNT){
            throw new InvalidAlgorithmParameterException("iteration count is not big enough, the count should be " + ITERATION_MIN_COUNT + "at least");
        }
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        KeySpec pbeKeySpec = new PBEKeySpec(password, salt, iterationCount, outputKeyLength);
        return secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
    }

    public static byte[] generate(String password, byte[] salt, int iterationCount, int keyLength) throws Exception {
        return generate(password.toCharArray(), salt, iterationCount, keyLength);
    }

    public static String generateBase64String(char[] password, byte[] salt, int iterationCount, int keyLength) throws Exception {
        return Base64.encode(generate(password, salt, iterationCount, keyLength));
    }

    public static String generateBase64String(String password, byte[] salt, int iterationCount, int keyLength) throws Exception {
        return Base64.encode(generate(password, salt, iterationCount, keyLength));
    }

    public static byte[] generate(char[] password, byte[] salt, int iterationCount) throws Exception {
        return generate(password, salt, iterationCount, OUTPUT_KEY_MIN_LENGTH);
    }

    public static byte[] generate(String password, byte[] salt, int iterationCount) throws Exception {
        return generate(password.toCharArray(), salt, iterationCount);
    }

    public static String generateBase64String(char[] password, byte[] salt, int iterationCount) throws Exception {
        return Base64.encode(generate(password, salt, iterationCount));
    }

    public static String generateBase64String(String password, byte[] salt, int iterationCount) throws Exception {
        return Base64.encode(generate(password, salt, iterationCount));
    }

    public static byte[] generate(char[] password, byte[] salt) throws Exception {
        return generate(password, salt, ITERATION_MIN_COUNT, OUTPUT_KEY_MIN_LENGTH);
    }

    public static byte[] generate(String password, byte[] salt) throws Exception {
        return generate(password.toCharArray(), salt);
    }

    public static String generateBase64String(char[] password, byte[] salt) throws Exception {
        return Base64.encode(generate(password, salt));
    }

    public static String generateBase64String(String password, byte[] salt) throws Exception {
        return Base64.encode(generate(password, salt));
    }

}
