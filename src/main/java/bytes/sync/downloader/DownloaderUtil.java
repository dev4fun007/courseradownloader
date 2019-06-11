package bytes.sync.downloader;

import java.io.File;
import java.io.IOException;

public class DownloaderUtil {

    private static final String BASE_PATH = "C:\\Users\\i319829\\Downloads\\Coursera";

    public static void createFolder(String courseName) {
        File dir = new File(BASE_PATH + File.pathSeparator + courseName);
        if(dir.mkdir()) {
            System.out.println("Folder created at path: " + dir.getAbsolutePath());
        } else {
            System.out.println("Failed to create directory" + dir.getAbsolutePath());
        }
    }

}
