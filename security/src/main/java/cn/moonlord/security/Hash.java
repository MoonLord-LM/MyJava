package cn.moonlord.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public static final String SHA256 = "SHA-256";

    public static final String SHA512 = "SHA-512";

    private static MessageDigest messageDigestSHA256;

    private static MessageDigest messageDigestSHA512;

    static {
        init();
    }

    public static void init(){
        try {
            messageDigestSHA256 = MessageDigest.getInstance(SHA256);
            messageDigestSHA512 = MessageDigest.getInstance(SHA512);
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

    public static byte[] sha256(byte[] sourceBytes) {
        if(sourceBytes == null){
            throw new IllegalArgumentException("Hash sha256 error, sourceBytes must not be null");
        }
        return messageDigestSHA256.digest(sourceBytes);
    }

    public static String sha256Hex(byte[] sourceBytes) {
        return Hex.encode(sha256(sourceBytes));
    }

    public static byte[] sha512(byte[] sourceBytes) {
        if(sourceBytes == null){
            throw new IllegalArgumentException("Hash sha512 error, sourceBytes must not be null");
        }
        return messageDigestSHA512.digest(sourceBytes);
    }

    public static String sha512Hex(byte[] sourceBytes) {
        return Hex.encode(sha512(sourceBytes));
    }

}
