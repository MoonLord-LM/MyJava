package cn.moonlord.security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * Provider
 */
public class Provider {

    public static final String BOUNCY_CASTLE_PROVIDER_NAME = "BC";

    public static final BouncyCastleProvider BOUNCY_CASTLE_PROVIDER = new BouncyCastleProvider();

    public static final String SUN_PROVIDER_NAME = "SUN";

    public static final java.security.Provider SUN_PROVIDER = Security.getProvider(SUN_PROVIDER_NAME);

    public static final String SUN_JCE_PROVIDER_NAME = "SunJCE";

    public static final java.security.Provider SUN_JCE_PROVIDER = Security.getProvider(SUN_JCE_PROVIDER_NAME);

    static {
        init();
    }

    public synchronized static void init() {
        addBouncyCastleProvider();
    }

    public synchronized static boolean hasProvider(String providerName) {
        return Security.getProvider(providerName) != null;
    }

    public synchronized static boolean hasProvider(java.security.Provider provider) {
        return Security.getProvider(provider.getName()) != null;
    }

    public synchronized static boolean hasBouncyCastleProvider() {
        return hasProvider(BOUNCY_CASTLE_PROVIDER_NAME);
    }

    public synchronized static boolean hasSunProvider() {
        return hasProvider(SUN_PROVIDER_NAME);
    }

    public synchronized static boolean hasSunJCEProvider() {
        return hasProvider(SUN_JCE_PROVIDER_NAME);
    }

    public synchronized static void addProvider(java.security.Provider provider) {
        try {
            Security.addProvider(provider);
        } catch (Exception e) {
            throw new IllegalArgumentException("Provider addBouncyCastleProvider error, error message: " + e.getMessage(), e);
        }
    }

    public synchronized static void addBouncyCastleProvider() {
        addProvider(BOUNCY_CASTLE_PROVIDER);
    }

    public synchronized static void addSunProvider() {
        addProvider(SUN_PROVIDER);
    }

    public synchronized static void addSunJCEProvider() {
        addProvider(SUN_JCE_PROVIDER);
    }

    public synchronized static void removeProvider(String providerName) {
        try {
            Security.removeProvider(providerName);
        } catch (Exception e) {
            throw new IllegalArgumentException("Provider removeBouncyCastleProvider error, error message: " + e.getMessage(), e);
        }
    }

    public synchronized static void removeBouncyCastleProvider() {
        removeProvider(BOUNCY_CASTLE_PROVIDER_NAME);
    }

    public synchronized static void removeSunProvider() {
        removeProvider(SUN_PROVIDER_NAME);
    }

    public synchronized static void removeSunJCEProvider() {
        removeProvider(SUN_JCE_PROVIDER_NAME);
    }

}
