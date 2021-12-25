package cn.moonlord.socket.bio;

import cn.moonlord.log.Logger;

import java.io.IOException;
import java.net.Socket;

public class SimpleTcpProxyHandler implements Runnable {

    private Socket inputClient;
    private Socket outputClient;

    public SimpleTcpProxyHandler(Socket client) {
        this.inputClient = client;
    }

    @Override
    public void run() {
        try {
            Socket outputClient = new Socket(inputClient.getInetAddress().getHostName(), 80);
            Logger.info("outputClient " + outputClient.toString());
        } catch (Exception e) {
            Logger.warn(e);
        }
        finally {
            try {
                inputClient.close();
            } catch (IOException e) { }
        }
    }

}
