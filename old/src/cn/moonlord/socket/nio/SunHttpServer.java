package cn.moonlord.socket.nio;

import cn.moonlord.log.Logger;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SunHttpServer {

    public SunHttpServer(int port, int maxConnectionNum) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), maxConnectionNum);
            server.createContext("/", new SunSimpleHttpHandler());
            server.start();
            Logger.info("HttpServer has started to listen at " + server.getAddress().getPort());
        } catch (IOException e) {
            Logger.warn(e);
        }
    }

}
