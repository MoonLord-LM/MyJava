package cn.moonlord.test;

public abstract class Performance implements Runnable {

    public abstract void testMethod() throws Exception;

    public abstract void onStarted() throws Exception;

    public abstract void onCompleted() throws Exception;

    private int numberOfRuns = 1;

    private long testMethodRunTime = 0;

    public Performance() { }

    public Performance(int numberOfRuns) {
        this.numberOfRuns = numberOfRuns;
    }

    @Override
    public void run() {
        try {
            onStarted();

            long beginTime = System.currentTimeMillis();
            for (int i = 0; i < numberOfRuns; i++) {
                testMethod();
            }
            long endTime = System.currentTimeMillis();
            testMethodRunTime = endTime - beginTime;

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

}
