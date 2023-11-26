package cn.moonlord.test;

public abstract class PerformanceTest implements ExceptionRunnable {

    private int cycleOfRuns = 1;

    public int getCycleOfRuns() {
        return cycleOfRuns;
    }

    public void setCycleOfRuns(int cycleOfRuns) {
        if (cycleOfRuns < 1) {
            throw new IllegalArgumentException("cycleOfRuns must be larger than 0");
        }
        this.cycleOfRuns = cycleOfRuns;
    }

    public long beginTime;

    public long endTime;

    @Override
    public void run() {
        try {
            beginTime = System.currentTimeMillis();
            for (int i = 0; i < getCycleOfRuns(); i++) {
                test();
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
        return getTotalRunTime() / getCycleOfRuns();
    }

}
