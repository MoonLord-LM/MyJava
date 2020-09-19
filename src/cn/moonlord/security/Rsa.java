package cn.moonlord.security;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Rsa {

    private static final String RSA_KEY_ALGORITHM = "RSA";

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

    public static byte[] encrypt(byte[] sourceBytes, Key encryptKey) throws Exception{
        Cipher cipher = Cipher.getInstance(RSA_KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, encryptKey);
        byte[] encryptedResult = cipher.doFinal(sourceBytes);
        return encryptedResult;
    }

    public static String encryptBase64String(byte[] sourceBytes, Key encryptKey) throws Exception {
        return Base64.encode(encrypt(sourceBytes, encryptKey));
    }

    public static byte[] encryptByPrivateKey(byte[] sourceBytes, byte[] encryptKeyBytes) throws Exception {
        return encrypt(sourceBytes, getPrivateKey(encryptKeyBytes));
    }

    public static byte[] encryptByPublicKey(byte[] sourceBytes, byte[] encryptKeyBytes) throws Exception {
        return encrypt(sourceBytes, getPublicKey(encryptKeyBytes));
    }

    public static byte[] encryptByPrivateKey(byte[] sourceBytes, String encryptKeyBase64String) throws Exception {
        return encrypt(sourceBytes, getPrivateKey(encryptKeyBase64String));
    }

    public static byte[] encryptByPublicKey(byte[] sourceBytes, String encryptKeyBase64String) throws Exception {
        return encrypt(sourceBytes, getPublicKey(encryptKeyBase64String));
    }

    public static String encryptBase64StringByPrivateKey(byte[] sourceBytes, byte[] encryptKeyBytes) throws Exception {
        return Base64.encode(encryptByPrivateKey(sourceBytes, encryptKeyBytes));
    }

    public static String encryptBase64StringByPublicKey(byte[] sourceBytes, byte[] encryptKeyBytes) throws Exception {
        return Base64.encode(encryptByPublicKey(sourceBytes, encryptKeyBytes));
    }

    public static String encryptBase64StringByPrivateKey(byte[] sourceBytes, String encryptKeyBase64String) throws Exception {
        return Base64.encode(encryptByPrivateKey(sourceBytes, encryptKeyBase64String));
    }

    public static String encryptBase64StringByPublicKey(byte[] sourceBytes, String encryptKeyBase64String) throws Exception {
        return Base64.encode(encryptByPublicKey(sourceBytes, encryptKeyBase64String));
    }

    public static byte[] decrypt(byte[] encryptedBytes, Key decryptKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, decryptKey);
        byte[] decryptedResult = cipher.doFinal(encryptedBytes);
        return decryptedResult;
    }

}
