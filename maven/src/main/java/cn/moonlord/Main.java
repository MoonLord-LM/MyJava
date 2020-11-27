package cn.moonlord;

import cn.moonlord.security.*;
import cn.moonlord.security.Base64;
import org.apache.commons.io.FileUtils;
import sun.security.util.ArrayUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Main {

    private static final String[] KEY_COMPONENT_QUESTION = new String[] {
            "1. 身份证号（18 位）？",
            "2. 最常用的 QQ 号（9 位）？",
            "3. 最常用的手机号（11 位）？",
            "4. 最常用的银行卡的卡号（19 位）？",
            "5. 最常用的英文 ID 的小写（8 位）？",
            "6. 最常用的数字密码（6 位）？",
            "7. 淘宝和支付宝账号的登陆密码（12 位）？",
            "8. 最喜欢的水果的中文名称，每个字的拼音的首字母的大写（2 位）？",
            "9. 最喜欢的女孩子的名字，每个字的拼音的首字母的大写（重复扩展到 13 位）？",
            "10. 日记文件的密码（重复扩展到 13 位）？"
    };

    private static final int KEY_COMPONENT_LENGTH = 111;

    private static final int KEY_COMPONENT_HASH_LENGTH = 128;

    private static final int KEY_ITERATION_COUNT = 16 * 1024 * 1024;

    private static final String WORKING_DIRECTORY = "F:\\Documents\\GitHub\\MyWebsite";

    private static final File KEY_COMPONENT_HASH_FILE = new File(WORKING_DIRECTORY + File.separator + "secret.tmp");

    private static final String[] SOURCE_FILE_SUFFIX = new String[] {"md", "MD"};

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        PrintWriter error = new PrintWriter(System.err);
        // 密钥组件哈希值
        String keyComponentHash = "";
        if(KEY_COMPONENT_HASH_FILE.exists()) {
            keyComponentHash = FileUtils.readFileToString(KEY_COMPONENT_HASH_FILE, StandardCharsets.UTF_8);
            writer.println("readFileToString: " + keyComponentHash.length() + " " + keyComponentHash);
            writer.flush();
        }
        if(keyComponentHash.isEmpty()){
            // 密钥保护问题
            StringBuilder tmp = new StringBuilder();
            for (String question : KEY_COMPONENT_QUESTION) {
                writer.println(question);
                writer.flush();
                tmp.append(scanner.nextLine());
            }
            String answer = tmp.toString();
            writer.println("answer: " + answer.length() + " " + answer);
            writer.flush();
            if(answer.length() != KEY_COMPONENT_LENGTH){
                error.println("wrong answer, expected length is: " + KEY_COMPONENT_LENGTH);
                error.flush();
                System.exit(0);
            }
            keyComponentHash = Hash.sha512(answer.getBytes(StandardCharsets.UTF_8));
            FileUtils.write(KEY_COMPONENT_HASH_FILE, keyComponentHash, StandardCharsets.UTF_8);
        }
        if(keyComponentHash.length() != KEY_COMPONENT_HASH_LENGTH){
            error.println("wrong hash, expected length is: " + KEY_COMPONENT_HASH_LENGTH);
            error.flush();
            System.exit(0);
        }
        writer.println("keyComponentHash: " + keyComponentHash.length() + " " + keyComponentHash);
        writer.flush();
        // 密钥导出
        byte[] allHash = new byte[KEY_ITERATION_COUNT * 64];
        byte[] beginHash = Hex.decode(keyComponentHash);
        byte[] tmpHash = beginHash;
        byte[] tmpInput = new byte[tmpHash.length * 2];
        for (int i = 1; i < KEY_ITERATION_COUNT; i++) {
            System.arraycopy(tmpHash, 0, tmpInput, 0, tmpHash.length);
            System.arraycopy(beginHash, 0, tmpInput, beginHash.length, beginHash.length);
            tmpHash = Hash.sha512ToBytes(tmpInput);
            System.arraycopy(tmpHash, 0, allHash, i * tmpHash.length, tmpHash.length);
        }
        byte[] allHashHash = Hash.sha512ToBytes(allHash);
        String finalHash = Hex.encode(allHashHash);
        writer.println("finalHash: " + finalHash.length() + " " + finalHash);
        writer.flush();
        byte[] part1 = new byte[tmpHash.length / 2];
        System.arraycopy(allHashHash, 0, part1, 0, allHashHash.length / 2);
        byte[] part2 = new byte[tmpHash.length / 2];
        System.arraycopy(allHashHash, allHashHash.length / 2, part2, 0, allHashHash.length / 2);
        byte[] key = Xor.merge(part1, part2);
        writer.println("key: " + key.length + " " + Base64.encode(key));
        writer.flush();
        // 加密文件
        List<File> sourceFiles = new ArrayList<>(FileUtils.listFiles(new File(WORKING_DIRECTORY), SOURCE_FILE_SUFFIX, true));
        writer.println("sourceFiles: " + sourceFiles.size() + " " + sourceFiles);
        writer.flush();
        List<File> safeFiles = sourceFiles.stream().map(sourceFile -> new File(sourceFile.getPath() + ".safe")).collect(Collectors.toList());
        writer.println("safeFiles: " + safeFiles.size() + " " + safeFiles);
        writer.flush();
        ConcurrentHashMap<String, String> encryptionMapping = new ConcurrentHashMap<>();
        for (int i = 0; i < safeFiles.size(); i++) {
            List<String> encryptedLines = FileUtils.readLines(safeFiles.get(i), StandardCharsets.UTF_8);
            for (String encryptedLine : encryptedLines) {
                if(encryptedLine.length() != 0){
                    String line = Aes.decryptStringFromBase64String(encryptedLine, key);
                    if(!encryptionMapping.contains(line)){
                        encryptionMapping.putIfAbsent(line, encryptedLine);
                    }
                }
            }
        }
        writer.println("encryptionMapping: " + encryptionMapping.size());
        writer.flush();
        for (int i = 0; i < sourceFiles.size(); i++) {
            List<String> lines = FileUtils.readLines(sourceFiles.get(i), StandardCharsets.UTF_8);
            List<String> encryptedLines = new ArrayList<>();
            for (String line : lines) {
                if(line.length() == 0){
                    encryptedLines.add("");
                }
                else if(encryptionMapping.contains(line)){
                    encryptedLines.add(encryptionMapping.get(line));
                }
                else{
                    String encryptedLine = Aes.encryptToBase64String(line, key);
                    encryptedLines.add(encryptedLine);
                    encryptionMapping.putIfAbsent(line, encryptedLine);
                }
            }
            FileUtils.writeLines(safeFiles.get(i), encryptedLines);
        }
        writer.println("encryptionMapping: " + encryptionMapping.size());
        writer.flush();
    }

}
