package cn.moonlord.common.https;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpsTest {

    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("java.home"));
        System.setProperty("javax.net.ssl.trustStore","C:\\Users\\cacerts");
        System.setProperty("javax.net.debug", "all");
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
        URLConnection conn = new URL("https://").openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response);
    }

}
