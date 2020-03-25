package cn.moonlord;

import cn.moonlord.task.TaskManager;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World");

        //HttpStressTest.test1();
        //HttpStressTest.test2();
        //M3U8VideoDownloaderTest.test1();

        new TaskManager("out/task.config").runDailyCycle();
    }

}
