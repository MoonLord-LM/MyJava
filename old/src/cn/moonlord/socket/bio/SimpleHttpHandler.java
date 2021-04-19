package cn.moonlord.socket.bio;

import cn.moonlord.log.Logger;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleHttpHandler implements Runnable {

    private Socket client;

    public SimpleHttpHandler(Socket client){
        this.client = client;
    }

    @Override
    public void run() {
        try {
            Logger.info("[ Input Begin ]");
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line =  in.readLine();
            Logger.info("    " + line);
            String[] requestLine = line.split(" ");
            if(requestLine.length != 3){
                Logger.info("[ Input Error ]");
                client.getOutputStream().write(("HTTP/1.1 400 Bad Request" + "\r\n\r\n").getBytes());
                client.getOutputStream().close();
                return;
            }
            String requestMethod = requestLine[0];
            String requestURL = requestLine[1];
            String requestProtocol= requestLine[2];
            while (line != null && line.length() > 0) {
                line = in.readLine();
                Logger.info("    " + line);
            }
            Logger.info("[ Input End ]");

            Logger.info("[ Output Begin ]");
            PrintWriter out = new PrintWriter(new BufferedOutputStream(client.getOutputStream()));
            String statusLine = "HTTP/1.1 200 OK";
            String contentBody = requestProtocol + " " + requestMethod + " request for " + requestURL + " is simply handled by " + this;
            String typeLine = "Content-Type: text/plain";
            String lengthLine = "Content-Length: " + contentBody.getBytes().length;
            out.println(statusLine);
            Logger.info("    " + statusLine);
            out.println(typeLine);
            Logger.info("    " + typeLine);
            out.println(lengthLine);
            Logger.info("    " + lengthLine);
            out.println();
            Logger.info("    ");
            out.print(contentBody);
            Logger.info("    " + contentBody);
            out.close();
            Logger.info("[ Output End ]");
        } catch (Exception e) {
            Logger.warn(e);
        }
        finally {
            try {
                client.close();
            } catch (IOException e) { }
        }
    }

}
