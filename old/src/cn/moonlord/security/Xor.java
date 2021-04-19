package cn.moonlord.security;

import java.security.InvalidAlgorithmParameterException;

public class Xor {

    public static byte[] compute(byte[] component1, byte[] component2) throws Exception {
        if(component1.length != component2.length){
            throw new InvalidAlgorithmParameterException("component1 length [" + component1.length + "] is not match with component2 length [" + component2.length + "]");
        }
        byte[] result = new byte[component1.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (component1[i] ^ component2[i]);
        }
        return result;
    }

}
