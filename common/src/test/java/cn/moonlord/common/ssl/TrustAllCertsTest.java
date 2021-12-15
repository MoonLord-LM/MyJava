package cn.moonlord.common.ssl;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
@RunWith(Enclosed.class)
public class TrustAllCertsTest {

    public static Logger logger = LoggerFactory.getLogger(TrustAllCertsTest.class);

    public static class run {
        @Test
        public void success_1() throws NoSuchAlgorithmException {
            TrustAllCerts.setTrusted(null);
            logger.info("SSLSocketFactory: {}", SSLContext.getDefault().getServerSocketFactory().getDefaultCipherSuites());
            logger.info("SSLSocketFactory: {}", SSLContext.getDefault().getServerSocketFactory().getSupportedCipherSuites());
            logger.info("SSLSocketFactory: {}", TrustAllCerts.TRUST_ALL_SSL_SOCKET_FACTORY);
            logger.info("SSLContext: {}", TrustAllCerts.TRUST_ALL_SSL_CONTEXT);
            logger.info("SSLContext.getProtocol() : {}", TrustAllCerts.TRUST_ALL_SSL_CONTEXT.getProtocol());
            logger.info("SSLContext.getSupportedSSLParameters().getProtocol() : {}", TrustAllCerts.TRUST_ALL_SSL_CONTEXT.getSupportedSSLParameters().getProtocols());
        }
    }

}
