package cn.moonlord.common.http;

import org.apache.commons.io.IOUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HLSDownloader implements Runnable {

    private final String downloadURL;

    private final String refererURL;

    private final String fileSavePath;

    public HLSDownloader(String downloadURL, String refererURL, String fileSavePath) {
        try {
            this.downloadURL = new URI(downloadURL).normalize().toString();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("downloadURL must be a valid URL");
        }
        try {
            this.refererURL = new URI(refererURL).normalize().toString();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("refererURL must be a valid URL");
        }
        try {
            this.fileSavePath = new File(fileSavePath).getCanonicalPath();
            if(!Files.exists(Paths.get(fileSavePath))) {
                Files.createDirectories(Paths.get(fileSavePath));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("fileSavePath must be a valid Directory");
        }
        System.out.println("this.downloadURL: " + this.downloadURL);
        System.out.println("this.refererURL: " + this.refererURL);
        System.out.println("this.fileSavePath: " + this.fileSavePath);
    }

    @Override
    public void run() {
        try {
            // index.m3u8
            URLConnection connection = (new URL(downloadURL)).openConnection();
            connection.setConnectTimeout(60 * 1000);
            connection.setReadTimeout(60 * 1000);
            connection.setRequestProperty("Referer", refererURL);
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            IOUtils.copy(inputStream, outputStream);
            byte[] output = outputStream.toByteArray();
            FileOutputStream fileOutputStreamStream = new FileOutputStream(fileSavePath + File.separator + "index.m3u8");
            IOUtils.write(output, fileOutputStreamStream);
            // index*.ts + key.ts
            String[] lines = new String(output, StandardCharsets.UTF_8).split("\n");
            List<String> tsFileNames = new ArrayList<>();
            String encryptionMethod = null;
            String encryptionKeyURI = null;
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i].trim();
                if(!line.startsWith("#" )) {
                    if(!tsFileNames.contains(line)) {
                        System.out.println("ts file" + tsFileNames.size() + " -> line " + i + " : " + line);
                        tsFileNames.add(line);
                    }
                }
                // #EXT-X-KEY:METHOD=AES-128,URI="/xxx/ts.key"
                if(line.startsWith("#")) {
                    String[] config = line.split(":");
                    if(config[0].equals("#EXT-X-KEY")) {
                        String[] keyConfigValues = config[1].split(",");
                        for (int j = 0; j < keyConfigValues.length; j++) {
                            String[] keyConfigValue = keyConfigValues[j].split("=");
                            if(keyConfigValue[0].equals("METHOD")) {
                                encryptionMethod = keyConfigValue[1].replace("\"", "");
                            }
                            else if(keyConfigValue[0].equals("URI")) {
                                encryptionKeyURI = keyConfigValue[1].replace("\"", "");
                            }
                        }
                    }
                }
            }
            // output.ts
            ByteArrayOutputStream totalOutputStream = new ByteArrayOutputStream();
            FileOutputStream totalFileOutputStreamStream = new FileOutputStream(fileSavePath + File.separator + "output.ts");
            for (int i = 0; i < tsFileNames.size(); i++) {
                String tsFileName = tsFileNames.get(i);
                System.out.println("start to download " + i + " : " + tsFileName);
                connection = (new URL(downloadURL.replace("index.m3u8", tsFileName))).openConnection();
                inputStream = connection.getInputStream();
                outputStream = new ByteArrayOutputStream();
                IOUtils.copy(inputStream, outputStream);
                output = outputStream.toByteArray();
                fileOutputStreamStream = new FileOutputStream(fileSavePath + File.separator + tsFileName);
                IOUtils.write(output, fileOutputStreamStream);
                // sum
                IOUtils.write(output, totalOutputStream);
                IOUtils.write(output, totalFileOutputStreamStream);
            }
            // key.ts
            if(encryptionMethod != null && encryptionMethod.equals("AES-128") && encryptionKeyURI != null) {
                String encryptionKeyURL = new URL(new URL(downloadURL), encryptionKeyURI).toString();
                connection = (new URL(encryptionKeyURL)).openConnection();
                inputStream = connection.getInputStream();
                outputStream = new ByteArrayOutputStream();
                IOUtils.copy(inputStream, outputStream);
                byte[] keyBytes = outputStream.toByteArray();
                // result.ts
                byte[] source = totalOutputStream.toByteArray();
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
                cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(new byte[16]));
                byte[] result = cipher.doFinal(source);
                System.out.println("source size : " + source.length + ", result size : " + result.length);
                FileOutputStream resultFileOutputStreamStream = new FileOutputStream(fileSavePath + File.separator + "result.ts");
                IOUtils.write(result, resultFileOutputStreamStream);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
