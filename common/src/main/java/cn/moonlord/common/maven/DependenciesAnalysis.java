package cn.moonlord.common.maven;

import cn.moonlord.common.ssl.TrustAllCerts;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DependenciesAnalysis implements Runnable {

    private final String inputDependencyManagementFilePath;

    private final String outputDependencyFilePath;

    public DependenciesAnalysis(String inputDependencyManagementFilePath, String outputDependencyFilePath) {
        this.inputDependencyManagementFilePath = inputDependencyManagementFilePath;
        this.outputDependencyFilePath = outputDependencyFilePath;
        if(!Files.exists(Paths.get(inputDependencyManagementFilePath))){
            throw new IllegalArgumentException("inputDependencyManagementFilePath must be a valid File");
        }
        if(!Files.exists(Paths.get(outputDependencyFilePath))){
            throw new IllegalArgumentException("outputDependencyFilePath must be a valid File");
        }
        System.out.println("this.inputDependencyManagementFilePath: " + this.inputDependencyManagementFilePath);
        System.out.println("this.outputDependencyFilePath: " + this.outputDependencyFilePath);
    }

    public static class Dependency implements Serializable {

        public static final String BASE_RELEASE_URL = "https://mvnrepository.com/artifact/";

        public static final String BASE_DOWNLOAD_URL = "https://repo1.maven.org/maven2/";

        private String releaseUrl;

        private String downloadUrl;

        private String fileName;

        private Map<String, String> properties;

        private String groupId;

        private String artifactId;

        private String version;

        private String classifier;

        private String type;

        private String scope;

        @Override
        public String toString(){
            return "" +
                    "        <dependency>" + "\r\n" +
                    "            <groupId>" + groupId + "</groupId>" + "\r\n" +
                    "            <artifactId>" + artifactId + "</artifactId>" + "\r\n" +
                    ( classifier == null ? "" : (
                    "            <classifier>" + classifier + "</classifier>" + "\r\n"
                    )) +
                    ( type == null ? "" : (
                    "            <type>" + type + "</type>" + "\r\n"
                    )) +
                    "        </dependency>" + "\r\n";
        }

        public String getReleaseUrl() {
            return releaseUrl;
        }

        public void setReleaseUrl(String releaseUrl) {
            this.releaseUrl = releaseUrl;
        }

        public String getDownloadUrl() {
            if(downloadUrl == null && releaseUrl != null && getFileName() != null) {
                return releaseUrl.replace(".", "/").replace(BASE_RELEASE_URL.replace(".", "/"), BASE_DOWNLOAD_URL) + "/" + version + "/" + getFileName();
            }
            if(downloadUrl == null && groupId != null && getFileName() != null) {
                return BASE_RELEASE_URL + groupId.replace(".", "/") + "/" + version + "/" + getFileName();
            }
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getFileName() {
            if(fileName == null  && artifactId != null && version != null) {
                return artifactId  + "-" + version + ( classifier == null ? "" : "-" + classifier) + "." + ( type == null ? "jar" : type);
            }
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public Map<String, String> getProperties() {
            if(properties == null) {
                return new HashMap<>();
            }
            return properties;
        }

        public void setProperties(Map<String, String> properties) {
            this.properties = properties;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getArtifactId() {
            return artifactId;
        }

        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }

        public String getVersion() {
            if(version != null && version.startsWith("${") && version.endsWith("}")) {
                String key = StringUtils.substringBetween(version, "${", "}");
                String value = getProperties().get(key);
                if(value != null){
                    return value;
                }
                throw new RuntimeException("getVersion error, version: " + version);
            }
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getClassifier() {
            return classifier;
        }

        public void setClassifier(String classifier) {
            this.classifier = classifier;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    private static String getDependencyTagValue(String inputLine, String TagString) {
        if (inputLine == null || TagString == null) {
            return null;
        }
        inputLine = inputLine.trim();
        String tmp = null;
        if (inputLine.startsWith("<" + TagString + ">")) {
            tmp = inputLine.substring(inputLine.indexOf("<" + TagString + ">") + ("<" + TagString + ">").length());
            tmp = tmp.substring(0, tmp.indexOf("</" + TagString + ">"));
            System.out.println(TagString + ": " + tmp);
        }
        if (TagString.equals("releaseUrl")) {
            if(inputLine.startsWith("<!-- " + Dependency.BASE_RELEASE_URL)) {
                tmp = inputLine.substring(inputLine.indexOf("<!-- ") + ("<!-- ").length());
                tmp = tmp.substring(0, tmp.indexOf(" -->"));
                tmp = tmp.trim();
                System.out.println(TagString + ": " + tmp);
            }
        }
        return tmp;
    }

    private static void findDependencies(String inputDependencyManagementFilePath, List<String> downloadedDependenciesUrl, StringBuilder outputDependencies) {
        try {
            List<String> inputLines = FileUtils.readLines(new File(inputDependencyManagementFilePath), StandardCharsets.UTF_8);
            boolean isProperties = false;
            boolean isDependencyManagement = false;
            Dependency dependency = new Dependency();
            for (String inputLine : inputLines) {
                inputLine = inputLine.trim();
                {
                    if (inputLine.startsWith("<properties>")) {
                        isProperties = true;
                        continue;
                    }
                    if (inputLine.startsWith("</properties>")) {
                        isProperties = false;
                        continue;
                    }
                    if (inputLine.startsWith("<dependencyManagement>")) {
                        isDependencyManagement = true;
                        continue;
                    }
                    if (inputLine.startsWith("</dependencyManagement>")) {
                        isDependencyManagement = false;
                        continue;
                    }
                }
                if(isProperties) {
                    if(inputLine.startsWith("<!-- ")) {
                        continue;
                    }
                    if(inputLine.startsWith("<")) {
                        String key = inputLine.substring(inputLine.indexOf("<") + ("<").length());
                        key = key.substring(0, key.indexOf(">"));
                        String value = StringUtils.substringBetween(inputLine, "<" + key + ">", "</" + key + ">");
                        System.out.println("property "+ key + ": " + value);
                        dependency.getProperties().put(key, value);
                        continue;
                    }
                }
                if(isDependencyManagement) {
                    {
                        String releaseUrl = getDependencyTagValue(inputLine, "releaseUrl");
                        String groupId = getDependencyTagValue(inputLine, "groupId");
                        String artifactId = getDependencyTagValue(inputLine, "artifactId");
                        String version = getDependencyTagValue(inputLine, "version");
                        String classifier = getDependencyTagValue(inputLine, "classifier");
                        String type = getDependencyTagValue(inputLine, "type");
                        String scope = getDependencyTagValue(inputLine, "scope");
                        if (releaseUrl != null) dependency.setReleaseUrl(releaseUrl);
                        if (groupId != null) dependency.setGroupId(groupId);
                        if (artifactId != null) dependency.setArtifactId(artifactId);
                        if (version != null) dependency.setVersion(version);
                        if (classifier != null) dependency.setClassifier(classifier);
                        if (type != null) dependency.setType(type);
                        if (scope != null) dependency.setScope(scope);
                    }
                    if (inputLine.startsWith("</dependency>")) {
                        if (dependency.getScope() == null || dependency.getScope().equals("compile")) {
                            if(!outputDependencies.toString().contains(dependency.toString())) {
                                outputDependencies.append(dependency);
                                outputDependencies.append("\r\n");
                            }
                        }
                        if (dependency.getScope() != null && dependency.getScope().equals("import")) {
                            String downloadUrl = dependency.getDownloadUrl();
                            String fileName = dependency.getFileName();
                            System.out.println("downloadUrl: " + downloadUrl);
                            System.out.println("fileName: " + fileName);
                            if(!downloadedDependenciesUrl.contains(downloadUrl)) {
                                try {
                                    FileUtils.copyInputStreamToFile(TrustAllCerts.setTrusted(new URL(downloadUrl).openConnection()).getInputStream(), new File("target" + "/" + fileName));
                                    FileUtils.readLines(new File("target" + "/" + fileName), StandardCharsets.UTF_8);
                                    // recursion
                                    downloadedDependenciesUrl.add(downloadUrl);
                                    findDependencies("target" + "/" + fileName, downloadedDependenciesUrl, outputDependencies);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        dependency = new Dependency();
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        List<String> downloadedDependenciesUrl = new ArrayList<>();
        StringBuilder outputDependencies = new StringBuilder("    <dependencies>" + "\r\n" + "\r\n");
        findDependencies(inputDependencyManagementFilePath, downloadedDependenciesUrl, outputDependencies);
        outputDependencies.append("    </dependencies>");
        System.out.println("outputDependencies: " + outputDependencies);
        try {
            List<String> outputLines = new ArrayList<>();
            List<String> tempLines = FileUtils.readLines(new File(outputDependencyFilePath), StandardCharsets.UTF_8);
            boolean isDependency = false;
            for (String line : tempLines) {
                if (line.trim().startsWith("<dependencies>")) {
                    isDependency = true;
                    outputLines.add(outputDependencies.toString());
                }
                if (line.trim().startsWith("</dependencies>")) {
                    isDependency = false;
                    continue;
                }
                if (isDependency) {
                    continue;
                }
                outputLines.add(line);
            }
            FileUtils.writeLines(new File(outputDependencyFilePath), StandardCharsets.UTF_8.name(), outputLines, "\r\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
