package cn.moonlord.socket.bio;

import cn.moonlord.log.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolServerSocket implements Runnable {

    private int port;
    private ExecutorService pool;

    public ThreadPoolServerSocket(int port, int workerThreadNum){
        this.port = port;
        this.pool = Executors.newFixedThreadPool(workerThreadNum);
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

                pool.execute(new SimpleHttpHandler(client));
                Logger.info("ServerSocket has got a client connection and started to handle it in a thread of the thread pool");
            }
        } catch (IOException e) {
            Logger.warn(e);
        }
    }

}
