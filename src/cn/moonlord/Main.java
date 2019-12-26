package cn.moonlord;

import cn.moonlord.test.HttpStressTest;
import cn.moonlord.socket.bio.UnlimitedThreadServerSocket;
import cn.moonlord.socket.bio.SingleThreadServerSocket;
import cn.moonlord.socket.bio.ThreadPoolServerSocket;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello World");

        new SingleThreadServerSocket(80);
        new ThreadPoolServerSocket(81,1);
        new ThreadPoolServerSocket(82,4);
        new ThreadPoolServerSocket(83,16);
        new ThreadPoolServerSocket(84,64);
        new UnlimitedThreadServerSocket(85);

        new HttpStressTest(64,"http://127.0.0.1:80", 10000);
        Thread.sleep(20000);
        new HttpStressTest(64,"http://127.0.0.1:81", 10000);
        Thread.sleep(20000);
        new HttpStressTest(64,"http://127.0.0.1:82", 10000);
        Thread.sleep(20000);
        new HttpStressTest(64,"http://127.0.0.1:83", 10000);
        Thread.sleep(20000);
        new HttpStressTest(64,"http://127.0.0.1:84", 10000);
        Thread.sleep(20000);
        new HttpStressTest(64,"http://127.0.0.1:85", 10000);
    }

}
