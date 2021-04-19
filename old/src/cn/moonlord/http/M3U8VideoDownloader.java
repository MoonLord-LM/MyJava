package cn.moonlord.http;

import cn.moonlord.log.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class M3U8VideoDownloader implements Runnable {

    private String downloadURL;
    private String downloadDirectory;
    private String refererURL;
    private String fileSaveDirectory;
    private String fileSaveName;
    private byte[] indexFileBytes;
    private String indexFileContent;
    private List<String> videoFileList;
    private List<SimpleDownloadHandler> videoFileDownloaders;
    private AtomicInteger videoDownloadedCount;

    public M3U8VideoDownloader(String downloadURL, String refererURL, String fileSavePath){
        this.downloadURL = downloadURL;
        this.downloadDirectory = downloadURL.substring(0, downloadURL.lastIndexOf("/"));
        this.refererURL = refererURL;
        if(fileSavePath.contains("/") && fileSavePath.endsWith("/") == false){
            this.fileSaveDirectory = fileSavePath.substring(0, fileSavePath.lastIndexOf("/"));
            this.fileSaveName = fileSavePath.substring(fileSavePath.lastIndexOf("/") + 1);
        }
        else{
            this.fileSaveDirectory = fileSavePath;
            this.fileSaveName = "sum.ts";
        }
        (new Thread(this)).start();
    }

    @Override
    public void run() {
        try {
            SimpleDownloadHandler indexHandler = new SimpleDownloadHandler(downloadURL, refererURL, fileSaveDirectory + "/index.m3u8");
            indexHandler.run();
            indexFileBytes = indexHandler.getFileBytes();
            Logger.info("the file size of [ index.m3u8 ] is " + indexFileBytes.length);
            indexFileContent = new String(indexFileBytes);
            Logger.info("the file content of [ index.m3u8 ] is" + "\r\n" + indexFileContent);

            String[] indexFileLines = indexFileContent.split("\n");
            videoFileList = new ArrayList<>(4096);
            for (int i = 0; i < indexFileLines.length; i++) {
                if(indexFileLines[i].trim().startsWith("#") == false){
                    videoFileList.add(indexFileLines[i]);
                    Logger.info("the video file name of [ index.m3u8 ] is " + indexFileLines[i]);
                }
            }
            Logger.warn("the video file count of [ index.m3u8 ] is " + videoFileList.size());

            videoFileDownloaders = new ArrayList<>(videoFileList.size());
            videoDownloadedCount = new AtomicInteger(0);
            for (int i = 0; i < videoFileList.size(); i++) {
                SimpleDownloadHandler handler = new SimpleDownloadHandler(
                        downloadDirectory + "/" + videoFileList.get(i),
                        refererURL,
                        fileSaveDirectory + "/" + videoFileList.get(i)
                );
                videoFileDownloaders.add(handler);
                (new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.run();
                        int count = videoDownloadedCount.incrementAndGet();
                        Logger.warn("the count of downloaded video file is "+ count);
                        if (count == videoFileList.size()){
                            Logger.warn("all the video file is downloaded");

                            int fileSizeSum = 0;
                            for (int j = 0; j < videoFileList.size(); j++) {
                                SimpleDownloadHandler handler = videoFileDownloaders.get(j);
                                fileSizeSum += handler.getFileBytes().length;
                            }
                            byte[] fileBytesSum = new byte[fileSizeSum];
                            int byteWrite = 0;
                            for (int j = 0; j < videoFileList.size(); j++) {
                                SimpleDownloadHandler handler = videoFileDownloaders.get(j);
                                System.arraycopy(handler.getFileBytes(), 0, fileBytesSum, byteWrite, handler.getFileBytes().length);
                                byteWrite += handler.getFileBytes().length;
                            }
                            Logger.warn("the joined video file size is " + fileBytesSum.length + " Byte");

                            try {
                                FileOutputStream outputStream = new FileOutputStream(fileSaveDirectory + "/" + fileSaveName);
                                outputStream.write(fileBytesSum, 0, fileBytesSum.length);
                                outputStream.close();
                            } catch (Exception e) {
                                Logger.warn(e);
                            }
                        }
                    }
                })).start();
            }
        } catch (Exception e) {
            Logger.warn(e);
        }
    }

}
