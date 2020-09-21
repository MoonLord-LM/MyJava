package cn.moonlord.security;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class Aes {

    private static final String AES_KEY_ALGORITHM = "AES";
    private static final String AES_CIPHER_INSTANCE = "AES/GCM/NOPADDING";

    private final static int AES_KEY_LENGTH = 256;
    private final static int GCM_TAG_LENGTH = 128;
    private final static int GCM_IV_LENGTH = 96;

    public static byte[] generateKey() {
        return Random.generate(AES_KEY_LENGTH);
    }

    public static String generateKeyBase64String() {
        return Base64.encode(generateKey());
    }

    public static SecretKeySpec getSecretKey(byte[] key) {
        return new SecretKeySpec(key, AES_KEY_ALGORITHM);
    }

    public static SecretKeySpec getSecretKey(String keyBase64String) {
        return getSecretKey(Base64.decode(keyBase64String));
    }

    public static byte[] encrypt(byte[] sourceBytes, SecretKeySpec encryptKey) throws Exception {
        byte[] iv = Random.generate(GCM_IV_LENGTH);
        GCMParameterSpec ivSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);

        Cipher cipher = Cipher.getInstance(AES_CIPHER_INSTANCE);
        cipher.init(Cipher.ENCRYPT_MODE, encryptKey, ivSpec);
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

    public static String encryptBase64String(byte[] sourceBytes, SecretKeySpec encryptKey) throws Exception {
        return Base64.encode(encrypt(sourceBytes, encryptKey));
    }

    public static String encryptBase64String(byte[] sourceBytes, byte[] encryptKeyBytes) throws Exception {
        return Base64.encode(encrypt(sourceBytes, encryptKeyBytes));
    }

    public static String encryptBase64String(byte[] sourceBytes, String encryptKeyBase64String) throws Exception {
        return Base64.encode(encrypt(sourceBytes, encryptKeyBase64String));
    }

    public static byte[] decrypt(byte[] encryptedBytes, SecretKeySpec decryptKey) throws Exception {
        byte[] iv = Arrays.copyOfRange(encryptedBytes, 0, GCM_IV_LENGTH / Byte.SIZE);
        Cipher cipher = Cipher.getInstance(AES_CIPHER_INSTANCE);
        GCMParameterSpec ivSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, decryptKey, ivSpec);
        byte[] decryptedSource = cipher.doFinal(encryptedBytes, iv.length, encryptedBytes.length - iv.length);
        return decryptedSource;
    }

    public static byte[] decrypt(byte[] encryptedBytes, byte[] decryptKeyBytes) throws Exception {
        return decrypt(encryptedBytes, getSecretKey(decryptKeyBytes));
    }

    public static byte[] decrypt(byte[] encryptedBytes, String decryptKeyBase64String) throws Exception {
        return decrypt(encryptedBytes, getSecretKey(decryptKeyBase64String));
    }

    public static byte[] decryptBase64String(String encryptedBase64String, SecretKeySpec decryptKey) throws Exception {
        return decrypt(Base64.decode(encryptedBase64String), decryptKey);
    }

    public static byte[] decryptBase64String(String encryptedBase64String, byte[] decryptKeyBytes) throws Exception {
        return decrypt(Base64.decode(encryptedBase64String), decryptKeyBytes);
    }

    public static byte[] decryptBase64String(String encryptedBase64String, String decryptKeyBase64String) throws Exception {
        return decrypt(Base64.decode(encryptedBase64String), decryptKeyBase64String);
    }

}
