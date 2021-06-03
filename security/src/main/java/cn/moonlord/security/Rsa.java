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

    private static final int RSA_KEY_LENGTH = 15360;

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_KEY_ALGORITHM);
            keyPairGenerator.initialize(RSA_KEY_LENGTH);
            return keyPairGenerator.generateKeyPair();
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Rsa generateKeyPair error, error message: " + e.getMessage(), e);
        }
    }

    public static PrivateKey getPrivateKey(KeyPair keyPair) {
        if(keyPair == null){
            throw new IllegalArgumentException("Rsa getPrivateKey error, keyPair must not be null");
        }
        return keyPair.getPrivate();
    }

    public static PublicKey getPublicKey(KeyPair keyPair) {
        if(keyPair == null){
            throw new IllegalArgumentException("Rsa getPrivateKey error, keyPair must not be null");
        }
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

    public static byte[] encrypt(byte[] sourceBytes, PublicKey encryptKey) {
        if(encryptKey.getEncoded().length < RSA_KEY_LENGTH  / Byte.SIZE){
            throw new IllegalArgumentException("encrypt key length is not match, the length should be " + RSA_KEY_LENGTH);
        }

        try {
            MGF1ParameterSpec mgf1Spec = new MGF1ParameterSpec(MGF1_DIGEST_ALGORITHM);
            OAEPParameterSpec oaepSpec = new OAEPParameterSpec(OAEP_DIGEST_ALGORITHM, MGF1_NAME, mgf1Spec, PSource.PSpecified.DEFAULT);
            Cipher cipher = Cipher.getInstance(RSA_CIPHER_INSTANCE);
            cipher.init(Cipher.ENCRYPT_MODE, encryptKey, oaepSpec);
            return cipher.doFinal(sourceBytes);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Rsa encrypt error, error message: " + e.getMessage(), e);
        }
    }

    public static byte[] encrypt(byte[] sourceBytes, byte[] encryptKeyBytes) throws Exception {
        return encrypt(sourceBytes, getPublicKey(encryptKeyBytes));
    }

    public static byte[] encrypt(byte[] sourceBytes, String encryptKeyBase64String) throws Exception {
        return encrypt(sourceBytes, getPublicKey(encryptKeyBase64String));
    }

    public static byte[] encrypt(String sourceString, PublicKey encryptKey) {
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

    public static byte[] decrypt(byte[] encryptedBytes, PrivateKey decryptKey) {
        if(decryptKey.getEncoded().length < RSA_KEY_LENGTH  / Byte.SIZE){
            throw new IllegalArgumentException("decrypt key length is not match, the length should be " + RSA_KEY_LENGTH);
        }

        try {
            MGF1ParameterSpec mgf1Spec = new MGF1ParameterSpec(MGF1_DIGEST_ALGORITHM);
            OAEPParameterSpec oaepSpec = new OAEPParameterSpec(OAEP_DIGEST_ALGORITHM, MGF1_NAME, mgf1Spec, PSource.PSpecified.DEFAULT);
            Cipher cipher = Cipher.getInstance(RSA_CIPHER_INSTANCE);
            cipher.init(Cipher.DECRYPT_MODE, decryptKey, oaepSpec);
            return cipher.doFinal(encryptedBytes);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Rsa encrypt error, error message: " + e.getMessage(), e);
        }
    }

    public static byte[] decrypt(byte[] encryptedBytes, byte[] decryptKeyBytes) throws Exception {
        return decrypt(encryptedBytes, getPrivateKey(decryptKeyBytes));
    }

    public static byte[] decrypt(byte[] encryptedBytes, String decryptKeyBase64String) throws Exception {
        return decrypt(encryptedBytes, getPrivateKey(decryptKeyBase64String));
    }

    public static String decryptString(byte[] encryptedBytes, PrivateKey decryptKey) {
        return new String(decrypt(encryptedBytes, decryptKey), StandardCharsets.UTF_8);
    }

    public static String decryptString(byte[] encryptedBytes, byte[] decryptKeyBytes) throws Exception {
        return new String(decrypt(encryptedBytes, decryptKeyBytes), StandardCharsets.UTF_8);
    }

    public static String decryptString(byte[] encryptedBytes, String decryptKeyBase64String) throws Exception {
        return new String(decrypt(encryptedBytes, decryptKeyBase64String), StandardCharsets.UTF_8);
    }

}
