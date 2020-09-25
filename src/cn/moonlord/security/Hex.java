package cn.moonlord.security;

import java.math.BigInteger;

public class Hex {

    public static String encode(byte[] bytes) {
        return String.format("%x", new BigInteger(1, bytes));
    }

}
