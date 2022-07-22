package cn.moonlord.security;

import java.nio.charset.StandardCharsets;

public class Base64 {

    public static final int MIME_CHUNK_MAX_LENGTH = 76;

    public static final byte[] MIME_LINE_SEPARATOR = new byte[] {'\r', '\n'};

    public static String encode(byte[] sourceBytes) {
        if(sourceBytes == null) {
            throw new IllegalArgumentException("Base64 encode error, sourceBytes must not be null");
        }
        byte[] buffer = java.util.Base64.getEncoder().encode(sourceBytes);
        return new String(buffer,StandardCharsets.UTF_8);
    }

    public static String encodeUrlSafe(byte[] sourceBytes) {
        if(sourceBytes == null) {
            throw new IllegalArgumentException("Base64 encodeUrlSafe error, sourceBytes must not be null");
        }
        byte[] buffer = java.util.Base64.getUrlEncoder().encode(sourceBytes);
        return new String(buffer,StandardCharsets.UTF_8);
    }

    public static String encodeMime(byte[] sourceBytes, int chunkLength) {
        if(sourceBytes == null) {
            throw new IllegalArgumentException("Base64 encodeMime error, sourceBytes must not be null");
        }
        if(chunkLength <= 0) {
            throw new IllegalArgumentException("Base64 encodeMime error, chunkLength must be larger than 0");
        }
        if(chunkLength > MIME_CHUNK_MAX_LENGTH) {
            throw new IllegalArgumentException("Base64 encodeMime error, chunkLength [ " + chunkLength + " ] must not be larger than " + MIME_CHUNK_MAX_LENGTH);
        }
        byte[] buffer = java.util.Base64.getMimeEncoder(chunkLength, MIME_LINE_SEPARATOR).encode(sourceBytes);
        return new String(buffer,StandardCharsets.UTF_8);
    }

    public static String encodeMime(byte[] sourceBytes) {
        return encodeMime(sourceBytes, Base64.MIME_CHUNK_MAX_LENGTH);
    }

    public static byte[] decode(String sourceString) {
        if(sourceString == null) {
            throw new IllegalArgumentException("Base64 decode error, sourceString must not be null");
        }
        return java.util.Base64.getDecoder().decode(sourceString.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] decodeUrlSafe(String sourceString) {
        if(sourceString == null) {
            throw new IllegalArgumentException("Base64 decodeUrlSafe error, sourceString must not be null");
        }
        return java.util.Base64.getUrlDecoder().decode(sourceString.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] decodeMime(String sourceString) {
        if(sourceString == null) {
            throw new IllegalArgumentException("Base64 decodeMime error, sourceString must not be null");
        }
        return java.util.Base64.getMimeDecoder().decode(sourceString.getBytes(StandardCharsets.UTF_8));
    }

}
