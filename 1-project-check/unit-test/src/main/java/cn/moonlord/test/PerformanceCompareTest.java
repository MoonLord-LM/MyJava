package cn.moonlord.test;

public abstract class PerformanceCompareTest implements java.lang.Runnable {

    private Runnable testMethod = Runnable.EMPTY;

    private Runnable compareMethod = Runnable.EMPTY;

    public void onStarted() throws Exception {
    }

    public void testMethod() throws Exception {
        testMethod.run();
    }

    public void compareMethod() throws Exception {
        compareMethod.run();
    }

    public void onFinished() throws Exception {
    }

    private int cycleOfRuns = 1;

    private boolean shouldRun = true;

    private long testMethodTotalRunTime = 0;

    private long compareMethodTotalRunTime = 0;

    private boolean isImproved = false;

    private long improvedPercentage = 0;

    private String improvement = improvedPercentage + "%";

    public PerformanceCompareTest() {
    }

    public PerformanceCompareTest(int cycleOfRuns) {
        if (cycleOfRuns < 1) {
            throw new IllegalArgumentException("cycleOfRuns must not be smaller than 1");
        }
        this.cycleOfRuns = cycleOfRuns;
    }

    public PerformanceCompareTest(int cycleOfRuns, Runnable testMethod, Runnable compareMethod) {
        if (cycleOfRuns < 1) {
            throw new IllegalArgumentException("cycleOfRuns must not be smaller than 1");
        }
        this.cycleOfRuns = cycleOfRuns;
        this.testMethod = testMethod;
        this.compareMethod = compareMethod;
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

                beginTime = System.currentTimeMillis();
                compareMethod();
                endTime = System.currentTimeMillis();
                compareMethodTotalRunTime += (endTime - beginTime);
            }

            isImproved = testMethodTotalRunTime < compareMethodTotalRunTime;
            double ratio = ((1 / (double) testMethodTotalRunTime) - (1 / (double) compareMethodTotalRunTime)) / (1 / (double) compareMethodTotalRunTime);
            improvedPercentage = Math.round(ratio * 100);
            improvement = "" + improvedPercentage + "%";

            onFinished();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getCycleOfRuns() {
        return cycleOfRuns;
    }

    public PerformanceCompareTest setCycleOfRuns(int cycleOfRuns) {
        this.cycleOfRuns = cycleOfRuns;
        return this;
    }

    public Boolean shouldRun() {
        return shouldRun;
    }

    public PerformanceCompareTest setRunOnSystemProperty(String property, String value) {
        if (System.getProperty(property) == null || !System.getProperty(property).equals(value)) {
            shouldRun = false;
        }
        return this;
    }

    public PerformanceCompareTest setRunOnWindowsOnly() {
        return setRunOnSystemProperty("os.name", "Windows");
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
