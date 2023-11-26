package cn.moonlord.test;

public class PerformanceTest implements ExceptionRunnable {

    public final int cycleOfRuns;

    public final ExceptionRunnable testMethod;

    public PerformanceTest(int cycleOfRuns, ExceptionRunnable testMethod) {
        if (cycleOfRuns < 1) {
            throw new IllegalArgumentException("cycleOfRuns must be larger than 0");
        }
        this.cycleOfRuns = cycleOfRuns;
        this.testMethod = testMethod;
    }

    public long beginTime;

    public long endTime;

    @Override
    public void run() {
        try {
            beginTime = System.currentTimeMillis();
            for (int i = 0; i < cycleOfRuns; i++) {
                testMethod.run();
            }
            endTime = System.currentTimeMillis();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Long getTotalRunTime() {
        return endTime - beginTime;
    }

    public Long getAverageRunTime() {
        return getTotalRunTime() / cycleOfRuns;
    }

}
