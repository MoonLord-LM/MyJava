package cn.moonlord.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;

public class Pbkdf2 {

    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA512";

    public static final int SALT_MIN_BYTE_LENGTH = 256 / Byte.SIZE;

    public static final int ITERATION_MIN_COUNT = 10000;

    public static final int OUTPUT_KEY_MIN_BYTE_LENGTH = 256 / Byte.SIZE;

    public static byte[] generate(char[] password, byte[] salt, int iterationCount, int outputKeyLength) {
        if(password == null){
            throw new IllegalArgumentException("Pbkdf2 generate error, password must not be null");
        }
        if(password.length == 0){
            throw new IllegalArgumentException("Pbkdf2 generate error, password must not be empty");
        }
        if(salt == null){
            throw new IllegalArgumentException("Pbkdf2 generate error, salt must not be null");
        }
        if(salt.length < SALT_MIN_BYTE_LENGTH){
            throw new IllegalArgumentException("Pbkdf2 generate error, the length of salt [ " + salt.length + " ] must not be smaller than " + SALT_MIN_BYTE_LENGTH);
        }
        if(iterationCount < ITERATION_MIN_COUNT){
            throw new IllegalArgumentException("Pbkdf2 generate error, the count of iteration [ " + iterationCount + " ] must not be smaller than " + ITERATION_MIN_COUNT);
        }
        if(outputKeyLength < OUTPUT_KEY_MIN_BYTE_LENGTH){
            throw new IllegalArgumentException("Pbkdf2 generate error, the length of output key [ " + outputKeyLength + " ] must not be smaller than " + OUTPUT_KEY_MIN_BYTE_LENGTH);
        }
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            KeySpec pbeKeySpec = new PBEKeySpec(password, salt, iterationCount, outputKeyLength);
            return secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Pbkdf2 generate error, error message: " + e.getMessage(), e);
        }
    }

    public static byte[] generate(String password, byte[] salt, int iterationCount, int keyLength) {
        if(password == null){
            throw new IllegalArgumentException("Pbkdf2 generate error, password must not be null");
        }
        return generate(password.toCharArray(), salt, iterationCount, keyLength);
    }

    public static byte[] generate(byte[] password, byte[] salt, int iterationCount, int keyLength) {
        if(password == null){
            throw new IllegalArgumentException("Pbkdf2 generate error, password must not be null");
        }
        return generate(Hex.encode(password), salt, iterationCount, keyLength);
    }

}
