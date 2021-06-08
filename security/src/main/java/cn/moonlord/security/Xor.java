package cn.moonlord.security;

public class Xor {

    public static byte sum(byte[] source) {
        if(source == null){
            throw new IllegalArgumentException("Xor sum error, source must not be null");
        }
        if(source.length == 0){
            throw new IllegalArgumentException("Xor sum error, source must not be empty");
        }
        byte result = source[0];
        for (int i = 1; i < source.length; i++) {
            result = (byte) (result ^ source[i]);
        }
        return result;
    }

    public static byte[] merge(byte[] source1, byte[] source2) {
        if(source1 == null){
            throw new IllegalArgumentException("Xor merge error, source1 must not be null");
        }
        if(source1.length == 0){
            throw new IllegalArgumentException("Xor merge error, source1 must not be empty");
        }
        if(source2 == null){
            throw new IllegalArgumentException("Xor merge error, source2 must not be null");
        }
        if(source2.length == 0){
            throw new IllegalArgumentException("Xor merge error, source2 must not be empty");
        }
        if(source1.length != source2.length){
            throw new IllegalArgumentException("Xor merge error, the length of source1 [" + source1.length + "] must be equal with the length of source2 [" + source2.length + "]");
        }
        byte[] result = new byte[source1.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (source1[i] ^ source2[i]);
        }
        return result;
    }

}