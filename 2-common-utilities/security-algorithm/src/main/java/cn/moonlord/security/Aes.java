package cn.moonlord.security;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 100% test
 */
public class Aes {

    public static final String AES_KEY_ALGORITHM = "AES";

    public static final String AES_CIPHER_INSTANCE = "AES_256/GCM/NoPadding";

    public static final int AES_KEY_BIT_LENGTH = 256;

    public static final int GCM_TAG_BIT_LENGTH = 128;

    public static final int GCM_IV_BIT_LENGTH = 96;

    public static final int ENCRYPTED_MIN_BYTE_LENGTH = (GCM_TAG_BIT_LENGTH + GCM_IV_BIT_LENGTH) / Byte.SIZE;

    public static final int SOURCE_MAX_BYTE_LENGTH = Integer.MAX_VALUE - ENCRYPTED_MIN_BYTE_LENGTH;

    static {
        init();
    }

    private synchronized static void init() {
        Provider.init();
    }

    public static SecretKeySpec generateKey() {
        return new SecretKeySpec(generateKeyBytes(), AES_KEY_ALGORITHM);
    }

    public static byte[] generateKeyBytes() {
        return Random.generateBits(AES_KEY_BIT_LENGTH);
    }

    public static String generateKeyBase64String() {
        return Base64.encode(generateKeyBytes());
    }

    public static SecretKeySpec getSecretKey(byte[] keyBytes) {
        if (keyBytes == null) {
            throw new IllegalArgumentException("Aes getSecretKey error, keyBytes must not be null");
        }
        if (keyBytes.length == 0) {
            throw new IllegalArgumentException("Aes getSecretKey error, keyBytes must not be empty");
        }
        if (keyBytes.length != AES_KEY_BIT_LENGTH / Byte.SIZE) {
            throw new IllegalArgumentException("Aes getSecretKey error, the length of keyBytes [ " + keyBytes.length + " ] must be " + (AES_KEY_BIT_LENGTH / Byte.SIZE));
        }
        return new SecretKeySpec(keyBytes, AES_KEY_ALGORITHM);
    }

    public static SecretKeySpec getSecretKey(String keyBase64String) {
        return getSecretKey(Base64.decode(keyBase64String));
    }

    public static byte[] encrypt(byte[] sourceBytes, SecretKeySpec encryptKey) {
        if (sourceBytes == null) {
            throw new IllegalArgumentException("Aes encrypt error, sourceBytes must not be null");
        }
        if (sourceBytes.length > SOURCE_MAX_BYTE_LENGTH) {
            throw new IllegalArgumentException("Aes encrypt error, the length of sourceBytes [ " + sourceBytes.length + " ] must not be larger than " + SOURCE_MAX_BYTE_LENGTH);
        }
        if (encryptKey == null) {
            throw new IllegalArgumentException("Aes encrypt error, encryptKey must not be null");
        }
        if (encryptKey.getEncoded() == null || encryptKey.getEncoded().length != AES_KEY_BIT_LENGTH / Byte.SIZE) {
            throw new IllegalArgumentException("Aes encrypt error, the length of encryptKey [ " + (encryptKey.getEncoded() != null ? encryptKey.getEncoded().length : null) + " ] must be " + (AES_KEY_BIT_LENGTH / Byte.SIZE));
        }

        byte[] iv = Random.generateBits(GCM_IV_BIT_LENGTH);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_BIT_LENGTH, iv);

        try {
            Cipher cipher = Cipher.getInstance(AES_CIPHER_INSTANCE);
            cipher.init(Cipher.ENCRYPT_MODE, encryptKey, gcmSpec);
            byte[] encryptedSource = cipher.doFinal(sourceBytes);

            byte[] encryptedResult = new byte[iv.length + encryptedSource.length];
            System.arraycopy(iv, 0, encryptedResult, 0, iv.length);
            System.arraycopy(encryptedSource, 0, encryptedResult, iv.length, encryptedSource.length);
            return encryptedResult;
        } catch (Exception e) {
            throw new IllegalArgumentException("Aes encrypt error, error message: " + e.getMessage(), e);
        }
    }

    public static byte[] encrypt(byte[] sourceBytes, byte[] encryptKeyBytes) {
        return encrypt(sourceBytes, getSecretKey(encryptKeyBytes));
    }

    public static byte[] encrypt(byte[] sourceBytes, String encryptKeyBase64String) {
        return encrypt(sourceBytes, getSecretKey(encryptKeyBase64String));
    }

    public static byte[] encrypt(String sourceString, SecretKeySpec encryptKey) {
        if (sourceString == null) {
            throw new IllegalArgumentException("Aes encrypt error, sourceString must not be null");
        }
        byte[] sourceBytes = sourceString.getBytes(StandardCharsets.UTF_8);
        return encrypt(sourceBytes, encryptKey);
    }

    public static byte[] encrypt(String sourceString, byte[] encryptKeyBytes) {
        if (sourceString == null) {
            throw new IllegalArgumentException("Aes encrypt error, sourceString must not be null");
        }
        byte[] sourceBytes = sourceString.getBytes(StandardCharsets.UTF_8);
        return encrypt(sourceBytes, encryptKeyBytes);
    }

    public static byte[] encrypt(String sourceString, String encryptKeyBase64String) {
        if (sourceString == null) {
            throw new IllegalArgumentException("Aes encrypt error, sourceString must not be null");
        }
        byte[] sourceBytes = sourceString.getBytes(StandardCharsets.UTF_8);
        return encrypt(sourceBytes, encryptKeyBase64String);
    }

    public static byte[] decrypt(byte[] encryptedBytes, SecretKeySpec decryptKey) {
        if (encryptedBytes == null) {
            throw new IllegalArgumentException("Aes decrypt error, encryptedBytes must not be null");
        }
        if (encryptedBytes.length == 0) {
            throw new IllegalArgumentException("Aes decrypt error, encryptedBytes must not be empty");
        }
        if (encryptedBytes.length < ENCRYPTED_MIN_BYTE_LENGTH) {
            throw new IllegalArgumentException("Aes decrypt error, the length of encryptedBytes [ " + encryptedBytes.length + " ] must not be smaller than " + ENCRYPTED_MIN_BYTE_LENGTH);
        }
        if (decryptKey == null) {
            throw new IllegalArgumentException("Aes decrypt error, decryptKey must not be null");
        }
        if (decryptKey.getEncoded() == null || decryptKey.getEncoded().length != AES_KEY_BIT_LENGTH / Byte.SIZE) {
            throw new IllegalArgumentException("Aes decrypt error, the length of decryptKey [ " + (decryptKey.getEncoded() != null ? decryptKey.getEncoded().length : null) + " ] must be " + (AES_KEY_BIT_LENGTH / Byte.SIZE));
        }

        byte[] iv = Arrays.copyOfRange(encryptedBytes, 0, GCM_IV_BIT_LENGTH / Byte.SIZE);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_BIT_LENGTH, iv);

        try {
            Cipher cipher = Cipher.getInstance(AES_CIPHER_INSTANCE);
            cipher.init(Cipher.DECRYPT_MODE, decryptKey, gcmSpec);
            return cipher.doFinal(encryptedBytes, iv.length, encryptedBytes.length - iv.length);
        } catch (Exception e) {
            throw new IllegalArgumentException("Aes decrypt error, error message: " + e.getMessage(), e);
        }
    }

    public static byte[] decrypt(byte[] encryptedBytes, byte[] decryptKeyBytes) {
        return decrypt(encryptedBytes, getSecretKey(decryptKeyBytes));
    }

    public static byte[] decrypt(byte[] encryptedBytes, String decryptKeyBase64String) {
        return decrypt(encryptedBytes, getSecretKey(decryptKeyBase64String));
    }

    public static String decryptString(byte[] encryptedBytes, SecretKeySpec decryptKey) {
        byte[] sourceBytes = decrypt(encryptedBytes, decryptKey);
        return new String(sourceBytes, StandardCharsets.UTF_8);
    }

    public static String decryptString(byte[] encryptedBytes, byte[] decryptKeyBytes) {
        byte[] sourceBytes = decrypt(encryptedBytes, decryptKeyBytes);
        return new String(sourceBytes, StandardCharsets.UTF_8);
    }

    public static String decryptString(byte[] encryptedBytes, String decryptKeyBase64String) {
        byte[] sourceBytes = decrypt(encryptedBytes, decryptKeyBase64String);
        return new String(sourceBytes, StandardCharsets.UTF_8);
    }

}
