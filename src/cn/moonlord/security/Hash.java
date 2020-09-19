package cn.moonlord.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Hash {

    private static final String SHA256 = "SHA-256";
    private static final String SHA512 = "SHA-512";

    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public static String SHA256(byte[] sourceBytes) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance(SHA256);
        byte digest[] = messageDigest.digest(sourceBytes);
        return byte2Hex(digest);
    }

    public static String SHA256(String sourceString) throws Exception {
        return SHA256(sourceString.getBytes(StandardCharsets.UTF_8));
    }

    public static String SHA512(byte[] sourceBytes) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance(SHA512);
        byte digest[] = messageDigest.digest(sourceBytes);
        return byte2Hex(digest);
    }

    public static String SHA512(String sourceString) throws Exception {
        return SHA512(sourceString.getBytes(StandardCharsets.UTF_8));
    }

}
