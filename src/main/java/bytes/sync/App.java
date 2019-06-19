package bytes.sync;

import bytes.sync.selenium.Scrapper;
import bytes.sync.util.NotificationUtil;

import java.awt.*;

import static java.lang.System.exit;

public class App
{
    private static String courseOverviewUrl = "learn/distributed-programming-in-java/home/welcome";
    private static String courseName = "Distributed Programming in Java";

    public static void main( String[] args ) {
        try {
            Scrapper.getInstance().scrape(courseOverviewUrl, courseName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        exit(0);
    }
}


/* Course Downloaded
learn/big-data-essentials/home/welcome
learn/real-time-streaming-big-data/home/welcome
learn/gcp-big-data-ml-fundamentals/home/welcome




 */