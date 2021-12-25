package cn.moonlord.http;

import cn.moonlord.log.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class HttpProxyClient implements Runnable {

    private String url;
    private String proxyHost;
    private int proxyPort;

    public HttpProxyClient(String url, String proxyHost, int proxyPort) {
        this.url = url;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        (new Thread(this)).start();
    }

    @Override
    public void run() {
        try {
            InetSocketAddress proxyAddress = new InetSocketAddress(proxyHost, proxyPort);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddress);
            HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection(proxy);
            int responseCode = connection.getResponseCode();
            // TODO
            connection.getContent();
            Logger.info("ProxyClient has got response code " + responseCode);
            connection.disconnect();
        } catch (IOException e) {
            Logger.warn(e);
        }
    }

}
