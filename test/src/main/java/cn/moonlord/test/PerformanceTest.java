package cn.moonlord.test;

public abstract class PerformanceTest implements Runnable {

    public void onStarted() throws Exception { }

    public abstract void testMethod() throws Exception;

    public void onFinished() throws Exception { }

    private int cycleOfRuns = 1;

    private long testMethodTotalRunTime = 0;

    public PerformanceTest() { }

    public PerformanceTest(int cycleOfRuns) {
        if(cycleOfRuns < 1) {
            throw new IllegalArgumentException("cycleOfRuns must not be smaller than 1");
        }
        this.cycleOfRuns = cycleOfRuns;
    }

    @Override
    public void run() {
        try {
            onStarted();

            long beginTime = System.currentTimeMillis();
            for (int i = 0; i < cycleOfRuns; i++) {
                testMethod();
            }
            long endTime = System.currentTimeMillis();
            testMethodTotalRunTime = endTime - beginTime;

            onFinished();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getCycleOfRuns() {
        return cycleOfRuns;
    }

    public Long getTestMethodTotalRunTime() {
        return testMethodTotalRunTime;
    }

    public Long getTestMethodAverageRunTime() {
        return testMethodTotalRunTime / cycleOfRuns;
    }

}
