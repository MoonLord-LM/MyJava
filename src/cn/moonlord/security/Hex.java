package cn.moonlord.security;

import java.math.BigInteger;

public class Hex {

    private static final int HEX_RADIX = 16;

    private static final String PADDING_ZERO = "0";

    public static String encode(byte[] sourceBytes) {
        long paddingLength = sourceBytes.length * 2;
        String buffer = String.format("%" + PADDING_ZERO + paddingLength + "x", new BigInteger(sourceBytes));
        return buffer;
    }

    public static byte[] decode(String sourceString) {
        return new BigInteger(sourceString, HEX_RADIX).toByteArray();
    }

}
