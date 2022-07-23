package cn.moonlord.security;

import java.security.MessageDigest;

public class Hash {

    public static final String SHA256 = "SHA-256";

    public static final String SHA512 = "SHA-512";

    static {
        init();
    }

    private synchronized static void init() {
        Provider.init();
    }

    public static byte[] sha256(byte[] sourceBytes) {
        if (sourceBytes == null) {
            throw new IllegalArgumentException("Hash sha256 error, sourceBytes must not be null");
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA256);
            return messageDigest.digest(sourceBytes);
        } catch (Exception e) {
            throw new IllegalArgumentException("Hash sha256 error, error message: " + e.getMessage(), e);
        }
    }

    public static String sha256Hex(byte[] sourceBytes) {
        return Hex.encode(sha256(sourceBytes));
    }

    public static byte[] sha512(byte[] sourceBytes) {
        if (sourceBytes == null) {
            throw new IllegalArgumentException("Hash sha512 error, sourceBytes must not be null");
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA512);
            return messageDigest.digest(sourceBytes);
        } catch (Exception e) {
            throw new IllegalArgumentException("Hash sha512 error, error message: " + e.getMessage(), e);
        }
    }

    public static String sha512Hex(byte[] sourceBytes) {
        return Hex.encode(sha512(sourceBytes));
    }

}
