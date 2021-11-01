package cn.moonlord.common.maven;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(imputDependencyManagementFilePath);
            System.out.println("document: " + document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
