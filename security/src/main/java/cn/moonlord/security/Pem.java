package cn.moonlord.security;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;

import java.io.IOException;
import java.io.StringReader;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Pem {

    public static final int PEM_CHUNK_SIZE = 64;

    public static final String LINE_SEPARATOR = "\r\n";

    public static final String BEGIN_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----";

    public static final String END_PUBLIC_KEY = "-----END PUBLIC KEY-----";

    public static final String BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";

    public static final String END_PRIVATE_KEY = "-----END PRIVATE KEY-----";

    public static final String BEGIN_RSA_PUBLIC_KEY = "-----BEGIN RSA PUBLIC KEY-----";

    public static final String END_RSA_PUBLIC_KEY = "-----END RSA PUBLIC KEY-----";

    public static final String BEGIN_RSA_PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----";

    public static final String END_RSA_PRIVATE_KEY = "-----END RSA PRIVATE KEY-----";

    public static String encodePublicKey(PublicKey publicKey) {
        return BEGIN_PUBLIC_KEY + LINE_SEPARATOR + Base64.encodeMime(publicKey.getEncoded(), PEM_CHUNK_SIZE) + LINE_SEPARATOR + END_PUBLIC_KEY;
    }

    public static String encodePrivateKey(PrivateKey privateKey) {
        return BEGIN_PRIVATE_KEY + LINE_SEPARATOR + Base64.encodeMime(privateKey.getEncoded(), PEM_CHUNK_SIZE) + LINE_SEPARATOR + END_PRIVATE_KEY;
    }

    public static String encodeRsaPublicKey(PublicKey publicKey) {
        return BEGIN_RSA_PUBLIC_KEY + LINE_SEPARATOR + Base64.encodeMime(publicKey.getEncoded(), PEM_CHUNK_SIZE) + LINE_SEPARATOR + END_RSA_PUBLIC_KEY;
    }

    public static String encodeRsaPrivateKey(PrivateKey privateKey) {
        return BEGIN_RSA_PRIVATE_KEY + LINE_SEPARATOR + Base64.encodeMime(privateKey.getEncoded(), PEM_CHUNK_SIZE) + LINE_SEPARATOR + END_RSA_PRIVATE_KEY;
    }

    public static PublicKey decodeRsaPublicKey(String rsaPublicKeyBase64String) {
        try {
            PEMParser pemParser = new PEMParser(new StringReader(rsaPublicKeyBase64String));
            SubjectPublicKeyInfo publicKeyObject = (SubjectPublicKeyInfo) pemParser.readObject();
            return Rsa.getPublicKey(publicKeyObject.getEncoded());
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Pem decodeRsaPublicKey error, error message: " + e.getMessage(), e);
        }
    }

    public static PrivateKey decodeRsaPrivateKey(String rsaPrivateKeyBase64String) {
        try {
            PEMParser pemParser = new PEMParser(new StringReader(rsaPrivateKeyBase64String));
            PrivateKeyInfo privateKeyObject = (PrivateKeyInfo) pemParser.readObject();
            return Rsa.getPrivateKey(privateKeyObject.getEncoded());
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Pem decodeRsaPrivateKey error, error message: " + e.getMessage(), e);
        }
    }

}
