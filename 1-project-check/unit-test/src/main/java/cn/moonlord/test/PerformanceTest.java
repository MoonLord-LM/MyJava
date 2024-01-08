package cn.moonlord.test;

public abstract class PerformanceTest implements ExceptionRunnable {

    private int cycleOfRuns = 1;

    public int getCycleOfRuns() {
        return cycleOfRuns;
    }

    public PerformanceTest setCycleOfRuns(int cycleOfRuns) {
        if (cycleOfRuns < 1) {
            throw new IllegalArgumentException("cycleOfRuns must be larger than 0");
        }
        this.cycleOfRuns = cycleOfRuns;
        return this;
    }

    public long beginTime;

    public long endTime;

    @Override
    public PerformanceTest run() {
        try {
            beginTime = System.currentTimeMillis();
            for (int i = 0; i < getCycleOfRuns(); i++) {
                test();
            }
            endTime = System.currentTimeMillis();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public Long getTotalRunTime() {
        return endTime - beginTime;
    }

    public Long getAverageRunTime() {
        return getTotalRunTime() / getCycleOfRuns();
    }

}
