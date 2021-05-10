package cn.moonlord.security;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.util.Arrays;

public class Aes {

    private static final String AES_KEY_ALGORITHM = "AES";

    private static final String AES_CIPHER_INSTANCE = "AES_256/GCM/NoPadding";

    private static final int AES_KEY_LENGTH = 256;

    private static final int GCM_TAG_LENGTH = 128;

    private static final int GCM_IV_LENGTH = 96;

    public static byte[] generateKey() {
        return Random.generate(AES_KEY_LENGTH);
    }

    public static String generateKeyBase64String() {
        return Base64.encode(generateKey());
    }

    public static SecretKeySpec getSecretKey(byte[] keyBytes) {
        if(keyBytes == null){
            throw new IllegalArgumentException("Aes getSecretKey error, keyBytes must not be null");
        }
        if(keyBytes.length == 0){
            throw new IllegalArgumentException("Aes getSecretKey error, keyBytes must not be empty");
        }
        if(keyBytes.length != AES_KEY_LENGTH  / Byte.SIZE){
            throw new IllegalArgumentException("Aes getSecretKey error, the length of keyBytes [" + keyBytes.length + "] must be " + ( AES_KEY_LENGTH  / Byte.SIZE ));
        }
        return new SecretKeySpec(keyBytes, AES_KEY_ALGORITHM);
    }

    public static SecretKeySpec getSecretKey(String keyBase64String) {
        return getSecretKey(Base64.decode(keyBase64String));
    }

    public static byte[] encrypt(byte[] sourceBytes, SecretKeySpec encryptKey) throws Exception {
        if(sourceBytes == null){
            throw new IllegalArgumentException("Aes encrypt error, sourceBytes must not be null");
        }
        if(encryptKey.getEncoded().length != AES_KEY_LENGTH  / Byte.SIZE){
            throw new InvalidAlgorithmParameterException("Aes encrypt error, encryptKey length must be " + AES_KEY_LENGTH);
        }

        byte[] iv = Random.generate(GCM_IV_LENGTH);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);

        Cipher cipher = Cipher.getInstance(AES_CIPHER_INSTANCE);
        cipher.init(Cipher.ENCRYPT_MODE, encryptKey, gcmSpec);
        byte[] encryptedSource = cipher.doFinal(sourceBytes);

        byte[] encryptedResult = new byte[iv.length + encryptedSource.length];
        System.arraycopy(iv, 0, encryptedResult, 0, iv.length);
        System.arraycopy(encryptedSource, 0, encryptedResult, iv.length, encryptedSource.length);
        return encryptedResult;
    }

    public static byte[] encrypt(byte[] sourceBytes, byte[] encryptKeyBytes) throws Exception {
        return encrypt(sourceBytes, getSecretKey(encryptKeyBytes));
    }

    public static byte[] encrypt(byte[] sourceBytes, String encryptKeyBase64String) throws Exception {
        return encrypt(sourceBytes, getSecretKey(encryptKeyBase64String));
    }

    public static byte[] encrypt(String sourceString, SecretKeySpec encryptKey) throws Exception {
        byte[] sourceBytes = sourceString.getBytes(StandardCharsets.UTF_8);
        return encrypt(sourceBytes, encryptKey);
    }

    public static byte[] encrypt(String sourceString, byte[] encryptKeyBytes) throws Exception {
        byte[] sourceBytes = sourceString.getBytes(StandardCharsets.UTF_8);
        return encrypt(sourceBytes, encryptKeyBytes);
    }

    public static byte[] encrypt(String sourceString, String encryptKeyBase64String) throws Exception {
        byte[] sourceBytes = sourceString.getBytes(StandardCharsets.UTF_8);
        return encrypt(sourceBytes, encryptKeyBase64String);
    }

    public static byte[] decrypt(byte[] encryptedBytes, SecretKeySpec decryptKey) throws Exception {
        if(encryptedBytes == null){
            throw new IllegalArgumentException("Aes decrypt error, encryptedBytes must not be null");
        }
        if(decryptKey.getEncoded().length != AES_KEY_LENGTH  / Byte.SIZE){
            throw new InvalidAlgorithmParameterException("Aes decrypt error, decryptKey length must be " + AES_KEY_LENGTH);
        }

        byte[] iv = Arrays.copyOfRange(encryptedBytes, 0, GCM_IV_LENGTH / Byte.SIZE);
        Cipher cipher = Cipher.getInstance(AES_CIPHER_INSTANCE);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, decryptKey, gcmSpec);
        return cipher.doFinal(encryptedBytes, iv.length, encryptedBytes.length - iv.length);
    }

    public static byte[] decrypt(byte[] encryptedBytes, byte[] decryptKeyBytes) throws Exception {
        return decrypt(encryptedBytes, getSecretKey(decryptKeyBytes));
    }

    public static byte[] decrypt(byte[] encryptedBytes, String decryptKeyBase64String) throws Exception {
        return decrypt(encryptedBytes, getSecretKey(decryptKeyBase64String));
    }

    public static String decryptString(byte[] encryptedBytes, SecretKeySpec decryptKey) throws Exception {
        byte[] sourceBytes = decrypt(encryptedBytes, decryptKey);
        return new String(sourceBytes, StandardCharsets.UTF_8);
    }

    public static String decryptString(byte[] encryptedBytes, byte[] decryptKeyBytes) throws Exception {
        byte[] sourceBytes = decrypt(encryptedBytes, decryptKeyBytes);
        return new String(sourceBytes, StandardCharsets.UTF_8);
    }

    public static String decryptString(byte[] encryptedBytes, String decryptKeyBase64String) throws Exception {
        byte[] sourceBytes = decrypt(encryptedBytes, decryptKeyBase64String);
        return new String(sourceBytes, StandardCharsets.UTF_8);
    }

}
