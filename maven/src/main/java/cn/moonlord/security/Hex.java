package cn.moonlord.security;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Hex {

    private static final List<Character> HEX_CHARS = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' );

    private static final char[] UNSIGNED_BYTE_MAPPING_HEX_CHARS_1 = new char[256];
    private static final char[] UNSIGNED_BYTE_MAPPING_HEX_CHARS_2 = new char[256];

    static {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                UNSIGNED_BYTE_MAPPING_HEX_CHARS_1[i * 16 + j] = HEX_CHARS.get(i).charValue();
                UNSIGNED_BYTE_MAPPING_HEX_CHARS_2[i * 16 + j] = HEX_CHARS.get(j).charValue();
            }
        }
    }

    public static String encode(byte[] sourceBytes) {
        if(sourceBytes == null){
            throw new IllegalArgumentException("Hex encode error, sourceBytes must not be null");
        }
        if(sourceBytes.length == 0){
            throw new IllegalArgumentException("Hex encode error, sourceBytes must not be empty");
        }
        if(sourceBytes.length > Integer.MAX_VALUE / 2){
            throw new IllegalArgumentException("Hex encode error, sourceBytes length must not be larger than " + ( Integer.MAX_VALUE / 2 ));
        }
        byte[] result = new byte[sourceBytes.length * 2];
        for (int i = 0; i < sourceBytes.length; i++) {
            result[i * 2] = UNSIGNED_BYTE_MAPPING_HEX_CHARS_1[Byte.toUnsignedInt(sourceBytes[i])];
            result[i * 2 + 1] = UNSIGNED_BYTE_MAPPING_HEX_CHARS_2[Byte.toUnsignedInt(sourceBytes[i])];
        }
        return new String(result, StandardCharsets.UTF_8);
    }

    public static byte[] decode(String sourceString) {
        if(sourceString == null){
            throw new IllegalArgumentException("Hex decode error, sourceString must not be null");
        }
        if(sourceString.length() == 0){
            throw new IllegalArgumentException("Hex decode error, sourceString must not be empty");
        }
        if(sourceString.length() % 2 == 1){
            throw new IllegalArgumentException("Hex decode error, sourceString length must be a multiple of 2");
        }
        byte[] sourceBytes = sourceString.getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[sourceString.length() / 2];
        for (int i = 0; i < result.length; i++) {
            int firstDigitIndex = (byte) HEX_CHARS.indexOf((char) sourceBytes[i * 2]);
            int secondDigitIndex = (byte) HEX_CHARS.indexOf((char) sourceBytes[i * 2 + 1]);
            if(firstDigitIndex == -1 || secondDigitIndex == -1){
                throw new IllegalArgumentException("Hex decode error, sourceString must only contain hexadecimal characters");
            }
            result[i] = (byte) (firstDigitIndex * 16 + secondDigitIndex);
        }
        return result;
    }

}
