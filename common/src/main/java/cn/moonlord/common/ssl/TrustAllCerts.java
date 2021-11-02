package cn.moonlord.common.ssl;

import cn.moonlord.security.Random;

import javax.net.ssl.*;
import java.net.URLConnection;
import java.security.cert.X509Certificate;

public class TrustAllCerts {

    public static final HostnameVerifier TRUST_ALL_HOSTNAME_VERIFIER = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    public static final TrustManager TRUST_ALL_TRUST_MANAGER = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };

    public static final TrustManager[] TRUST_ALL_TRUST_MANAGERS = new TrustManager[] { TRUST_ALL_TRUST_MANAGER };

    public static final SSLContext TRUST_ALL_SSL_CONTEXT;

    public static final SSLSocketFactory TRUST_ALL_SSL_SOCKET_FACTORY;

    static {
        try {
            TRUST_ALL_SSL_CONTEXT = SSLContext.getInstance("TLSv1.2");
            TRUST_ALL_SSL_CONTEXT.init(null, TRUST_ALL_TRUST_MANAGERS, Random.getInstance());
            TRUST_ALL_SSL_SOCKET_FACTORY = TRUST_ALL_SSL_CONTEXT.getSocketFactory();
        } catch (Exception e) {
            throw new IllegalArgumentException("TrustAllCerts init error", e);
        }
    }

    public static URLConnection setTrusted(URLConnection connection) {
        if(connection instanceof HttpsURLConnection) {
            ((HttpsURLConnection) connection).setHostnameVerifier(TRUST_ALL_HOSTNAME_VERIFIER);
            ((HttpsURLConnection) connection).setSSLSocketFactory(TRUST_ALL_SSL_SOCKET_FACTORY);
        }
        return connection;
    }

}
