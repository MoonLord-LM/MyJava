package cn.moonlord.security;

import com.sun.crypto.provider.SunJCE;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * Provider
 */
public class Provider {

    public static final BouncyCastleProvider BOUNCY_CASTLE_PROVIDER = new BouncyCastleProvider();

    public static final String BOUNCY_CASTLE_PROVIDER_NAME = BouncyCastleProvider.PROVIDER_NAME;

    public static final SunJCE SUN_JCE_PROVIDER = new SunJCE();

    public static final String SUN_JCE_PROVIDER_NAME = SunJCE.class.getSimpleName();

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
                throw new IllegalArgumentException("Provider addBouncyCastleProvider error, error message: " + e.getMessage(), e);
            }
        }
    }

    public synchronized static void removeSunJCEProvider() {
        if (hasSunJCEProvider()) {
            try {
                Security.removeProvider(SUN_JCE_PROVIDER_NAME);
            } catch (Exception e) {
                throw new IllegalArgumentException("Provider removeBouncyCastleProvider error, error message: " + e.getMessage(), e);
            }
        }
    }

}
