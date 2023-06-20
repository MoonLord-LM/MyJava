package cn.moonlord.common.lombok;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

public class VOScanner implements Runnable {

    private final String inputScanDirPath;

    public VOScanner(String inputScanFilePath) {
        this.inputScanDirPath = inputScanFilePath;
        if (!Files.exists(Paths.get(inputScanFilePath))) {
            throw new IllegalArgumentException("inputScanDirPath must be a valid Dir Path");
        }
        System.out.println("this.inputScanDirPath: " + this.inputScanDirPath);
    }

    @Override
    public void run() {
        System.out.println("run begin");
        Collection<File> files = FileUtils.listFiles(new File(inputScanDirPath),
            new AbstractFileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.getName().endsWith("VO.java") || file.getName().endsWith("Model.java");
                }
            },
            new AbstractFileFilter() {
                @Override
                public boolean accept(File dir) {
                    return !dir.getName().equals(".git") && !dir.getName().equals("target") && !dir.getName().equals("test");
                }
            }
        );
        System.out.println("files size: " + files.size());
        for (File file : files) {
            System.out.println("files path: " + file.getAbsolutePath());
            try {
                ParseResult<CompilationUnit> source = new JavaParser().parse(new FileInputStream(file));
                if (source.getResult().isPresent()) {
                    AstNodeCleaner newSource = new AstNodeCleaner(source.getResult().get());
                    FileUtils.write(new File(file.getAbsolutePath()), newSource.toString(), StandardCharsets.UTF_8);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("run end");
    }

}
