package cn.moonlord.test;

@FunctionalInterface
public interface ExceptionRunnable {

    void test() throws Exception;

    default Object run() {
        try {
            test();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
