package cn.moonlord.security;

import java.util.Arrays;

public class Hex {

    public static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static final int SOURCE_MAX_BYTE_LENGTH = Integer.MAX_VALUE / 2;

    private static final byte[] HEX_CHAR_TO_UNSIGNED_BYTE = new byte[getMaxHexChar() + 1];

    static {
        init();
    }

    private static int getMaxHexChar() {
        int maxChar = -1;
        for (char hexChar : HEX_CHARS) {
            maxChar = Math.max(maxChar, hexChar);
            maxChar = Math.max(maxChar, Character.toUpperCase(hexChar));
        }
        return maxChar;
    }

    public static synchronized void init() {
        Arrays.fill(HEX_CHAR_TO_UNSIGNED_BYTE, (byte) 0xFF);
        for (int i = 0; i < HEX_CHARS.length; i++) {
            HEX_CHAR_TO_UNSIGNED_BYTE[HEX_CHARS[i]] = (byte) i;
            HEX_CHAR_TO_UNSIGNED_BYTE[Character.toUpperCase(HEX_CHARS[i])] = (byte) i;
        }
    }

    public static String encode(byte[] sourceBytes) {
        if (sourceBytes == null) {
            throw new IllegalArgumentException("Hex encode error, sourceBytes must not be null");
        }
        if (sourceBytes.length > SOURCE_MAX_BYTE_LENGTH) {
            throw new IllegalArgumentException("Hex encode error, the length of sourceBytes [ " + sourceBytes.length + " ] must not be larger than " + SOURCE_MAX_BYTE_LENGTH);
        }
        char[] result = new char[sourceBytes.length * 2];
        int resultIndex = 0;
        for (byte sourceByte : sourceBytes) {
            int mappingIndex = Byte.toUnsignedInt(sourceByte);
            result[resultIndex++] = HEX_CHARS[mappingIndex / 16];
            result[resultIndex++] = HEX_CHARS[mappingIndex % 16];
        }
        return new String(result);
    }

    public static byte[] decode(String sourceString) {
        if (sourceString == null) {
            throw new IllegalArgumentException("Hex decode error, sourceString must not be null");
        }
        if (sourceString.length() % 2 != 0) {
            throw new IllegalArgumentException("Hex decode error, the length of sourceString [ " + sourceString.length() + " ] must be a multiple of 2");
        }
        char[] sourceChars = sourceString.toCharArray();
        byte[] result = new byte[sourceString.length() / 2];
        for (int i = 0; i < result.length; i++) {
            char firstChar = sourceChars[i * 2];
            if (firstChar >= HEX_CHAR_TO_UNSIGNED_BYTE.length) {
                throw new IllegalArgumentException("Hex decode error, sourceString  [ " + (i * 2) + " : " + firstChar + " ] must only contain ASCII characters");
            }
            byte firstCharIndex = HEX_CHAR_TO_UNSIGNED_BYTE[firstChar];
            if (firstCharIndex == (byte) 0xFF) {
                throw new IllegalArgumentException("Hex decode error, sourceString  [ " + (i * 2) + " : " + firstChar + " ] must only contain hexadecimal characters");
            }
            char secondChar = sourceChars[i * 2 + 1];
            if (secondChar >= HEX_CHAR_TO_UNSIGNED_BYTE.length) {
                throw new IllegalArgumentException("Hex decode error, sourceString  [ " + (i * 2) + " : " + secondChar + " ] must only contain ASCII characters");
            }
            byte secondCharIndex = HEX_CHAR_TO_UNSIGNED_BYTE[secondChar];
            if (secondCharIndex == (byte) 0xFF) {
                throw new IllegalArgumentException("Hex decode error, sourceString  [ " + (i * 2 + 1) + " : " + secondChar + " ] must only contain hexadecimal characters");
            }
            result[i] = (byte) (firstCharIndex * 16 + secondCharIndex);
        }
        return result;
    }

}
