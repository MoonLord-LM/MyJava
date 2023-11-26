package cn.moonlord.test;

public abstract class PerformanceTest implements java.lang.Runnable {

    private Runnable testMethod = Runnable.EMPTY;

    public void onStarted() throws Exception {
    }

    public void testMethod() throws Exception {
        testMethod.run();
    }

    public void onFinished() throws Exception {
    }

    private int cycleOfRuns = 1;

    private boolean shouldRun = true;

    private long testMethodTotalRunTime = 0;

    public PerformanceTest() {
    }

    public PerformanceTest(int cycleOfRuns) {
        if (cycleOfRuns < 1) {
            throw new IllegalArgumentException("cycleOfRuns must not be smaller than 1");
        }
        this.cycleOfRuns = cycleOfRuns;
    }

    public PerformanceTest(int cycleOfRuns, Runnable testMethod) {
        if (cycleOfRuns < 1) {
            throw new IllegalArgumentException("cycleOfRuns must not be smaller than 1");
        }
        this.cycleOfRuns = cycleOfRuns;
        this.testMethod = testMethod;
    }

    @Override
    public void run() {
        try {
            if (!shouldRun()) {
                return;
            }

            onStarted();

            long beginTime;
            long endTime;

            for (int i = 0; i < cycleOfRuns; i++) {
                beginTime = System.currentTimeMillis();
                testMethod();
                endTime = System.currentTimeMillis();
                testMethodTotalRunTime += (endTime - beginTime);
            }

            onFinished();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getCycleOfRuns() {
        return cycleOfRuns;
    }

    public PerformanceTest setCycleOfRuns(int cycleOfRuns) {
        this.cycleOfRuns = cycleOfRuns;
        return this;
    }

    public Boolean shouldRun() {
        return shouldRun;
    }

    public PerformanceTest setRunOnSystemProperty(String property, String value) {
        if (System.getProperty(property) == null || !System.getProperty(property).equals(value)) {
            shouldRun = false;
        }
        return this;
    }

    public PerformanceTest setRunOnWindowsOnly() {
        return setRunOnSystemProperty("os.name", "Windows");
    }

    public Long getTestMethodTotalRunTime() {
        return testMethodTotalRunTime;
    }

    public Long getTestMethodAverageRunTime() {
        return testMethodTotalRunTime / cycleOfRuns;
    }

}
