package cn.moonlord.maven;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DependenciesAnalysis implements Runnable {

    private final String inputDependencyManagementFilePath;

    private final String outputDependencyFilePath;

    public DependenciesAnalysis(String inputDependencyManagementFilePath, String outputDependencyFilePath) {
        this.inputDependencyManagementFilePath = inputDependencyManagementFilePath;
        this.outputDependencyFilePath = outputDependencyFilePath;
        if (!Files.exists(Paths.get(inputDependencyManagementFilePath))) {
            throw new IllegalArgumentException("inputDependencyManagementFilePath must be a valid File");
        }
        if (!Files.exists(Paths.get(outputDependencyFilePath))) {
            throw new IllegalArgumentException("outputDependencyFilePath must be a valid File");
        }
        System.out.println("this.inputDependencyManagementFilePath: " + this.inputDependencyManagementFilePath);
        System.out.println("this.outputDependencyFilePath: " + this.outputDependencyFilePath);
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


    private static String getDependencyTagValue(String inputLine, String TagString) {
        if (inputLine == null || TagString == null) {
            return null;
        }
        inputLine = inputLine.trim();
        String value = null;
        if (inputLine.startsWith("<" + TagString + ">")) {
            value = StringUtils.substringBetween(inputLine, "<" + TagString + ">", "</" + TagString + ">");
            System.out.println(TagString + ": " + value);
        }
        if (TagString.equals("releaseUrl")) {
            if (inputLine.startsWith("<!-- " + DependencyModel.BASE_RELEASE_URL)) {
                value = StringUtils.substringBetween(inputLine, "<!-- ", " -->");
                value = value.trim();
                System.out.println(TagString + ": " + value);
            }
        }
        return value;
    }

    private static void findDependencies(String inputDependencyManagementFilePath, List<String> downloadedDependenciesUrl, StringBuilder outputDependencies) {
        try {
            List<String> inputLines = FileUtils.readLines(new File(inputDependencyManagementFilePath), StandardCharsets.UTF_8);
            HashMap<String, String> fileProperties = new HashMap<>();
            boolean isProperties = false;
            boolean isDependencyManagement = false;
            boolean isExclusions = false;
            DependencyModel dependencyModel = new DependencyModel();
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
                if (isProperties) {
                    if (inputLine.startsWith("<!-- ")) {
                        continue;
                    }
                    if (inputLine.startsWith("<")) {
                        String key = StringUtils.substringBetween(inputLine, "<", ">");
                        String value = StringUtils.substringBetween(inputLine, "<" + key + ">", "</" + key + ">");
                        System.out.println("property " + key + ": " + value);
                        fileProperties.put(key, value);
                        continue;
                    }
                }
                if (isDependencyManagement) {
                    if (inputLine.startsWith("<dependency>")) {
                        dependencyModel.getProperties().putAll(fileProperties);
                        continue;
                    }
                    if (inputLine.startsWith("<exclusions>")) {
                        isExclusions = true;
                        continue;
                    }
                    if (inputLine.startsWith("</exclusions>")) {
                        isExclusions = false;
                        continue;
                    }
                    if (!isExclusions) {
                        String releaseUrl = getDependencyTagValue(inputLine, "releaseUrl");
                        String groupId = getDependencyTagValue(inputLine, "groupId");
                        String artifactId = getDependencyTagValue(inputLine, "artifactId");
                        String version = getDependencyTagValue(inputLine, "version");
                        String classifier = getDependencyTagValue(inputLine, "classifier");
                        String type = getDependencyTagValue(inputLine, "type");
                        String scope = getDependencyTagValue(inputLine, "scope");
                        if (releaseUrl != null) dependencyModel.setReleaseUrl(releaseUrl);
                        if (groupId != null) dependencyModel.setGroupId(groupId);
                        if (artifactId != null) dependencyModel.setArtifactId(artifactId);
                        if (version != null) dependencyModel.setVersion(version);
                        if (classifier != null) dependencyModel.setClassifier(classifier);
                        if (type != null) dependencyModel.setType(type);
                        if (scope != null) dependencyModel.setScope(scope);
                    }
                    if (inputLine.startsWith("</dependency>")) {
                        try {
                            if (dependencyModel.getGroupId().contains("${")) {
                                // TO DO
                            } else if (dependencyModel.getVersion() != null && dependencyModel.getVersion().contains("${")) {
                                // TO DO
                            } else if (dependencyModel.getVersion() == null || dependencyModel.getVersion().endsWith("-SNAPSHOT")) {
                                // TO DO
                            } else if (dependencyModel.getScope() == null || dependencyModel.getScope().equals("compile")) {
                                dependencyModel.setVersion(null);
                                if (!outputDependencies.toString().contains(dependencyModel.toString())) {
                                    outputDependencies.append("        <!--" + inputDependencyManagementFilePath + "-->" + "\r\n");
                                    outputDependencies.append(dependencyModel);
                                    outputDependencies.append("\r\n");
                                }
                            } else if (dependencyModel.getScope() != null && dependencyModel.getScope().equals("import")) {
                                String downloadUrl = dependencyModel.getDownloadUrl();
                                String fileName = dependencyModel.getFileName();
                                System.out.println("downloadUrl: " + downloadUrl);
                                System.out.println("fileName: " + fileName);
                                if (!downloadedDependenciesUrl.contains(downloadUrl)) {
                                    String cachefileName = "target/import/" + fileName;
                                    FileUtils.copyInputStreamToFile(new URL(downloadUrl).openConnection().getInputStream(), new File(cachefileName));
                                    FileUtils.readLines(new File(cachefileName), StandardCharsets.UTF_8);
                                    // recursion
                                    downloadedDependenciesUrl.add(downloadUrl);
                                    findDependencies(cachefileName, downloadedDependenciesUrl, outputDependencies);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dependencyModel = new DependencyModel();
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
