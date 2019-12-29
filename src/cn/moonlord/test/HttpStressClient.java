package cn.moonlord.test;

import cn.moonlord.log.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpStressClient implements Runnable {

    private ExecutorService pool;
    private String url;
    private int totalTestNum;
    private AtomicInteger finishedTestNum;

    public HttpStressClient(int workerThreadNum, String url, int totalTestNum){
        this.pool = Executors.newFixedThreadPool(workerThreadNum);
        this.url = url;
        this.totalTestNum = totalTestNum;
        this.finishedTestNum = new AtomicInteger(0);
        (new Thread(this)).start();
    }

    @Override
    public void run() {
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < totalTestNum; i++) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
                        int responseCode = connection.getResponseCode();
                        connection.getContent();
                        connection.disconnect();
                        int finished = finishedTestNum.incrementAndGet();
                        Logger.info("StressTest [ " + finished + " / " + totalTestNum + "] has got response code " + responseCode);
                        if(finished == totalTestNum){
                            finishedTestNum.incrementAndGet();
                            long endTime = System.currentTimeMillis();
                            Logger.warn("StressTest [ " + totalTestNum + "] is finished, total time is " + (endTime - beginTime) + " ms, average time is " + ((float)(endTime - beginTime) / totalTestNum) + " ms");
                            pool.shutdown();
                        }
                    } catch (IOException e) {
                        Logger.warn(e);
                    }
                }
            });
        }
    }

}
