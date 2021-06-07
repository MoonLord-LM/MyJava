package cn.moonlord.security;

import java.nio.charset.StandardCharsets;

public class Base64 {

    public static String encode(byte[] sourceBytes) {
        if(sourceBytes == null){
            throw new IllegalArgumentException("Base64 encode error, sourceBytes must not be null");
        }
        byte[] buffer = java.util.Base64.getEncoder().encode(sourceBytes);
        return new String(buffer,StandardCharsets.UTF_8);
    }

    public static byte[] decode(String sourceString) {
        if(sourceString == null){
            throw new IllegalArgumentException("Base64 decode error, sourceString must not be null");
        }
        return java.util.Base64.getDecoder().decode(sourceString.getBytes(StandardCharsets.UTF_8));
    }

    public static String encode(byte[] sourceBytes, int lineLength, String lineSeparator) {
        if(sourceBytes == null){
            throw new IllegalArgumentException("Base64 encode error, sourceBytes must not be null");
        }
        if(lineLength == 0){
            throw new IllegalArgumentException("Base64 encode error, lineLength must be larger than 0");
        }
        if(lineSeparator == null){
            throw new IllegalArgumentException("Base64 encode error, lineSeparator must not be null");
        }
        if(lineSeparator.isEmpty()){
            throw new IllegalArgumentException("Base64 encode error, lineSeparator must not be empty");
        }
        byte[] buffer = java.util.Base64.getMimeEncoder(lineLength, lineSeparator.getBytes(StandardCharsets.UTF_8)).encode(sourceBytes);
        return new String(buffer,StandardCharsets.UTF_8);
    }

}
