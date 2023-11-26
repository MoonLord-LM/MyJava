package cn.moonlord.test;

@FunctionalInterface
public interface ExceptionRunnable extends Runnable {

    void test() throws Exception;

    @Override
    default void run() {
        try {
            test();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
