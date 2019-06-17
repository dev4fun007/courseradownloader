package bytes.sync.concurrent;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class DownloadTask implements Runnable{

    private String urlString;
    private String videoName;
    private String videoPath;

    public DownloadTask(String urlString, String videoName, String videoPath) {
        this.urlString = urlString;
        this.videoName = videoName;
        this.videoPath = videoPath;
    }


    @Override
    public void run() {
        downloadVideo();
    }

    private void downloadVideo() {

        ReadableByteChannel videoReadableByteChannel = null;
        FileOutputStream videoFileOutputStream = null;
        FileChannel videoFileChannel = null;

        try {
            //Video url
            URL url = new URL(urlString);
            File videoFile = new File(sanitizePath(videoPath + File.separator + videoName + ".mp4"));
            if(videoFile.exists()) {
                //File exists, no need to re-download it
                return;
            }

            System.out.println("Starting to download........");
            //Using Java nio
            videoReadableByteChannel = Channels.newChannel(url.openStream());
            videoFileOutputStream = new FileOutputStream(videoFile);
            videoFileChannel = videoFileOutputStream.getChannel();
            System.out.println("Downloading........");
            videoFileChannel.transferFrom(videoReadableByteChannel, 0, Long.MAX_VALUE);
            System.out.println("Download complete for video: " + videoName);

        } catch (MalformedURLException e) {
            System.out.println("Video URL is malformed: " + e);
        } catch (IOException e) {
            System.out.println("Download IO error: " + e);
        } finally {
            try {
                if(videoReadableByteChannel != null) {
                    videoReadableByteChannel.close();
                }
                if(videoFileChannel != null) {
                    videoFileChannel.close();
                }
                if(videoFileOutputStream != null) {
                    videoFileOutputStream.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing streams: " + e);
            }
        }
    }

    private static String sanitizePath(String path) {
        path = path.replaceAll("[:;?\"<>|]", "");
        return path;
    }

    private void saveVideoInfoOnError(String videoName, String videoUrl) {

    }

}
