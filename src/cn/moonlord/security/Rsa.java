package cn.moonlord.security;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Rsa {

    private static final String RSA_KEY_ALGORITHM = "RSA";
    private static final String RSA_CIPHER_INSTANCE = "RSA/ECB/OAEPPADDING";

    private final static int RSA_KEY_LENGTH = 4096;

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_KEY_ALGORITHM);
        keyPairGenerator.initialize(RSA_KEY_LENGTH);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    public static PrivateKey getPrivateKey(KeyPair keyPair) {
        return keyPair.getPrivate();
    }

    public static PublicKey getPublicKey(KeyPair keyPair) {
        return keyPair.getPublic();
    }

    public static byte[] getPrivateKeyBytes(KeyPair keyPair) {
        return getPrivateKey(keyPair).getEncoded();
    }

    public static byte[] getPublicKeyBytes(KeyPair keyPair) {
        return getPublicKey(keyPair).getEncoded();
    }

    public static String getPrivateKeyBase64String(KeyPair keyPair) {
        return Base64.encode(getPrivateKeyBytes(keyPair));
    }

    public static String getPublicKeyBase64String(KeyPair keyPair) {
        return Base64.encode(getPublicKeyBytes(keyPair));
    }

    public static PrivateKey getPrivateKey(byte[] key) throws Exception {
        return KeyFactory.getInstance(RSA_KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(key));
    }

    public static PrivateKey getPrivateKey(String keyBase64String) throws Exception {
        return getPrivateKey(Base64.decode(keyBase64String));
    }

    public static PublicKey getPublicKey(byte[] key) throws Exception {
        return KeyFactory.getInstance(RSA_KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(key));
    }

    public static PublicKey getPublicKey(String keyBase64String) throws Exception {
        return getPublicKey(Base64.decode(keyBase64String));
    }

    public static byte[] encrypt(byte[] sourceBytes, PublicKey encryptKey) throws Exception{
        Cipher cipher = Cipher.getInstance(RSA_CIPHER_INSTANCE);
        cipher.init(Cipher.ENCRYPT_MODE, encryptKey);
        byte[] encryptedResult = cipher.doFinal(sourceBytes);
        return encryptedResult;
    }

    public static byte[] encrypt(byte[] sourceBytes, byte[] encryptKeyBytes) throws Exception {
        return encrypt(sourceBytes, getPublicKey(encryptKeyBytes));
    }

    public static byte[] encrypt(byte[] sourceBytes, String encryptKeyBase64String) throws Exception {
        return encrypt(sourceBytes, getPublicKey(encryptKeyBase64String));
    }

    public static String encryptBase64String(byte[] sourceBytes, PublicKey encryptKey) throws Exception {
        return Base64.encode(encrypt(sourceBytes, encryptKey));
    }

    public static String encryptBase64String(byte[] sourceBytes, byte[] encryptKeyBytes) throws Exception {
        return Base64.encode(encrypt(sourceBytes, encryptKeyBytes));
    }

    public static String encryptBase64String(byte[] sourceBytes, String encryptKeyBase64String) throws Exception {
        return Base64.encode(encrypt(sourceBytes, encryptKeyBase64String));
    }

    public static byte[] decrypt(byte[] encryptedBytes, PrivateKey decryptKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_CIPHER_INSTANCE);
        cipher.init(Cipher.DECRYPT_MODE, decryptKey);
        byte[] decryptedResult = cipher.doFinal(encryptedBytes);
        return decryptedResult;
    }

    public static byte[] decrypt(byte[] sourceBytes, byte[] decryptKeyBytes) throws Exception {
        return decrypt(sourceBytes, getPrivateKey(decryptKeyBytes));
    }

    public static byte[] decrypt(byte[] sourceBytes, String decryptKeyBase64String) throws Exception {
        return decrypt(sourceBytes, getPrivateKey(decryptKeyBase64String));
    }

    public static byte[] decryptBase64String(String encryptedBase64String, PrivateKey decryptKey) throws Exception {
        return decrypt(Base64.decode(encryptedBase64String), decryptKey);
    }

    public static byte[] decryptBase64String(String encryptedBase64String, byte[] decryptKeyBytes) throws Exception {
        return decrypt(Base64.decode(encryptedBase64String), decryptKeyBytes);
    }

    public static byte[] decryptBase64String(String encryptedBase64String, String decryptKeyBase64String) throws Exception {
        return decrypt(Base64.decode(encryptedBase64String), decryptKeyBase64String);
    }

}
