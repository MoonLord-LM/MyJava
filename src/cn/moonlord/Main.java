package cn.moonlord;

import cn.moonlord.socket.bio.SimpleTcpProxyHandler;
import cn.moonlord.socket.bio.UnlimitedThreadServerSocket;
import cn.moonlord.task.TaskManager;
import cn.moonlord.test.HttpProxyTest;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World");

        //HttpStressTest.test1();
        //HttpStressTest.test2();
        //M3U8VideoDownloaderTest.test1();

        new TaskManager("out/task.config").runDailyCycle();

        new UnlimitedThreadServerSocket(80, SimpleTcpProxyHandler.class);
        HttpProxyTest.test1();
    }

}
