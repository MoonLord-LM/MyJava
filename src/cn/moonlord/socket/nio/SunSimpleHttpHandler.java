package cn.moonlord.socket.nio;

import cn.moonlord.log.Logger;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class SunSimpleHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String requestProtocol = httpExchange.getProtocol();
        String requestMethod = httpExchange.getRequestMethod();
        String requestURL = httpExchange.getRequestURI().toASCIIString();
        String contentBody = requestProtocol + " " + requestMethod + " request for " + requestURL + " is simply handled by " + this;
        try {
            httpExchange.getResponseHeaders().set("Content-Type", "text/plain");
            httpExchange.sendResponseHeaders(200, contentBody.getBytes().length);
            httpExchange.getResponseBody().write(contentBody.getBytes());
            httpExchange.getResponseBody().flush();;
            httpExchange.getResponseBody().close();
            Logger.info(contentBody);
        } catch (IOException e) {
            Logger.warn(e);
        }
    }

}
