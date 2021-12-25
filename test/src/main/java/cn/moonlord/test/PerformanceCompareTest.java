package cn.moonlord.test;

public abstract class PerformanceCompareTest implements Runnable {

    public void onStarted() throws Exception { }

    public abstract void testMethod() throws Exception;

    public abstract void compareMethod() throws Exception;

    public void onFinished() throws Exception { }

    private int cycleOfRuns = 1;

    private long testMethodTotalRunTime = 0;

    private long compareMethodTotalRunTime = 0;

    private boolean isImproved = false;

    private long improvedPercentage = 0;

    private String improvement = "" + improvedPercentage + "%";

    public PerformanceCompareTest() { }

    public PerformanceCompareTest(int cycleOfRuns) {
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

            beginTime = System.currentTimeMillis();
            for (int i = 0; i < cycleOfRuns; i++) {
                compareMethod();
            }
            endTime = System.currentTimeMillis();
            compareMethodTotalRunTime = endTime - beginTime;

            isImproved = testMethodTotalRunTime < compareMethodTotalRunTime;
            double ratio = ((1 / (double) testMethodTotalRunTime) - (1 / (double) compareMethodTotalRunTime)) / (1 / (double) compareMethodTotalRunTime);
            improvedPercentage = Math.round(ratio * 100);
            improvement = "" + improvedPercentage + "%";

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

    public Long getCompareMethodTotalRunTime() {
        return compareMethodTotalRunTime;
    }

    public Long getTestMethodAverageRunTime() {
        return testMethodTotalRunTime / cycleOfRuns;
    }

    public Long getCompareMethodAverageRunTime() {
        return compareMethodTotalRunTime / cycleOfRuns;
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
