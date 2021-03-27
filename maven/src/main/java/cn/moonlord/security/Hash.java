package cn.moonlord.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public static final String SHA256 = "SHA-256";
    public static final String SHA512 = "SHA-512";

    public static byte[] sha256(byte[] sourceBytes) {
        if(sourceBytes == null){
            throw new IllegalArgumentException("Hash sha256 error, sourceBytes must not be null");
        }
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(SHA256);
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
        return messageDigest.digest(sourceBytes);
    }

    public static byte[] sha256(String sourceString) {
        if(sourceString == null){
            throw new IllegalArgumentException("Hash sha256 error, sourceString must not be null");
        }
        return sha256(sourceString.getBytes(StandardCharsets.UTF_8));
    }

    public static String sha256Hex(byte[] sourceBytes) {
        return Hex.encode(sha256(sourceBytes));
    }

    public static String sha256Hex(String sourceString) {
        return Hex.encode(sha256(sourceString));
    }

    public static byte[] sha512(byte[] sourceBytes) {
        if(sourceBytes == null){
            throw new IllegalArgumentException("Hash sha512 error, sourceBytes must not be null");
        }
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(SHA512);
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
        return messageDigest.digest(sourceBytes);
    }

    public static byte[] sha512(String sourceString) {
        if(sourceString == null){
            throw new IllegalArgumentException("Hash sha512 error, sourceString must not be null");
        }
        return sha512(sourceString.getBytes(StandardCharsets.UTF_8));
    }

    public static String sha512Hex(byte[] sourceBytes) {
        return Hex.encode(sha512(sourceBytes));
    }

    public static String sha512Hex(String sourceString) {
        return Hex.encode(sha512(sourceString));
    }

}
