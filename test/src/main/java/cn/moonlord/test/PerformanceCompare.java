package cn.moonlord.test;

public abstract class PerformanceCompare implements Runnable {

    public abstract void testMethod() throws Exception;

    public abstract void compareMethod() throws Exception;

    public abstract void onCompleted() throws Exception;

    private int numberOfRuns = 1;

    private long testMethodRunTime = 0;

    private long compareMethodRunTime = 0;

    private long improvementPercentage = 0;

    private String improvement = "" + improvementPercentage + "%";

    private boolean isImproved = false;

    public PerformanceCompare() { }

    public PerformanceCompare(int numberOfRuns) {
        this.numberOfRuns = numberOfRuns;
    }

    @Override
    public void run() {
        try {
            long beginTime = System.currentTimeMillis();
            for (int i = 0; i < numberOfRuns; i++) {
                testMethod();
            }
            long endTime = System.currentTimeMillis();
            testMethodRunTime = endTime - beginTime;

            beginTime = System.currentTimeMillis();
            for (int i = 0; i < numberOfRuns; i++) {
                compareMethod();
            }
            endTime = System.currentTimeMillis();
            compareMethodRunTime = endTime - beginTime;

            double ratio = ((1 / (double) testMethodRunTime) - (1 / (double) compareMethodRunTime)) / (1 / (double) compareMethodRunTime);
            improvementPercentage = Math.round(ratio * 100);
            improvement = "" + improvementPercentage + "%";
            isImproved = testMethodRunTime < compareMethodRunTime;

            onCompleted();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void startInNewThread(){
        new Thread(this).start();
    }

    public Integer getNumberOfRuns() {
        return numberOfRuns;
    }

    public Long getTestMethodRunTime() {
        return testMethodRunTime;
    }

    public Long getCompareMethodRunTime() {
        return compareMethodRunTime;
    }

    public Long getImprovementPercentage() {
        return improvementPercentage;
    }

    public String getImprovement() {
        return improvement;
    }

    public Boolean isImproved() {
        return isImproved;
    }

}
