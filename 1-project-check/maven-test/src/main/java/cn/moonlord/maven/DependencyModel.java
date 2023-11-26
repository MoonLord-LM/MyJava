package cn.moonlord.maven;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class DependencyModel implements Serializable {

    public static final String BASE_RELEASE_URL = "https://mvnrepository.com/artifact/";

    public static final String BASE_DOWNLOAD_URL = "https://repo1.maven.org/maven2/";

    private String releaseUrl;

    private String downloadUrl;

    private String fileName;

    private Map<String, String> properties = new HashMap<>();

    private String groupId;

    private String artifactId;

    private String version;

    private String classifier;

    private String type;

    private String scope;

    @Override
    public String toString() {
        return "" +
                "        <dependency>" + "\r\n" +
                "            <groupId>" + groupId + "</groupId>" + "\r\n" +
                "            <artifactId>" + artifactId + "</artifactId>" + "\r\n" +
                (classifier == null ? "" : (
                        "            <classifier>" + classifier + "</classifier>" + "\r\n"
                )) +
                (type == null ? "" : (
                        "            <type>" + type + "</type>" + "\r\n"
                )) +
                (version == null ? "" : (
                        "            <version>" + version + "</version>" + "\r\n"
                )) +
                "        </dependency>" + "\r\n";
    }

    public String getDownloadUrl() {
        if (downloadUrl == null && releaseUrl != null && getFileName() != null) {
            return releaseUrl.replace(".", "/").replace(BASE_RELEASE_URL.replace(".", "/"), BASE_DOWNLOAD_URL) + "/" + getVersion() + "/" + getFileName();
        }
        if (downloadUrl == null && groupId != null && getFileName() != null) {
            return BASE_RELEASE_URL + groupId.replace(".", "/") + "/" + getVersion() + "/" + getFileName();
        }
        return downloadUrl;
    }

    public String getFileName() {
        if (fileName == null && artifactId != null && getVersion() != null) {
            return artifactId + "-" + getVersion() + (classifier == null ? "" : "-" + classifier) + "." + (type == null ? "jar" : type);
        }
        return fileName;
    }

    public String getVersion() {
        if (version != null && version.startsWith("${") && version.endsWith("}")) {
            String key = StringUtils.substringBetween(version, "${", "}");
            return getProperties().get(key);
        }
        return version;
    }

}
