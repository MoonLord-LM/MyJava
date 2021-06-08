package cn.moonlord.security;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.util.Locale;

public class Hex {

    private static final int HEX_RADIX = 16;

    private static final String PADDING_ZERO = "0";

    public static String encode(byte[] sourceBytes) throws Exception {
        if(sourceBytes == null || sourceBytes.length == 0){
            throw new InvalidAlgorithmParameterException("encode source bytes must not be empty");
        }
        long paddingLength = sourceBytes.length * 2;
        return String.format("%" + PADDING_ZERO + paddingLength + "x", new BigInteger(1, sourceBytes), Locale.ROOT);
    }

    public static byte[] decode(String sourceString) throws Exception {
        if(sourceString == null || sourceString.length() == 0){
            throw new InvalidAlgorithmParameterException("decode source string must not be empty");
        }
        if(sourceString.length() % 2 == 1){
            throw new InvalidAlgorithmParameterException("decode source string length must be a multiple of 2");
        }
        int bytesLength = sourceString.length() / 2;
        byte[] tmp = new BigInteger(sourceString, HEX_RADIX).toByteArray();
        if(tmp.length > bytesLength){
            byte[] buffer = new byte[bytesLength];
            System.arraycopy(tmp, tmp.length - bytesLength, buffer, 0, bytesLength);
            return buffer;
        }
        else if(tmp.length < bytesLength) {
            byte[] buffer = new byte[bytesLength];
            System.arraycopy(tmp, 0, buffer, bytesLength - tmp.length, tmp.length);
            return buffer;
        }
        return tmp;
    }

}