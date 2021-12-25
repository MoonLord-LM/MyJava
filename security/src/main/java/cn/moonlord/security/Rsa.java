package cn.moonlord.security;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Rsa {

    public static final String RSA_KEY_ALGORITHM = "RSA";

    public static final String RSA_CIPHER_INSTANCE = "RSA/ECB/OAEPPadding";

    public static final String OAEP_DIGEST_ALGORITHM = "SHA-512";

    public static final String OAEP_MGF_ALGORITHM = "MGF1";

    public static final String MGF1_DIGEST_ALGORITHM = "SHA-512";

    public static final int RSA_KEY_BIT_LENGTH = 15360;

    public static final int SOURCE_MAX_BYTE_LENGTH = 1790;

    public static final int ENCRYPTED_BYTE_LENGTH = 1920;

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_KEY_ALGORITHM);
            keyPairGenerator.initialize(RSA_KEY_BIT_LENGTH);
            return keyPairGenerator.generateKeyPair();
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Rsa generateKeyPair error, error message: " + e.getMessage(), e);
        }
    }

    public static PublicKey getPublicKey(KeyPair keyPair) {
        if(keyPair == null) {
            throw new IllegalArgumentException("Rsa getPublicKey error, keyPair must not be null");
        }
        PublicKey publicKey = keyPair.getPublic();
        if(publicKey == null) {
            throw new IllegalArgumentException("Rsa getPublicKey error, publicKey must not be null");
        }
        if(publicKey.getEncoded() == null) {
            throw new IllegalArgumentException("Rsa getPublicKey error, publicKey must not be empty");
        }
        return publicKey;
    }

    public static PrivateKey getPrivateKey(KeyPair keyPair) {
        if(keyPair == null) {
            throw new IllegalArgumentException("Rsa getPrivateKey error, keyPair must not be null");
        }
        PrivateKey privateKey = keyPair.getPrivate();
        if(privateKey == null) {
            throw new IllegalArgumentException("Rsa getPrivateKey error, privateKey must not be null");
        }
        if(privateKey.getEncoded() == null) {
            throw new IllegalArgumentException("Rsa getPrivateKey error, privateKey must not be empty");
        }
        return keyPair.getPrivate();
    }

    public static byte[] getPublicKeyBytes(KeyPair keyPair) {
        return getPublicKey(keyPair).getEncoded();
    }

    public static byte[] getPrivateKeyBytes(KeyPair keyPair) {
        return getPrivateKey(keyPair).getEncoded();
    }

    public static byte[] getPublicKeyBytes(PublicKey publicKey) {
        return publicKey.getEncoded();
    }

    public static byte[] getPrivateKeyBytes(PrivateKey privateKey) {
        return privateKey.getEncoded();
    }

    public static String getPublicKeyBase64String(KeyPair keyPair) {
        return Base64.encode(getPublicKeyBytes(keyPair));
    }

    public static String getPrivateKeyBase64String(KeyPair keyPair) {
        return Base64.encode(getPrivateKeyBytes(keyPair));
    }

    public static String getPublicKeyBase64String(PublicKey publicKey) {
        return Base64.encode(getPublicKeyBytes(publicKey));
    }

    public static String getPrivateKeyBase64String(PrivateKey privateKey) {
        return Base64.encode(getPrivateKeyBytes(privateKey));
    }

    public static PublicKey getPublicKey(byte[] publicKeyBytes) {
        try {
            return KeyFactory.getInstance(RSA_KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Rsa getPublicKey error, error message: " + e.getMessage(), e);
        }
    }

    public static PrivateKey getPrivateKey(byte[] privateKeyBytes) {
        try {
            return KeyFactory.getInstance(RSA_KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Rsa getPrivateKey error, error message: " + e.getMessage(), e);
        }
    }

    public static PublicKey getPublicKey(String publicKeyBase64String) {
        return getPublicKey(Base64.decode(publicKeyBase64String));
    }

    public static PrivateKey getPrivateKey(String privateKeyBase64String) {
        return getPrivateKey(Base64.decode(privateKeyBase64String));
    }

    public static byte[] encrypt(byte[] sourceBytes, PublicKey publicKey) {
        if(sourceBytes == null) {
            throw new IllegalArgumentException("Rsa encrypt error, sourceBytes must not be null");
        }
        if(sourceBytes.length > SOURCE_MAX_BYTE_LENGTH) {
            throw new IllegalArgumentException("Rsa encrypt error, the length of sourceBytes [ " + sourceBytes.length + " ] must not be larger than " + SOURCE_MAX_BYTE_LENGTH);
        }
        if(publicKey == null) {
            throw new IllegalArgumentException("Rsa encrypt error, publicKey must not be null");
        }
        if(publicKey.getEncoded() == null) {
            throw new IllegalArgumentException("Rsa encrypt error, publicKey must not be empty");
        }
        if(publicKey.getEncoded().length <= RSA_KEY_BIT_LENGTH / Byte.SIZE) {
            throw new IllegalArgumentException("Rsa encrypt error, the length of publicKey [ " + publicKey.getEncoded().length + " ] must be larger than " + ( RSA_KEY_BIT_LENGTH / Byte.SIZE ));
        }

        try {
            MGF1ParameterSpec mgf1Spec = new MGF1ParameterSpec(MGF1_DIGEST_ALGORITHM);
            OAEPParameterSpec oaepSpec = new OAEPParameterSpec(OAEP_DIGEST_ALGORITHM, OAEP_MGF_ALGORITHM, mgf1Spec, PSource.PSpecified.DEFAULT);
            Cipher cipher = Cipher.getInstance(RSA_CIPHER_INSTANCE);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey, oaepSpec);
            return cipher.doFinal(sourceBytes);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Rsa encrypt error, error message: " + e.getMessage(), e);
        }
    }

    public static byte[] encrypt(byte[] sourceBytes, byte[] publicKeyBytes) {
        return encrypt(sourceBytes, getPublicKey(publicKeyBytes));
    }

    public static byte[] encrypt(byte[] sourceBytes, String publicKeyBase64String) {
        return encrypt(sourceBytes, getPublicKey(publicKeyBase64String));
    }

    public static byte[] encrypt(String sourceString, PublicKey publicKey) {
        byte[] sourceBytes = sourceString.getBytes(StandardCharsets.UTF_8);
        return encrypt(sourceBytes, publicKey);
    }

    public static byte[] encrypt(String sourceString, byte[] publicKeyBytes) {
        byte[] sourceBytes = sourceString.getBytes(StandardCharsets.UTF_8);
        return encrypt(sourceBytes, publicKeyBytes);
    }

    public static byte[] encrypt(String sourceString, String publicKeyBase64String) {
        byte[] sourceBytes = sourceString.getBytes(StandardCharsets.UTF_8);
        return encrypt(sourceBytes, publicKeyBase64String);
    }

    public static byte[] decrypt(byte[] encryptedBytes, PrivateKey privateKey) {
        if(encryptedBytes == null) {
            throw new IllegalArgumentException("Rsa decrypt error, encryptedBytes must not be null");
        }
        if(encryptedBytes.length != ENCRYPTED_BYTE_LENGTH) {
            throw new IllegalArgumentException("Rsa decrypt error, the length of encryptedBytes [ " + encryptedBytes.length + " ] must be " + ENCRYPTED_BYTE_LENGTH);
        }
        if(privateKey == null) {
            throw new IllegalArgumentException("Rsa decrypt error, privateKey must not be null");
        }
        if(privateKey.getEncoded() == null) {
            throw new IllegalArgumentException("Rsa decrypt error, privateKey must not be empty");
        }
        if(privateKey.getEncoded().length <= RSA_KEY_BIT_LENGTH / Byte.SIZE) {
            throw new IllegalArgumentException("Rsa decrypt error, the length of privateKey [ " + privateKey.getEncoded().length + " ] must be larger than " + ( RSA_KEY_BIT_LENGTH / Byte.SIZE ));
        }

        try {
            MGF1ParameterSpec mgf1Spec = new MGF1ParameterSpec(MGF1_DIGEST_ALGORITHM);
            OAEPParameterSpec oaepSpec = new OAEPParameterSpec(OAEP_DIGEST_ALGORITHM, OAEP_MGF_ALGORITHM, mgf1Spec, PSource.PSpecified.DEFAULT);
            Cipher cipher = Cipher.getInstance(RSA_CIPHER_INSTANCE);
            cipher.init(Cipher.DECRYPT_MODE, privateKey, oaepSpec);
            return cipher.doFinal(encryptedBytes);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Rsa decrypt error, error message: " + e.getMessage(), e);
        }
    }

    public static byte[] decrypt(byte[] encryptedBytes, byte[] privateKeyBytes) {
        return decrypt(encryptedBytes, getPrivateKey(privateKeyBytes));
    }

    public static byte[] decrypt(byte[] encryptedBytes, String privateKeyBase64String) {
        return decrypt(encryptedBytes, getPrivateKey(privateKeyBase64String));
    }

    public static String decryptString(byte[] encryptedBytes, PrivateKey privateKey) {
        return new String(decrypt(encryptedBytes, privateKey), StandardCharsets.UTF_8);
    }

    public static String decryptString(byte[] encryptedBytes, byte[] privateKeyBytes) {
        return new String(decrypt(encryptedBytes, privateKeyBytes), StandardCharsets.UTF_8);
    }

    public static String decryptString(byte[] encryptedBytes, String privateKeyBase64String) {
        return new String(decrypt(encryptedBytes, privateKeyBase64String), StandardCharsets.UTF_8);
    }

}
