package cn.moonlord.http;

import cn.moonlord.log.Logger;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleDownloadHandler implements Runnable {

    private String downloadURL;
    private String refererURL;
    private String fileSavePath;
    private byte[] fileBytes;

    public byte[] getFileBytes(){
        return fileBytes;
    }

    public SimpleDownloadHandler(String downloadURL, String refererURL, String fileSavePath){
        this.downloadURL = downloadURL;
        this.refererURL = refererURL;
        this.fileSavePath = fileSavePath;
    }

    @Override
    public void run() {
        try {
            URLConnection connection = (new URL(downloadURL)).openConnection();
            connection.setRequestProperty("Referer", refererURL);
            InputStream inputStream = connection.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(fileSavePath);

            byte[] buffer = new byte[4096];
            List<byte[]> cache = new ArrayList<>(4096);
            int byteSum = 0;
            int byteRead;
            while ((byteRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, byteRead);
                cache.add(Arrays.copyOfRange(buffer, 0, byteRead));
                byteSum += byteRead;
            }
            inputStream.close();
            outputStream.close();

            fileBytes = new byte[byteSum];
            int byteWrite = 0;
            for (int i = 0; i < cache.size(); i++) {
                System.arraycopy(cache.get(i), 0, fileBytes, byteWrite, cache.get(i).length);
                byteWrite += cache.get(i).length;
            }
        } catch (Exception e) {
            Logger.warn(e);
        }
    }

}
