package cn.moonlord.test;

public abstract class PerformanceCompareTest implements Runnable {

    public void onStarted() throws Exception { }

    public abstract void testMethod() throws Exception;

    public abstract void compareMethod() throws Exception;

    public void onCompleted() throws Exception { }

    private int cycleOfRuns = 1;

    private long testMethodRunTime = 0;

    private long compareMethodRunTime = 0;

    private boolean isImproved = false;

    private long improvedPercentage = 0;

    private String improvement = "" + improvedPercentage + "%";

    public PerformanceCompareTest() { }

    public PerformanceCompareTest(int cycleOfRuns) {
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
            testMethodRunTime = endTime - beginTime;

            beginTime = System.currentTimeMillis();
            for (int i = 0; i < cycleOfRuns; i++) {
                compareMethod();
            }
            endTime = System.currentTimeMillis();
            compareMethodRunTime = endTime - beginTime;

            isImproved = testMethodRunTime < compareMethodRunTime;
            double ratio = ((1 / (double) testMethodRunTime) - (1 / (double) compareMethodRunTime)) / (1 / (double) compareMethodRunTime);
            improvedPercentage = Math.round(ratio * 100);
            improvement = "" + improvedPercentage + "%";

            onCompleted();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getCycleOfRuns() {
        return cycleOfRuns;
    }

    public Long getTestMethodRunTime() {
        return testMethodRunTime;
    }

    public Long getCompareMethodRunTime() {
        return compareMethodRunTime;
    }

    public Long getImprovedPercentage() {
        return improvedPercentage;
    }

    public String getImprovement() {
        return improvement;
    }

    public Boolean isImproved() {
        return isImproved;
    }

}
