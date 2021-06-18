package cn.moonlord.security;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;

public class Pbkdf2 {

    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA512";

    private static final int SALT_MIN_SIZE = 256 / Byte.SIZE;

    private static final int ITERATION_MIN_COUNT = 10000;

    private static final int OUTPUT_KEY_MIN_LENGTH = 256 / Byte.SIZE;

    public static SecretKey generate(char[] password, byte[] salt, int iterationCount, int outputKeyLength) {
        if(password == null){
            throw new IllegalArgumentException("Pbkdf2 generate error, password must not be null");
        }
        if(salt.length < SALT_MIN_SIZE){
            throw new IllegalArgumentException("Pbkdf2 generate error, the length of salt [ " + salt.length + " ] must not be smaller than " + SALT_MIN_SIZE);
        }
        if(iterationCount < ITERATION_MIN_COUNT){
            throw new IllegalArgumentException("Pbkdf2 generate error, the count of iteration [ " + iterationCount + " ] must not be smaller than " + ITERATION_MIN_COUNT);
        }
        if(outputKeyLength < OUTPUT_KEY_MIN_LENGTH){
            throw new IllegalArgumentException("Pbkdf2 generate error, the length of output key [ " + outputKeyLength + " ] must not be smaller than " + OUTPUT_KEY_MIN_LENGTH);
        }
        SecretKeyFactory secretKeyFactory = null;
        try {
            secretKeyFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            KeySpec pbeKeySpec = new PBEKeySpec(password, salt, iterationCount, outputKeyLength);
            return secretKeyFactory.generateSecret(pbeKeySpec);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Pbkdf2 generate error, error message: " + e.getMessage(), e);
        }
    }

    public static SecretKey generate(String password, byte[] salt, int iterationCount, int keyLength) {
        return generate(password.toCharArray(), salt, iterationCount, keyLength);
    }

    public static byte[] generateBytes(char[] password, byte[] salt, int iterationCount, int keyLength) {
        return generate(password, salt, iterationCount, keyLength).getEncoded();
    }

    public static byte[] generateBytes(String password, byte[] salt, int iterationCount, int keyLength) {
        return generate(password.toCharArray(), salt, iterationCount, keyLength).getEncoded();
    }

    public static String generateBase64String(char[] password, byte[] salt, int iterationCount, int keyLength) {
        return Base64.encode(generateBytes(password, salt, iterationCount, keyLength));
    }

    public static String generateBase64String(String password, byte[] salt, int iterationCount, int keyLength) {
        return Base64.encode(generateBytes(password, salt, iterationCount, keyLength));
    }

}
