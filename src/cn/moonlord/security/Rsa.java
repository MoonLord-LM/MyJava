package cn.moonlord.security;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Rsa {

    private static final String RSA_KEY_ALGORITHM = "RSA";
    private static final String RSA_CIPHER_INSTANCE = "RSA/ECB/OAEPPadding";

    private static final String OAEP_DIGEST_ALGORITHM = "SHA-512";
    private static final String MGF1_NAME = "MGF1";
    private static final String MGF1_DIGEST_ALGORITHM = "SHA-512";

    private final static int RSA_KEY_LENGTH = 4096;
    private final static int SOURCE_BLOCK_SIZE = 382;
    private final static int ENCRYPTED_BLOCK_SIZE = 512;

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

    public static byte[] encrypt(byte[] sourceBytes, PublicKey encryptKey) throws Exception {
        if(encryptKey.getEncoded().length < RSA_KEY_LENGTH  / Byte.SIZE){
            throw new InvalidAlgorithmParameterException("encrypt key length is not match, the length should be " + RSA_KEY_LENGTH);
        }

        int blockCount = sourceBytes.length / SOURCE_BLOCK_SIZE;
        blockCount = blockCount + (sourceBytes.length % SOURCE_BLOCK_SIZE == 0 ? 0 : 1);
        blockCount = blockCount + (sourceBytes.length == 0 ? 1 : 0);
        byte[] encryptedResult = new byte[blockCount * ENCRYPTED_BLOCK_SIZE];
        int encryptedOffset = 0;
        int sourceOffset = 0;

        while(sourceOffset == 0 || sourceOffset < sourceBytes.length){
            int sourceLength = Math.min(sourceBytes.length - sourceOffset, SOURCE_BLOCK_SIZE);
            MGF1ParameterSpec mgf1Spec = new MGF1ParameterSpec(MGF1_DIGEST_ALGORITHM);
            OAEPParameterSpec oaepSpec = new OAEPParameterSpec(OAEP_DIGEST_ALGORITHM, MGF1_NAME, mgf1Spec, PSource.PSpecified.DEFAULT);
            Cipher cipher = Cipher.getInstance(RSA_CIPHER_INSTANCE);
            cipher.init(Cipher.ENCRYPT_MODE, encryptKey, oaepSpec);
            cipher.doFinal(sourceBytes, sourceOffset, sourceLength, encryptedResult, encryptedOffset);
            sourceOffset += SOURCE_BLOCK_SIZE;
            encryptedOffset += ENCRYPTED_BLOCK_SIZE;
        }
        return encryptedResult;
    }

    public static byte[] encrypt(byte[] sourceBytes, byte[] encryptKeyBytes) throws Exception {
        return encrypt(sourceBytes, getPublicKey(encryptKeyBytes));
    }

    public static byte[] encrypt(byte[] sourceBytes, String encryptKeyBase64String) throws Exception {
        return encrypt(sourceBytes, getPublicKey(encryptKeyBase64String));
    }

    public static byte[] encrypt(String sourceString, PublicKey encryptKey) throws Exception {
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

    public static String encryptToBase64String(byte[] sourceBytes, PublicKey encryptKey) throws Exception {
        return Base64.encode(encrypt(sourceBytes, encryptKey));
    }

    public static String encryptToBase64String(byte[] sourceBytes, byte[] encryptKeyBytes) throws Exception {
        return Base64.encode(encrypt(sourceBytes, encryptKeyBytes));
    }

    public static String encryptToBase64String(byte[] sourceBytes, String encryptKeyBase64String) throws Exception {
        return Base64.encode(encrypt(sourceBytes, encryptKeyBase64String));
    }

    public static String encryptToBase64String(String sourceString, PublicKey encryptKey) throws Exception {
        byte[] sourceBytes = sourceString.getBytes(StandardCharsets.UTF_8);
        return encryptToBase64String(sourceBytes, encryptKey);
    }

    public static String encryptToBase64String(String sourceString, byte[] encryptKeyBytes) throws Exception {
        byte[] sourceBytes = sourceString.getBytes(StandardCharsets.UTF_8);
        return encryptToBase64String(sourceBytes, encryptKeyBytes);
    }

    public static String encryptToBase64String(String sourceString, String encryptKeyBase64String) throws Exception {
        byte[] sourceBytes = sourceString.getBytes(StandardCharsets.UTF_8);
        return encryptToBase64String(sourceBytes, encryptKeyBase64String);
    }

    public static byte[] decrypt(byte[] encryptedBytes, PrivateKey decryptKey) throws Exception {
        if(decryptKey.getEncoded().length < RSA_KEY_LENGTH  / Byte.SIZE){
            throw new InvalidAlgorithmParameterException("decrypt key length is not match, the length should be " + RSA_KEY_LENGTH);
        }

        int blockCount = encryptedBytes.length / ENCRYPTED_BLOCK_SIZE;
        byte[] buffer = new byte[blockCount * SOURCE_BLOCK_SIZE + ENCRYPTED_BLOCK_SIZE];
        int sourceLength = 0;
        int sourceOffset = 0;
        int encryptedOffset = 0;

        while(encryptedOffset < encryptedBytes.length) {
            MGF1ParameterSpec mgf1Spec = new MGF1ParameterSpec(MGF1_DIGEST_ALGORITHM);
            OAEPParameterSpec oaepSpec = new OAEPParameterSpec(OAEP_DIGEST_ALGORITHM, MGF1_NAME, mgf1Spec, PSource.PSpecified.DEFAULT);
            Cipher cipher = Cipher.getInstance(RSA_CIPHER_INSTANCE);
            cipher.init(Cipher.DECRYPT_MODE, decryptKey, oaepSpec);
            sourceLength =  sourceLength + cipher.doFinal(encryptedBytes, encryptedOffset, ENCRYPTED_BLOCK_SIZE, buffer, sourceOffset);
            encryptedOffset += ENCRYPTED_BLOCK_SIZE;
            sourceOffset += SOURCE_BLOCK_SIZE;
        }

        byte[] decryptedResult = new byte[sourceLength];
        System.arraycopy(buffer, 0, decryptedResult, 0, sourceLength);
        return decryptedResult;
    }

    public static byte[] decrypt(byte[] encryptedBytes, byte[] decryptKeyBytes) throws Exception {
        return decrypt(encryptedBytes, getPrivateKey(decryptKeyBytes));
    }

    public static byte[] decrypt(byte[] encryptedBytes, String decryptKeyBase64String) throws Exception {
        return decrypt(encryptedBytes, getPrivateKey(decryptKeyBase64String));
    }

    public static String decryptString(byte[] encryptedBytes, PrivateKey decryptKey) throws Exception {
        return new String(decrypt(encryptedBytes, decryptKey), StandardCharsets.UTF_8);
    }

    public static String decryptString(byte[] encryptedBytes, byte[] decryptKeyBytes) throws Exception {
        return new String(decrypt(encryptedBytes, decryptKeyBytes), StandardCharsets.UTF_8);
    }

    public static String decryptString(byte[] encryptedBytes, String decryptKeyBase64String) throws Exception {
        return new String(decrypt(encryptedBytes, decryptKeyBase64String), StandardCharsets.UTF_8);
    }

    public static byte[] decryptFromBase64String(String encryptedBase64String, PrivateKey decryptKey) throws Exception {
        return decrypt(Base64.decode(encryptedBase64String), decryptKey);
    }

    public static byte[] decryptFromBase64String(String encryptedBase64String, byte[] decryptKeyBytes) throws Exception {
        return decrypt(Base64.decode(encryptedBase64String), decryptKeyBytes);
    }

    public static byte[] decryptFromBase64String(String encryptedBase64String, String decryptKeyBase64String) throws Exception {
        return decrypt(Base64.decode(encryptedBase64String), decryptKeyBase64String);
    }

    public static String decryptStringFromBase64String(String encryptedBase64String, PrivateKey decryptKey) throws Exception {
        return new String(decryptFromBase64String(encryptedBase64String, decryptKey), StandardCharsets.UTF_8);
    }

    public static String decryptStringFromBase64String(String encryptedBase64String, byte[] decryptKeyBytes) throws Exception {
        return new String(decryptFromBase64String(encryptedBase64String, decryptKeyBytes), StandardCharsets.UTF_8);
    }

    public static String decryptStringFromBase64String(String encryptedBase64String, String decryptKeyBase64String) throws Exception {
        return new String(decryptFromBase64String(encryptedBase64String, decryptKeyBase64String), StandardCharsets.UTF_8);
    }

}
