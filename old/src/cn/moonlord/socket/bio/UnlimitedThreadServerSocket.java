package cn.moonlord.socket.bio;

import cn.moonlord.log.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class UnlimitedThreadServerSocket implements Runnable {

    private int port;
    private Class<Runnable> handler;

    public UnlimitedThreadServerSocket(int port, Class<?> handler) {
        this.port = port;
        this.handler = (Class<Runnable>) handler;
        (new Thread(this)).start();
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);
            Logger.info("ServerSocket has started to listen at " + server.getLocalSocketAddress());

            while (!Thread.interrupted()) {
                Logger.info("ServerSocket is waiting for a new client connection ...");
                Socket client = server.accept();

                new Thread(handler.getConstructor(Socket.class).newInstance(client)).start();
                Logger.info("ServerSocket has got a client connection and started a new thread to handle it");
            }
        } catch (Exception e) {
            Logger.warn(e);
        }
    }

}
