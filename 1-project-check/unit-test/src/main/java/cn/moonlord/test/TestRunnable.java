package cn.moonlord.test;

@FunctionalInterface
public interface TestRunnable {

    Object run() throws Exception;

    TestRunnable EMPTY = () -> null;

}
