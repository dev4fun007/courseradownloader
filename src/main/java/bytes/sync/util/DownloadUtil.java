package bytes.sync.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class DownloadUtil {

    private static DownloadUtil instance = new DownloadUtil();

    private DownloadUtil() {}

    public static DownloadUtil getInstance() {
        return instance;
    }

    public void downloadFile(URL url, String fileName, String path) {

        try {
            url = new URL("https://d3c33hcgiwev3.cloudfront.net/9YEZbSYjEemj-RKX93anOA.processed/full/540p/index.mp4?Expires=1560643200&Signature=M8L2kVZEDX6KzWErbt7edgpBWItoOBITC8~wG4JTfuoVyIQTJ2RLywwh29j8rKhnK6Ug1-rkAeDVK31IBMz16uC1PPNQnHcYDyLUQYj5RuUS5bN2zYe8loAiWZjD8-7gr8NehKzPEq5vYT4uesL0Tl2nZLBS6DiuL01b1SbP6vg_&Key-Pair-Id=APKAJLTNE6QMUY6HBC5A");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        ReadableByteChannel urlReadableChannel = null;

        try {

            urlReadableChannel = Channels.newChannel(url.openStream());
            File videoFile = new File("Coursera" + File.separator + "test.mp4");
            FileOutputStream fileOutputStream = new FileOutputStream(videoFile);
            FileChannel fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(urlReadableChannel, 0, Long.MAX_VALUE);


        } catch (IOException e) {
            System.out.println("IO exception caught while downloading file: " + e.getMessage());
        } finally {
            if(urlReadableChannel != null) {
                try {
                    urlReadableChannel.close();
                } catch (IOException e) {
                    System.out.println("Unable to close url readable channel: " + e.getMessage());
                }
            }
        }
    }

}
