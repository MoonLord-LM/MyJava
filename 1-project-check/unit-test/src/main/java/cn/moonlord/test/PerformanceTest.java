package cn.moonlord.test;

public abstract class PerformanceTest {

    private int cycleOfRuns = 1;
    private long totalRunTimeMs = 0;

    abstract void test() throws Exception;

    public PerformanceTest run() {
        try {
            long beginTime = System.currentTimeMillis();
            for (int i = 0; i < getCycleOfRuns(); i++) {
                test();
            }
            long endTime = System.currentTimeMillis();
            totalRunTimeMs = endTime - beginTime;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

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

    public Long getTotalRunTimeMs() {
        return totalRunTimeMs;
    }

    public Long getAverageRunTimeMs() {
        return totalRunTimeMs / cycleOfRuns;
    }

}
