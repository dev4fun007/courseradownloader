package bytes.sync;

import bytes.sync.selenium.Scrapper;
import bytes.sync.util.DownloadUtil;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Hello world!
 *
 */
public class App 
{

    private static String courseOverviewUrl = "learn/real-time-streaming-big-data/home/welcome";
    private static String courseName = "Big Data Applications: Real-Time Streaming";

    public static void main( String[] args ) {
        try {
            Scrapper.getInstance().scrape(courseOverviewUrl, courseName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //DownloadUtil.getInstance().downloadFile(null,"","");
    }
}
