package cn.moonlord;

import cn.moonlord.test.HttpStressTest;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello World");

        HttpStressTest.test1();
        HttpStressTest.test2();
    }

}
