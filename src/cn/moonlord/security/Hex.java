package cn.moonlord.security;

import java.math.BigInteger;
import java.util.Locale;

public class Hex {

    public static String encode(byte[] bytes) {
        return String.format(Locale.ROOT, "%x", new BigInteger(1, bytes));
    }

}
