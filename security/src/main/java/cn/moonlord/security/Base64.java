package cn.moonlord.security;

import java.nio.charset.StandardCharsets;

/**
 * 100% test
 */
public class Base64 {

    public static final int MIME_LINE_MAX_LENGTH = 76;

    public static final byte[] MIME_LINE_SEPARATOR_BYTES = new byte[]{'\r', '\n'};

    public static final String MIME_LINE_SEPARATOR_STRING = "\r\n";

    public static String encode(byte[] sourceBytes) {
        if (sourceBytes == null) {
            throw new IllegalArgumentException("Base64 encode error, sourceBytes must not be null");
        }
        byte[] buffer = java.util.Base64.getEncoder().encode(sourceBytes);
        return new String(buffer, StandardCharsets.UTF_8);
    }

    public static String encodeUrlSafe(byte[] sourceBytes) {
        if (sourceBytes == null) {
            throw new IllegalArgumentException("Base64 encodeUrlSafe error, sourceBytes must not be null");
        }
        byte[] buffer = java.util.Base64.getUrlEncoder().encode(sourceBytes);
        return new String(buffer, StandardCharsets.UTF_8);
    }

    public static String encodeMime(byte[] sourceBytes, int lineLength) {
        if (sourceBytes == null) {
            throw new IllegalArgumentException("Base64 encodeMime error, sourceBytes must not be null");
        }
        if (lineLength <= 0) {
            throw new IllegalArgumentException("Base64 encodeMime error, lineLength must be larger than 0");
        }
        if (lineLength > MIME_LINE_MAX_LENGTH) {
            throw new IllegalArgumentException("Base64 encodeMime error, lineLength [ " + lineLength + " ] must not be larger than " + MIME_LINE_MAX_LENGTH);
        }
        byte[] buffer = java.util.Base64.getMimeEncoder(lineLength, MIME_LINE_SEPARATOR_BYTES).encode(sourceBytes);
        return new String(buffer, StandardCharsets.UTF_8);
    }

    public static String encodeMime(byte[] sourceBytes) {
        return encodeMime(sourceBytes, Base64.MIME_LINE_MAX_LENGTH);
    }

    public static byte[] decode(String sourceString) {
        if (sourceString == null) {
            throw new IllegalArgumentException("Base64 decode error, sourceString must not be null");
        }
        sourceString = sourceString.replace(MIME_LINE_SEPARATOR_STRING, "");
        sourceString = sourceString.replace("-", "+");
        sourceString = sourceString.replace("_", "/");
        return java.util.Base64.getDecoder().decode(sourceString.getBytes(StandardCharsets.UTF_8));
    }

}
