package cn.moonlord.security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;
import java.util.Enumeration;
import java.util.Locale;

public class Provider {

    static {
        init();
    }

    public synchronized static void init(){
        java.security.Provider[] providers = Security.getProviders();
        for (java.security.Provider provider: providers) {
            if(provider instanceof BouncyCastleProvider) {
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
            result.append("Provider" + " [ " + i + " ] " + " [ " + provider.getName() + " ] " + provider.getInfo());
            result.append("\r\n");
        }
        return result.toString();
    }

    public static String showAllMessageDigests() {
        StringBuilder result = new StringBuilder();
        java.security.Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            java.security.Provider provider = providers[i];
            for (Enumeration<Object> e = provider.keys(); e.hasMoreElements();) {
                String key = (String) e.nextElement();
                if (key.toUpperCase(Locale.ROOT).startsWith("MessageDigest.".toUpperCase(Locale.ROOT))) {
                    result.append("Provider" + " [ " + i + " ] " + " [ " + provider.getName() + " ] ");
                    result.append(key.substring("MessageDigest.".length()) + "  -  " + provider.get(key));
                    result.append("\r\n");
                }
            }
        }
        return result.toString();
    }

    public static String showAllSignatures() {
        StringBuilder result = new StringBuilder();
        java.security.Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            java.security.Provider provider = providers[i];
            for (Enumeration<Object> e = provider.keys(); e.hasMoreElements();) {
                String key = (String) e.nextElement();
                if (key.toUpperCase(Locale.ROOT).startsWith("Signature.".toUpperCase(Locale.ROOT))) {
                    result.append("Provider" + " [ " + i + " ] " + " [ " + provider.getName() + " ] ");
                    result.append(key.substring("Signature.".length()) + "  -  " + provider.get(key));
                    result.append("\r\n");
                }
            }
        }
        return result.toString();
    }

    public static String showAllCiphers() {
        StringBuilder result = new StringBuilder();
        java.security.Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            java.security.Provider provider = providers[i];
            for (Enumeration<Object> e = provider.keys(); e.hasMoreElements();) {
                String key = (String) e.nextElement();
                if (key.toUpperCase(Locale.ROOT).startsWith("Cipher.".toUpperCase(Locale.ROOT))) {
                    result.append("Provider" + " [ " + i + " ] " + " [ " + provider.getName() + " ] ");
                    result.append(key.substring("Cipher.".length()) + "  -  " + provider.get(key));
                    result.append("\r\n");
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
            for (Enumeration<Object> e = provider.keys(); e.hasMoreElements();) {
                String key = (String) e.nextElement();
                if (key.toUpperCase(Locale.ROOT).startsWith("Cipher.".toUpperCase(Locale.ROOT))) {
                    if (key.toUpperCase(Locale.ROOT).contains("AES".toUpperCase(Locale.ROOT))) {
                        if (!key.toUpperCase(Locale.ROOT).contains("With".toUpperCase(Locale.ROOT))) {
                            result.append("Provider" + " [ " + i + " ] " + " [ " + provider.getName() + " ] ");
                            result.append(key.substring("Cipher.".length()) + "  -  " + provider.get(key));
                            result.append("\r\n");
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
            for (Enumeration<Object> e = provider.keys(); e.hasMoreElements();) {
                String key = (String) e.nextElement();
                if (key.toUpperCase(Locale.ROOT).startsWith("Cipher.".toUpperCase(Locale.ROOT))) {
                    if (key.toUpperCase(Locale.ROOT).contains("AES".toUpperCase(Locale.ROOT))) {
                        if (!key.toUpperCase(Locale.ROOT).contains("With".toUpperCase(Locale.ROOT))) {
                            result.append("Provider" + " [ " + i + " ] " + " [ " + provider.getName() + " ] ");
                            result.append(key.substring("Cipher.".length()) + "  -  " + provider.get(key));
                            result.append("\r\n");
                        }
                    }
                }
            }
        }
        return result.toString();
    }

}
