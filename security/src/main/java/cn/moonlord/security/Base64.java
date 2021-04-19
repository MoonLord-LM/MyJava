package cn.moonlord.security;

import java.nio.charset.StandardCharsets;

public class Base64 {

    public static String encode(byte[] sourceBytes) {
        byte[] buffer = java.util.Base64.getEncoder().encode(sourceBytes);
        return new String(buffer,StandardCharsets.UTF_8);
    }

    public static byte[] decode(String sourceString) {
        return java.util.Base64.getDecoder().decode(sourceString.getBytes(StandardCharsets.UTF_8));
    }

}
