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

    public static final String INDENT = "        ";

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

    /**
     * <!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
     * <dependency>
     * <groupId>javax.servlet</groupId>
     * <artifactId>javax.servlet-api</artifactId>
     * <version>4.0.1</version>
     * <scope>provided</scope>
     * </dependency>
     * <!-- https://repo1.maven.org/maven2/javax/servlet/javax.servlet-api/4.0.1/javax.servlet-api-4.0.1.jar -->
     */
    public String getDownloadUrl() {
        if (downloadUrl == null && groupId != null && artifactId != null && getVersion() != null && getFileName() != null) {
            return BASE_DOWNLOAD_URL + groupId.replace(".", "/") + "/" + artifactId + "/" + getVersion() + "/" + getFileName();
        }
        if (downloadUrl == null && releaseUrl != null && getVersion() != null && getFileName() != null) {
            return BASE_DOWNLOAD_URL + releaseUrl.replace(BASE_RELEASE_URL, "") + "/" + getVersion() + "/" + getFileName();
        }
        return downloadUrl;
    }

    public String getVersion() {
        if (version != null && version.startsWith("${") && version.endsWith("}")) {
            String key = StringUtils.substringBetween(version, "${", "}");
            return getProperties().get(key);
        }
        return version;
    }

    public String getFileName() {
        if (fileName == null && artifactId != null && getVersion() != null) {
            return artifactId + "-" + getVersion() + (classifier == null ? "" : "-" + classifier) + "." + (type == null ? "jar" : type);
        }
        return fileName;
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append(INDENT).append("<dependency>").append("\r\n");
        tmp.append(INDENT).append(INDENT).append("<groupId>").append(groupId).append("</groupId>").append("\r\n");
        tmp.append(INDENT).append(INDENT).append("<artifactId>").append(artifactId).append("</artifactId>").append("\r\n");
        if (classifier != null) {
            tmp.append(INDENT).append(INDENT).append("<classifier>").append(classifier).append("</classifier>").append("\r\n");
        }
        if (type != null) {
            tmp.append(INDENT).append(INDENT).append("<type>").append(type).append("</type>").append("\r\n");
        }
        if (version != null) {
            tmp.append(INDENT).append(INDENT).append("<version>").append(version).append("</version>").append("\r\n");
        }
        tmp.append(INDENT).append("</dependency>").append("\r\n");
        return tmp.toString();
    }

}
