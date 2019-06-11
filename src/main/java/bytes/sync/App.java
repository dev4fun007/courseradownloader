package bytes.sync;

import bytes.sync.selenium.Scrapper;

/**
 * Hello world!
 *
 */
public class App 
{

    private static String courseOverviewUrl = "learn/real-time-streaming-big-data/home/welcome";
    private static String courseName = "Big Data Applications: Real-Time Streaming";

    public static void main( String[] args )
    {
        Scrapper.getInstance().scrape(courseOverviewUrl, courseName);
    }
}
