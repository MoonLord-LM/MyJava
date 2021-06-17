package cn.moonlord.security;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Pem {

    public static final int LINE_LENGTH = 64;

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
        return BEGIN_PUBLIC_KEY + LINE_SEPARATOR + Base64.encodeMime(publicKey.getEncoded(), LINE_LENGTH) + LINE_SEPARATOR + END_PUBLIC_KEY;
    }

    public static String encodePrivateKey(PrivateKey privateKey) {
        return BEGIN_PRIVATE_KEY + LINE_SEPARATOR + Base64.encodeMime(privateKey.getEncoded(), LINE_LENGTH) + LINE_SEPARATOR + END_PRIVATE_KEY;
    }

    public static String encodeRsaPublicKey(PublicKey publicKey) {
        return BEGIN_RSA_PUBLIC_KEY + LINE_SEPARATOR + Base64.encodeMime(publicKey.getEncoded(), LINE_LENGTH) + LINE_SEPARATOR + END_RSA_PUBLIC_KEY;
    }

    public static String encodeRsaPrivateKey(PrivateKey privateKey) {
        return BEGIN_RSA_PRIVATE_KEY + LINE_SEPARATOR + Base64.encodeMime(privateKey.getEncoded(), LINE_LENGTH) + LINE_SEPARATOR + END_RSA_PRIVATE_KEY;
    }

}
