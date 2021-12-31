package cn.moonlord.security;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;

import java.io.StringReader;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Pem {

    public static final int PEM_CHUNK_LENGTH = 64;

    public static final String PEM_LINE_SEPARATOR = "\r\n";

    public static final String BEGIN_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----";

    public static final String END_PUBLIC_KEY = "-----END PUBLIC KEY-----";

    public static final String BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";

    public static final String END_PRIVATE_KEY = "-----END PRIVATE KEY-----";

    public static final String BEGIN_RSA_PUBLIC_KEY = "-----BEGIN RSA PUBLIC KEY-----";

    public static final String END_RSA_PUBLIC_KEY = "-----END RSA PUBLIC KEY-----";

    public static final String BEGIN_RSA_PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----";

    public static final String END_RSA_PRIVATE_KEY = "-----END RSA PRIVATE KEY-----";

    public static String encodePublicKey(PublicKey publicKey) {
        return BEGIN_PUBLIC_KEY + PEM_LINE_SEPARATOR + Base64.encodeMime(publicKey.getEncoded(), PEM_CHUNK_LENGTH) + PEM_LINE_SEPARATOR + END_PUBLIC_KEY;
    }

    public static String encodePrivateKey(PrivateKey privateKey) {
        return BEGIN_PRIVATE_KEY + PEM_LINE_SEPARATOR + Base64.encodeMime(privateKey.getEncoded(), PEM_CHUNK_LENGTH) + PEM_LINE_SEPARATOR + END_PRIVATE_KEY;
    }

    public static String encodeRsaPublicKey(PublicKey publicKey) {
        try {
            SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(publicKey.getEncoded());
            ASN1Primitive asn1Primitive = subjectPublicKeyInfo.parsePublicKey();
            return BEGIN_RSA_PUBLIC_KEY + PEM_LINE_SEPARATOR + Base64.encodeMime(asn1Primitive.getEncoded(), PEM_CHUNK_LENGTH) + PEM_LINE_SEPARATOR + END_RSA_PUBLIC_KEY;
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Pem encodeRsaPublicKey error, error message: " + e.getMessage(), e);
        }
    }

    public static String encodeRsaPrivateKey(PrivateKey privateKey) {
        try {
            PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(privateKey.getEncoded());
            ASN1Primitive asn1Primitive = privateKeyInfo.parsePrivateKey().toASN1Primitive();
            return BEGIN_RSA_PRIVATE_KEY + PEM_LINE_SEPARATOR + Base64.encodeMime(asn1Primitive.getEncoded(), PEM_CHUNK_LENGTH) + PEM_LINE_SEPARATOR + END_RSA_PRIVATE_KEY;
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Pem encodeRsaPrivateKey error, error message: " + e.getMessage(), e);
        }
    }

    public static PublicKey decodeRsaPublicKey(String rsaPublicKeyPemString) {
        try {
            PEMParser pemParser = new PEMParser(new StringReader(rsaPublicKeyPemString));
            SubjectPublicKeyInfo publicKeyObject = (SubjectPublicKeyInfo) pemParser.readObject();
            return Rsa.getPublicKey(publicKeyObject.getEncoded());
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Pem decodeRsaPublicKey error, error message: " + e.getMessage(), e);
        }
    }

    public static PrivateKey decodeRsaPrivateKey(String rsaPrivateKeyPemString) {
        try {
            PEMParser pemParser = new PEMParser(new StringReader(rsaPrivateKeyPemString));
            Object object = pemParser.readObject();
            if(object instanceof PrivateKeyInfo) {
                return Rsa.getPrivateKey(((PrivateKeyInfo) object).getEncoded());
            }
            else if (object instanceof PEMKeyPair) {
                return Rsa.getPrivateKey(((PEMKeyPair) object).getPrivateKeyInfo().getEncoded());
            }
            throw new IllegalArgumentException("Pem decodeRsaPrivateKey error, unknown object type: " + object.getClass().getName());
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Pem decodeRsaPrivateKey error, error message: " + e.getMessage(), e);
        }
    }

}
