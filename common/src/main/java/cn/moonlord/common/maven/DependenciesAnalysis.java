package cn.moonlord.common.maven;

import cn.moonlord.common.ssl.TrustAllCerts;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DependenciesAnalysis implements Runnable {

    private final String inputDependencyManagementFilePath;

    private final String outputDependencyFilePath;

    public DependenciesAnalysis(String imputDependencyManagementFilePath, String outputDependencyFilePath) {
        this.inputDependencyManagementFilePath = imputDependencyManagementFilePath;
        this.outputDependencyFilePath = outputDependencyFilePath;
        if(!Files.exists(Paths.get(imputDependencyManagementFilePath))){
            throw new IllegalArgumentException("imputDependencyManagementFilePath must be a valid File");
        }
        if(!Files.exists(Paths.get(outputDependencyFilePath))){
            throw new IllegalArgumentException("outputDependencyFilePath must be a valid File");
        }
        System.out.println("this.inputDependencyManagementFilePath: " + this.inputDependencyManagementFilePath);
        System.out.println("this.outputDependencyFilePath: " + this.outputDependencyFilePath);
    }

    private String getTagString(String inputLine, String TagString, String currentValue) {
        String tmp = currentValue;
        if(tmp == null && inputLine.startsWith("<" + TagString + ">")) {
            tmp = inputLine.substring(inputLine.indexOf("<" + TagString + ">") + ("<" + TagString + ">").length());
            tmp = tmp.substring(0, tmp.indexOf("</" + TagString + ">"));
            System.out.println(TagString + ": " + tmp);
        }
        return tmp;
    }

    private void findDependencies(String inputDependencyManagementFilePath, List<String> downloadedDependenciesUrl, StringBuilder outputDependencies) {
        try {
            List<String> inputLines = FileUtils.readLines(new File(inputDependencyManagementFilePath), StandardCharsets.UTF_8);
            boolean isDependencyManagement = false;
            String baseUrl = null;
            String groupId = null;
            String artifactId = null;
            String version = null;
            String classifier = null;
            String type = null;
            String scope = null;
            for (String inputLine : inputLines) {
                inputLine = inputLine.trim();
                if(inputLine.startsWith("<dependencyManagement>")) {
                    isDependencyManagement = true;
                }
                if(inputLine.startsWith("</dependencyManagement>")) {
                    isDependencyManagement = false;
                }
                if(!isDependencyManagement) {
                    continue;
                }
                if(baseUrl == null && inputLine.startsWith("<!-- https://mvnrepository.com/artifact/")) {
                    inputLine = inputLine.substring(inputLine.indexOf("<!-- https://mvnrepository.com/artifact/") + "<!-- ".length());
                    inputLine = inputLine.substring(0, inputLine.indexOf(" "));
                    inputLine = inputLine.replace(".", "/");
                    inputLine = inputLine.replace("https://mvnrepository/com/artifact/", "https://repo1.maven.org/maven2/");
                    baseUrl = inputLine;
                    System.out.println("baseUrl: " + baseUrl);
                }
                if(baseUrl != null) {
                    groupId = getTagString(inputLine, "groupId", groupId);
                    artifactId = getTagString(inputLine, "artifactId", artifactId);
                    version = getTagString(inputLine, "version", version);
                    classifier = getTagString(inputLine, "classifier", classifier);
                    type = getTagString(inputLine, "type", type);
                    scope = getTagString(inputLine, "scope", scope);
                }
                if(baseUrl != null && inputLine.isEmpty()) {
                    String fileName = artifactId  + "-" + version + ( classifier == null ? "" : "-" + classifier) + "." + ( type == null ? "jar" : type);
                    String dependencyUrl = baseUrl + "/" + version + "/" + fileName;
                    System.out.println("dependencyUrl: " + dependencyUrl);
                    downloadedDependenciesUrl.add(dependencyUrl);
                    if(scope == null) {
                        outputDependencies.append("          <dependency>" + "\r\n");
                        outputDependencies.append("              <groupId>").append(groupId).append("</groupId>").append("\r\n");
                        outputDependencies.append("              <artifactId>").append(artifactId).append("</artifactId>").append("\r\n");
                        outputDependencies.append(classifier == null ? "" : "              <classifier>" + classifier + "</classifier>" + "\r\n");
                        outputDependencies.append(type == null ? "" : "              <type>" + type + "</type>" + "\r\n");
                        outputDependencies.append("          </dependency>" + "\r\n");
                        outputDependencies.append("\r\n");
                    }
                    if(Objects.equals(scope, "import")) {
                        FileUtils.copyInputStreamToFile(TrustAllCerts.setTrusted(new URL(dependencyUrl).openConnection()).getInputStream(), new File("target" + "/" +fileName));
                        FileUtils.readLines(new File("target" + "/" +fileName), StandardCharsets.UTF_8);
                    }
                    baseUrl = null;
                    groupId = null;
                    artifactId = null;
                    version = null;
                    classifier = null;
                    type = null;
                    scope = null;
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
