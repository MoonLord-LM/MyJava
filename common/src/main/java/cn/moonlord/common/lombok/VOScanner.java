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
import java.util.ArrayList;
import java.util.Collection;

public class VOScanner implements Runnable {

    private Collection<File> files = new ArrayList<>();

    public VOScanner(String inputScanFilePath) {
        if (!Files.isDirectory(Paths.get(inputScanFilePath))) {
            files.add(new File(inputScanFilePath));
        } else if (Files.isDirectory(Paths.get(inputScanFilePath))) {
            files = FileUtils.listFiles(new File(inputScanFilePath),
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
        }
        System.out.println("inputScanDirPath: " + inputScanFilePath + "files size: " + files.size());
    }

    @Override
    public void run() {
        System.out.println("run begin");
        for (File file : files) {
            System.out.println("files path: " + file.getAbsolutePath());
            try {
                ParseResult<CompilationUnit> source = new JavaParser().parse(new FileInputStream(file));
                if (source.getResult().isPresent()) {
                    AstNodeCleaner newSource = new AstNodeCleaner(source.getResult().get());
                    FileUtils.write(new File(file.getAbsolutePath()), newSource.toString(), StandardCharsets.UTF_8);
                }
            } catch (IllegalArgumentException ignored) {
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("run end");
    }

}
