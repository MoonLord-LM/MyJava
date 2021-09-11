package cn.moonlord.io;

import cn.moonlord.security.Hex;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

public class SourceFileEncodingConverter implements Runnable {

    public static final String UNKNOWN_STRING = "�";

    public static final byte[] UNKNOWN_BYTES = UNKNOWN_STRING.getBytes(StandardCharsets.UTF_8);

    private final String sourceFilePath;

    public SourceFileEncodingConverter(String sourceFilePath) {
        try {
            this.sourceFilePath = new File(sourceFilePath).getCanonicalPath();
            if(!Files.exists(Paths.get(sourceFilePath))){
                Files.createDirectories(Paths.get(sourceFilePath));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("sourceFilePath must be a valid Directory");
        }
        System.out.println("this.sourceFilePath: " + this.sourceFilePath);
    }

    @Override
    public void run() {
        try {
            System.out.println("UNKNOWN_BYTES: " + Hex.encode(UNKNOWN_BYTES));

            File sourceDir = new File(sourceFilePath);
            System.out.println("sourceDir: " + sourceDir.getCanonicalPath());
            Collection<File> allFileList = FileUtils.listFiles(sourceDir,  null, true);
            System.out.println("allFileListSize: " + allFileList.size());
            Collection<File> sourceFileList = FileUtils.listFiles(sourceDir, new String[] {"java", "xml", "properties"}, true);
            System.out.println("sourceFileListSize: " + sourceFileList.size());
            for (File file: sourceFileList) {
                String filePath = file.getCanonicalPath();
                if(filePath.contains("\\.git\\") || filePath.contains("\\.idea\\") || filePath.contains("\\target\\")) {
                    continue;
                }
                if(!file.exists() || !file.isFile() || !file.canRead() || !file.canWrite()){
                    continue;
                }
                byte[] sourceBytes = FileUtils.readFileToByteArray(file);
                boolean isBadFile = false;
                for (int i = 0; i < sourceBytes.length - 2; i++) {
                    if(sourceBytes[i] == UNKNOWN_BYTES[0] && sourceBytes[i + 1] == UNKNOWN_BYTES[1] && sourceBytes[i + 2] == UNKNOWN_BYTES[2]){
                        isBadFile = true;
                    }
                }
                if(isBadFile) {
                    System.out.println("file encoding is bad: " + file.getCanonicalPath());
                    continue;
                }
                String sourceString = new String(sourceBytes, StandardCharsets.UTF_8);
                if(sourceString.contains(UNKNOWN_STRING)) {
                    sourceString = new String(sourceBytes, Charset.forName("GB2312"));
                    if(!sourceString.contains(UNKNOWN_STRING)) {
                        System.out.println("convert from GB2312 to UTF-8: " + file.getCanonicalPath());
                        FileUtils.write(file, sourceString, StandardCharsets.UTF_8);
                    }
                    else {
                        System.out.println("file encoding is unknown: " + file.getCanonicalPath());
                    }
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
