package cn.moonlord.security;

import java.util.Arrays;

public class Hex {

    public static final char[] HEX_CHARS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static final int SOURCE_MAX_BYTE_LENGTH = Integer.MAX_VALUE / 2;

    private static final char[] UNSIGNED_BYTE_TO_HEX_CHAR = new char[512];

    private static final byte[] HEX_CHAR_TO_UNSIGNED_BYTE = new byte[128];

    static {
        init();
    }

    public static synchronized void init() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int mappingIndex = (i * 16 + j) * 2;
                UNSIGNED_BYTE_TO_HEX_CHAR[mappingIndex] = HEX_CHARS[i];
                UNSIGNED_BYTE_TO_HEX_CHAR[mappingIndex + 1] = HEX_CHARS[j];
            }
        }
        Arrays.fill(HEX_CHAR_TO_UNSIGNED_BYTE, (byte) 0xFF);
        for (int i = 0; i < HEX_CHARS.length; i++) {
            HEX_CHAR_TO_UNSIGNED_BYTE[HEX_CHARS[i]] = (byte) i;
            HEX_CHAR_TO_UNSIGNED_BYTE[Character.toUpperCase(HEX_CHARS[i])] = (byte) i;
        }
    }

    public static String encode(byte[] sourceBytes) {
        if(sourceBytes == null) {
            throw new IllegalArgumentException("Hex encode error, sourceBytes must not be null");
        }
        if(sourceBytes.length > SOURCE_MAX_BYTE_LENGTH) {
            throw new IllegalArgumentException("Hex encode error, the length of sourceBytes [ " + sourceBytes.length + " ] must not be larger than " + SOURCE_MAX_BYTE_LENGTH);
        }
        char[] result = new char[sourceBytes.length * 2];
        int resultIndex = 0;
        for (byte sourceByte : sourceBytes) {
            int mappingIndex = Byte.toUnsignedInt(sourceByte) * 2;
            result[resultIndex++] = UNSIGNED_BYTE_TO_HEX_CHAR[mappingIndex++];
            result[resultIndex++] = UNSIGNED_BYTE_TO_HEX_CHAR[mappingIndex];
        }
        return new String(result);
    }

    public static byte[] decode(String sourceString) {
        if(sourceString == null) {
            throw new IllegalArgumentException("Hex decode error, sourceString must not be null");
        }
        if(sourceString.length() % 2 != 0) {
            throw new IllegalArgumentException("Hex decode error, the length of sourceString [ " + sourceString.length() + " ] must be a multiple of 2");
        }
        char[] sourceChars = sourceString.toCharArray();
        byte[] result = new byte[sourceString.length() / 2];
        for (int i = 0; i < result.length; i++) {
            byte firstDigitIndex = HEX_CHAR_TO_UNSIGNED_BYTE[sourceChars[i * 2]];
            byte secondDigitIndex = HEX_CHAR_TO_UNSIGNED_BYTE[sourceChars[i * 2 + 1]];
            if(firstDigitIndex == -1) {
                throw new IllegalArgumentException("Hex decode error, sourceString  [ " + ( i * 2 ) + " : " + sourceChars[i * 2] + " ] must only contain hexadecimal characters");
            }
            if(secondDigitIndex == -1) {
                throw new IllegalArgumentException("Hex decode error, sourceString  [ " + ( i * 2 + 1 ) + " : " + sourceChars[i * 2 + 1] + " ] must only contain hexadecimal characters");
            }
            result[i] = (byte) (firstDigitIndex * 16 + secondDigitIndex);
        }
        return result;
    }

}
