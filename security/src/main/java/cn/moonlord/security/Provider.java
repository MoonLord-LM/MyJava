package cn.moonlord.security;

import jdk.nashorn.internal.objects.NativeJSON;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;
import java.util.Enumeration;
import java.util.Locale;

public class Provider {

    static {
        init();
    }

    public synchronized static void init() {
        java.security.Provider[] providers = Security.getProviders();
        for (java.security.Provider provider : providers) {
            if (provider instanceof BouncyCastleProvider) {
                return;
            }
        }
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String showAllProviders() {
        StringBuilder result = new StringBuilder();
        java.security.Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            java.security.Provider provider = providers[i];
            result.append("Provider [ " + i + " ]  [ " + provider.getName() + " ] " + " [ version " + provider.getVersion() + " ]  [ size " + provider.size() + " ] " + provider.getInfo());
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    public static String showAllElements() {
        StringBuilder result = new StringBuilder();
        java.security.Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            java.security.Provider provider = providers[i];
            for (Enumeration<Object> e = provider.keys(); e.hasMoreElements(); ) {
                String key = (String) e.nextElement();
                result.append("Provider [ " + i + " ]  [ " + provider.getName() + " ] ");
                result.append(key + "  -  " + provider.get(key));
                result.append(System.lineSeparator());
            }
        }
        return result.toString();
    }

    public static String showMessageDigests() {
        StringBuilder result = new StringBuilder();
        java.security.Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            java.security.Provider provider = providers[i];
            for (Enumeration<Object> e = provider.keys(); e.hasMoreElements(); ) {
                String key = (String) e.nextElement();
                if (key.toUpperCase(Locale.ROOT).startsWith("MessageDigest.".toUpperCase(Locale.ROOT))) {
                    result.append("Provider [ " + i + " ]  [ " + provider.getName() + " ] ");
                    result.append(key.substring("MessageDigest.".length()) + "  -  " + provider.get(key));
                    result.append(System.lineSeparator());
                }
            }
        }
        return result.toString();
    }

    public static String showSignatures() {
        StringBuilder result = new StringBuilder();
        java.security.Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            java.security.Provider provider = providers[i];
            for (Enumeration<Object> e = provider.keys(); e.hasMoreElements(); ) {
                String key = (String) e.nextElement();
                if (key.toUpperCase(Locale.ROOT).startsWith("Signature.".toUpperCase(Locale.ROOT))) {
                    result.append("Provider [ " + i + " ]  [ " + provider.getName() + " ] ");
                    result.append(key.substring("Signature.".length()) + "  -  " + provider.get(key));
                    result.append(System.lineSeparator());
                }
            }
        }
        return result.toString();
    }

    public static String showSecretKeyFactorys() {
        StringBuilder result = new StringBuilder();
        java.security.Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            java.security.Provider provider = providers[i];
            for (Enumeration<Object> e = provider.keys(); e.hasMoreElements(); ) {
                String key = (String) e.nextElement();
                if (key.toUpperCase(Locale.ROOT).startsWith("SecretKeyFactory.".toUpperCase(Locale.ROOT))) {
                    result.append("Provider [ " + i + " ]  [ " + provider.getName() + " ] ");
                    result.append(key.substring("SecretKeyFactory.".length()) + "  -  " + provider.get(key));
                    result.append(System.lineSeparator());
                }
            }
        }
        return result.toString();
    }

    public static String showPBKDF2s() {
        StringBuilder result = new StringBuilder();
        java.security.Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            java.security.Provider provider = providers[i];
            for (Enumeration<Object> e = provider.keys(); e.hasMoreElements(); ) {
                String key = (String) e.nextElement();
                if (key.toUpperCase(Locale.ROOT).startsWith("SecretKeyFactory.PBKDF2".toUpperCase(Locale.ROOT))) {
                    result.append("Provider [ " + i + " ]  [ " + provider.getName() + " ] ");
                    result.append(key.substring("SecretKeyFactory.PBKDF2".length()) + "  -  " + provider.get(key));
                    result.append(System.lineSeparator());
                }
            }
        }
        return result.toString();
    }

    public static String showSecureRandoms() {
        StringBuilder result = new StringBuilder();
        java.security.Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            java.security.Provider provider = providers[i];
            for (Enumeration<Object> e = provider.keys(); e.hasMoreElements(); ) {
                String key = (String) e.nextElement();
                if (key.toUpperCase(Locale.ROOT).contains("SecureRandom.".toUpperCase(Locale.ROOT))) {
                    result.append("Provider [ " + i + " ]  [ " + provider.getName() + " ] ");
                    result.append(key.substring("SecureRandom.".length()) + "  -  " + provider.get(key));
                    result.append(System.lineSeparator());
                }
            }
        }
        return result.toString();
    }

    public static String showCiphers() {
        StringBuilder result = new StringBuilder();
        java.security.Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            java.security.Provider provider = providers[i];
            for (Enumeration<Object> e = provider.keys(); e.hasMoreElements(); ) {
                String key = (String) e.nextElement();
                if (key.toUpperCase(Locale.ROOT).startsWith("Cipher.".toUpperCase(Locale.ROOT))) {
                    result.append("Provider [ " + i + " ]  [ " + provider.getName() + " ] ");
                    result.append(key.substring("Cipher.".length()) + "  -  " + provider.get(key));
                    result.append(System.lineSeparator());
                }
            }
        }
        return result.toString();
    }

    public static String showAesCiphers() {
        StringBuilder result = new StringBuilder();
        java.security.Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            java.security.Provider provider = providers[i];
            for (Enumeration<Object> e = provider.keys(); e.hasMoreElements(); ) {
                String key = (String) e.nextElement();
                if (key.toUpperCase(Locale.ROOT).startsWith("Cipher.".toUpperCase(Locale.ROOT))) {
                    if (key.toUpperCase(Locale.ROOT).contains("AES".toUpperCase(Locale.ROOT))) {
                        if (!key.toUpperCase(Locale.ROOT).contains("With".toUpperCase(Locale.ROOT))) {
                            result.append("Provider [ " + i + " ]  [ " + provider.getName() + " ] ");
                            result.append(key.substring("Cipher.".length()) + "  -  " + provider.get(key));
                            result.append(System.lineSeparator());
                        }
                    }
                }
            }
        }
        return result.toString();
    }

    public static String showChaChaCiphers() {
        StringBuilder result = new StringBuilder();
        java.security.Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            java.security.Provider provider = providers[i];
            for (Enumeration<Object> e = provider.keys(); e.hasMoreElements(); ) {
                String key = (String) e.nextElement();
                if (key.toUpperCase(Locale.ROOT).startsWith("Cipher.".toUpperCase(Locale.ROOT))) {
                    if (key.toUpperCase(Locale.ROOT).contains("ChaCha".toUpperCase(Locale.ROOT))) {
                        result.append("Provider [ " + i + " ]  [ " + provider.getName() + " ] ");
                        result.append(key.substring("Cipher.".length()) + "  -  " + provider.get(key));
                        result.append(System.lineSeparator());
                    }
                }
            }
        }
        return result.toString();
    }

}
