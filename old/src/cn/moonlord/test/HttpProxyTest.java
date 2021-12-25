package cn.moonlord.test;

import cn.moonlord.http.HttpProxyClient;

public class HttpProxyTest {

    public static void test1() {
        new HttpProxyClient("http://www.baidu.com", "127.0.0.1", 80);
    }

}
