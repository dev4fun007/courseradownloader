package bytes.sync.concurrent;



import java.util.logging.Level;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Logger;

public class DownloadTask implements Runnable{

    private static final Logger logger = Logger.getLogger(DownloadTask.class.getName());

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

            logger.log(Level.INFO, "Download request for video: " + urlString);
            logger.log(Level.INFO, "Starting to download");

            //Using Java nio
            videoReadableByteChannel = Channels.newChannel(url.openStream());
            videoFileOutputStream = new FileOutputStream(videoFile);
            videoFileChannel = videoFileOutputStream.getChannel();

            logger.log(Level.INFO, "Downloading");

            videoFileChannel.transferFrom(videoReadableByteChannel, 0, Long.MAX_VALUE);

            logger.log(Level.INFO, "Download completed");

        } catch (MalformedURLException e) {
            logger.log(Level.SEVERE, "Video URL is malformed: " + urlString, e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to download video: " + urlString, e);
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
                logger.log(Level.SEVERE, "Error closing streams", e);
            }
        }
    }

    private static String sanitizePath(String path) {
        path = path.replaceAll("[:;?\"<>|]", "");
        return path;
    }
}
