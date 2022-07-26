package cn.moonlord.common.string;

import java.io.File;
import java.net.URI;
import java.text.Normalizer;

public class Normalize {

    public static String getString(String source) {
        if (source == null) {
            throw new IllegalArgumentException("Normalize getString error, source must not be null");
        }
        return Normalizer.normalize(source, Normalizer.Form.NFKC);
    }

    public static String getUriString(URI uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Normalize getUriString error, uri must not be null");
        }
        return uri.normalize().toString();
    }

    public static String getUriString(String uriString) {
        if (uriString == null) {
            throw new IllegalArgumentException("Normalize getUriString error, uriString must not be null");
        }
        try {
            return getUriString(new URI(uriString));
        } catch (Exception e) {
            throw new IllegalArgumentException("Normalize getUriString error, error message: " + e.getMessage(), e);
        }
    }

    public static String getFilePath(File file) {
        if (file == null) {
            throw new IllegalArgumentException("Normalize getFilePath error, file must not be null");
        }
        try {
            return file.getCanonicalPath();
        } catch (Exception e) {
            throw new IllegalArgumentException("Normalize getFilePath error, error message: " + e.getMessage(), e);
        }
    }

    public static String getFilePath(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("Normalize getFilePath error, filePath must not be null");
        }
        File file = new File(filePath);
        return getFilePath(file);
    }

}
