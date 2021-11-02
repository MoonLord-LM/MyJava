package cn.moonlord.common.maven;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DependenciesAnalysis implements Runnable {

    private final String imputDependencyManagementFilePath;

    private final String outputDependencyFilePath;

    public DependenciesAnalysis(String imputDependencyManagementFilePath, String outputDependencyFilePath) {
        this.imputDependencyManagementFilePath = imputDependencyManagementFilePath;
        this.outputDependencyFilePath = outputDependencyFilePath;
        if(!Files.exists(Paths.get(imputDependencyManagementFilePath))){
            throw new IllegalArgumentException("imputDependencyManagementFilePath must be a valid File");
        }
        if(!Files.exists(Paths.get(outputDependencyFilePath))){
            throw new IllegalArgumentException("outputDependencyFilePath must be a valid File");
        }
        System.out.println("this.imputDependencyManagementFilePath: " + this.imputDependencyManagementFilePath);
        System.out.println("this.outputDependencyFilePath: " + this.outputDependencyFilePath);
    }

    @Override
    public void run() {
        List<String> dependencyUrls = new ArrayList<>();
        String output = "    <dependencies>" + "\r\n"+ "\r\n";
        try {
            List<String> inputLines = FileUtils.readLines(new File(imputDependencyManagementFilePath), StandardCharsets.UTF_8);
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
                if(baseUrl != null && inputLine.startsWith("<groupId>")) {
                    inputLine = inputLine.substring(inputLine.indexOf("<groupId>") + "<groupId>".length());
                    inputLine = inputLine.substring(0, inputLine.indexOf("</groupId>"));
                    groupId = inputLine;
                    System.out.println("groupId: " + groupId);
                }
                if(baseUrl != null && inputLine.startsWith("<artifactId>")) {
                    inputLine = inputLine.substring(inputLine.indexOf("<artifactId>") + "<artifactId>".length());
                    inputLine = inputLine.substring(0, inputLine.indexOf("</artifactId>"));
                    artifactId = inputLine;
                    System.out.println("artifactId: " + artifactId);
                }
                if(baseUrl != null && inputLine.startsWith("<version>")) {
                    inputLine = inputLine.substring(inputLine.indexOf("<version>") + "<version>".length());
                    inputLine = inputLine.substring(0, inputLine.indexOf("</version>"));
                    version = inputLine;
                    System.out.println("version: " + version);
                }
                if(baseUrl != null && inputLine.startsWith("<classifier>")) {
                    inputLine = inputLine.substring(inputLine.indexOf("<classifier>") + "<classifier>".length());
                    inputLine = inputLine.substring(0, inputLine.indexOf("</classifier>"));
                    classifier = inputLine;
                    System.out.println("classifier: " + classifier);
                }
                if(baseUrl != null && inputLine.startsWith("<type>")) {
                    inputLine = inputLine.substring(inputLine.indexOf("<type>") + "<type>".length());
                    inputLine = inputLine.substring(0, inputLine.indexOf("</type>"));
                    type = inputLine;
                    System.out.println("type: " + type);
                }
                if(baseUrl != null && inputLine.startsWith("<scope>")) {
                    inputLine = inputLine.substring(inputLine.indexOf("<scope>") + "<scope>".length());
                    inputLine = inputLine.substring(0, inputLine.indexOf("</scope>"));
                    scope = inputLine;
                    System.out.println("scope: " + scope);
                }
                if(baseUrl != null && inputLine.isEmpty()) {
                    String dependencyUrl = "dependencyUrl: " + baseUrl + "/" + version + "/" + artifactId  + "-" + version + ( classifier == null ? "" : "-" + classifier) + "." + ( type == null ? "jar" : type);
                    System.out.println(dependencyUrl);
                    dependencyUrls.add(dependencyUrl);
                    if(scope == null) {
                        output += "          <dependency>" + "\r\n";
                        output += "              <groupId>" + groupId + "</groupId>" + "\r\n";
                        output += "              <artifactId>" + artifactId + "</artifactId>" + "\r\n";
                        output += (classifier == null ? "" : "              <classifier>" + classifier + "</classifier>" + "\r\n");
                        output += (type == null ? "" : "              <type>" + type + "</type>" + "\r\n");
                        output += "          </dependency>" + "\r\n";
                        output += "\r\n";
                    }
                    groupId = null;
                    artifactId = null;
                    baseUrl = null;
                    classifier = null;
                    type = null;
                    scope = null;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        output += "    </dependencies>";
        System.out.println("output: " + output);
        try {
            List<String> outputLines = new ArrayList<>();
            List<String> tempLines = FileUtils.readLines(new File(outputDependencyFilePath), StandardCharsets.UTF_8);
            boolean isDependency = false;
            for (String line : tempLines) {
                if (line.trim().startsWith("<dependencies>")) {
                    isDependency = true;
                    outputLines.add(output);
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
