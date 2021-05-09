package cn.moonlord.test;

public abstract class PerformanceCompare implements Runnable {

    public abstract void testMethod();

    public abstract void compareMethod();

    public abstract void onCompleted();

    private final int numberOfRuns;

    private long testMethodRunTime;

    private long compareMethodRunTime;

    private String improvementRadio;

    public PerformanceCompare(int numberOfRuns){
        this.numberOfRuns = numberOfRuns;
    }

    @Override
    public void run() {
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

        double ratio = ( ( 1 / (double) testMethodRunTime ) -  ( 1 / (double) compareMethodRunTime ) ) / ( 1 / (double) compareMethodRunTime );
        improvementRadio = Math.round(ratio * 100) + "%";

        onCompleted();
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

    public String getImprovementRadio() {
        return improvementRadio;
    }

}
