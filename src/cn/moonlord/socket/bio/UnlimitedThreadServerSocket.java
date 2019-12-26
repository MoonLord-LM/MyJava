package cn.moonlord.socket.bio;

import cn.moonlord.log.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class UnlimitedThreadServerSocket implements Runnable {

    private int port;

    public UnlimitedThreadServerSocket(int port){
        this.port = port;
        (new Thread(this)).start();
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);
            Logger.info("ServerSocket has started to listen at " + server.getLocalSocketAddress());

            while(!Thread.interrupted()) {
                Logger.info("ServerSocket is waiting for a new client connection ...");
                Socket client = server.accept();

                new Thread(new SimpleHttpHandler(client)).start();
                Logger.info("ServerSocket has got a client connection and started a new thread to handle it");
            }
        } catch (IOException e) {
            Logger.warn(e);
        }
    }

}
