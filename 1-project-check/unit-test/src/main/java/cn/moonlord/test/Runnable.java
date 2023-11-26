package cn.moonlord.test;

@FunctionalInterface
public interface Runnable {

    Object run() throws Exception;

    Runnable EMPTY = () -> null;

}
