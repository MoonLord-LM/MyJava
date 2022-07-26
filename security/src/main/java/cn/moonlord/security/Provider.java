package cn.moonlord.security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class Provider {

    static {
        init();
    }

    public synchronized static void init() {
        addBouncyCastleProvider();
    }

    public synchronized static boolean hasBouncyCastleProvider() {
        return Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) != null;
    }

    public synchronized static void addBouncyCastleProvider() {
        if (!hasBouncyCastleProvider()) {
            try {
                Security.addProvider(new BouncyCastleProvider());
            } catch (Exception e) {
                throw new IllegalArgumentException("Provider addBouncyCastleProvider error, error message: " + e.getMessage(), e);
            }
        }
    }

    public synchronized static void removeBouncyCastleProvider() {
        if (hasBouncyCastleProvider()) {
            try {
                Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
            } catch (Exception e) {
                throw new IllegalArgumentException("Provider removeBouncyCastleProvider error, error message: " + e.getMessage(), e);
            }
        }
    }

}
