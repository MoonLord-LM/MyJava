package cn.moonlord.test;

public abstract class Performance implements Runnable {

    public void onStarted() throws Exception { }

    public abstract void testMethod() throws Exception;

    public void onCompleted() throws Exception { }

    private int cycleOfRuns = 1;

    private long testMethodRunTime = 0;

    public Performance() { }

    public Performance(int cycleOfRuns) {
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

            onCompleted();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void startInNewThread(){
        new Thread(this).start();
    }

    public Integer getCycleOfRuns() {
        return cycleOfRuns;
    }

    public Long getTestMethodRunTime() {
        return testMethodRunTime;
    }

}
