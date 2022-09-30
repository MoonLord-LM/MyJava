package cn.moonlord.security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * Provider
 */
public class Provider {

    public static final String BOUNCY_CASTLE_PROVIDER_NAME = BouncyCastleProvider.PROVIDER_NAME;

    public static final BouncyCastleProvider BOUNCY_CASTLE_PROVIDER = new BouncyCastleProvider();

    public static final String SUN_JCE_PROVIDER_NAME = "SunJCE";

    public static final java.security.Provider SUN_JCE_PROVIDER = Security.getProvider(SUN_JCE_PROVIDER_NAME);

    static {
        init();
    }

    public synchronized static void init() {
        addBouncyCastleProvider();
    }

    public synchronized static boolean hasBouncyCastleProvider() {
        return Security.getProvider(BOUNCY_CASTLE_PROVIDER_NAME) != null;
    }

    public synchronized static void addBouncyCastleProvider() {
        if (!hasBouncyCastleProvider()) {
            try {
                Security.addProvider(BOUNCY_CASTLE_PROVIDER);
            } catch (Exception e) {
                throw new IllegalArgumentException("Provider addBouncyCastleProvider error, error message: " + e.getMessage(), e);
            }
        }
    }

    public synchronized static void removeBouncyCastleProvider() {
        if (hasBouncyCastleProvider()) {
            try {
                Security.removeProvider(BOUNCY_CASTLE_PROVIDER_NAME);
            } catch (Exception e) {
                throw new IllegalArgumentException("Provider removeBouncyCastleProvider error, error message: " + e.getMessage(), e);
            }
        }
    }

    public synchronized static boolean hasSunJCEProvider() {
        return Security.getProvider(SUN_JCE_PROVIDER_NAME) != null;
    }

    public synchronized static void addSunJCEProvider() {
        if (!hasSunJCEProvider()) {
            try {
                Security.addProvider(SUN_JCE_PROVIDER);
            } catch (Exception e) {
                throw new IllegalArgumentException("Provider addSunJCEProvider error, error message: " + e.getMessage(), e);
            }
        }
    }

    public synchronized static void removeSunJCEProvider() {
        if (hasSunJCEProvider()) {
            try {
                Security.removeProvider(SUN_JCE_PROVIDER_NAME);
            } catch (Exception e) {
                throw new IllegalArgumentException("Provider removeSunJCEProvider error, error message: " + e.getMessage(), e);
            }
        }
    }

}
