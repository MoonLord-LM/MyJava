package cn.moonlord.test;

import cn.moonlord.socket.bio.SingleThreadServerSocket;
import cn.moonlord.socket.bio.ThreadPoolServerSocket;
import cn.moonlord.socket.bio.UnlimitedThreadServerSocket;
import cn.moonlord.socket.nio.SunHttpServer;

public class HttpStressTest {

    public static void test1() throws InterruptedException {
        new SingleThreadServerSocket(80);
        new ThreadPoolServerSocket(81,1);
        new ThreadPoolServerSocket(82,4);
        new ThreadPoolServerSocket(83,16);
        new ThreadPoolServerSocket(84,64);
        new UnlimitedThreadServerSocket(85);

        new HttpStressClient(64,"http://127.0.0.1:80", 10000);
        Thread.sleep(20000);
        new HttpStressClient(64,"http://127.0.0.1:81", 10000);
        Thread.sleep(20000);
        new HttpStressClient(64,"http://127.0.0.1:82", 10000);
        Thread.sleep(20000);
        new HttpStressClient(64,"http://127.0.0.1:83", 10000);
        Thread.sleep(20000);
        new HttpStressClient(64,"http://127.0.0.1:84", 10000);
        Thread.sleep(20000);
        new HttpStressClient(64,"http://127.0.0.1:85", 10000);
        Thread.sleep(20000);
    }

    public static void test2() throws InterruptedException {
        new SunHttpServer(90,64);

        new HttpStressClient(64,"http://127.0.0.1:90", 10000);
        Thread.sleep(20000);
    }

}
