package cn.moonlord.common.git;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SingleFileCommitter implements Runnable {

    private final String repoPath;

    public SingleFileCommitter(String repoPath) {
        this.repoPath = repoPath;
        if (!Files.exists(Paths.get(repoPath))) {
            throw new IllegalArgumentException("repoPath must be a valid Dir Path");
        }
        System.out.println("this.repoPath: " + this.repoPath);
    }

    public List<String> runCmd(String cmd) {
        try {
            System.out.println("        <<<" + cmd);
            Process process = Runtime.getRuntime().exec(cmd, null, new File(this.repoPath));
            List<String> lines = IOUtils.readLines(process.getInputStream(), StandardCharsets.UTF_8);
            for (String line : lines) {
                System.out.println("        >>>" + line);
            }
            return lines;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        System.out.println("run begin");
        try {
            // status
            List<String> lines = runCmd("git status");
            String branch = null;
            List<String> filePaths = new ArrayList<>();
            for (String line : lines) {
                line = line.replace("\t", "").trim();
                if (branch == null && line.startsWith("On branch")) {
                    branch = line.replace("On branch", "").trim();
                } else if (line.startsWith("modified:")) {
                    String filePath = line.replace("modified:", "").trim();
                    filePaths.add(filePath);
                }
            }
            if (filePaths.isEmpty()) {
                System.out.println("filePaths is empty");
                return;
            }
            // branch
            String newBranch = branch + "-liming-" + "20230602" + RandomUtils.nextInt(1000, 10000);
            runCmd("git stash");
            runCmd("git checkout -b \"" + newBranch + "\"");
            runCmd("git stash pop");
            System.out.println("branch: " + branch + "  ->  newBranch: " + newBranch);
            // commit
            for (int i = 0; i <filePaths.size() ; i++) {
                System.out.println("commit: " + (i+1) + " / " + filePaths.size());
                runCmd("git add " + "\"" + filePaths.get(i) + "\"");
                runCmd("git commit -m \"Update\"");
                runCmd("git push origin \"" + newBranch + "\"");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("run end");
    }

}
