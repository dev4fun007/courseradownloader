package bytes.sync.selenium;

import bytes.sync.concurrent.DownloadHelper;
import bytes.sync.concurrent.DownloadTask;
import bytes.sync.util.FileUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.Random;


public class Scrapper {

    private static final String CHROME_PROFILE_ARGUMENT = "user-data-dir=C:\\Users\\katakuri\\AppData\\Local\\Google\\Chrome\\User Data\\Default";
    private static final String HOME_URL = "https://www.coursera.org/";

    private static Scrapper instance = new Scrapper();
    private static ChromeDriver driver;
    private static Random random;

    private Scrapper() {
        initWebDriver();
        random = new Random();
    }

    private void initWebDriver() {

        //Using the driver manager to decouple web-driver with the program
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments(CHROME_PROFILE_ARGUMENT);
        driver = new ChromeDriver(options);
    }

    public static Scrapper getInstance() {
        return instance;
    }

    public void scrape(String courseOverviewUrl, String courseName) throws InterruptedException {

        driver.navigate().to(HOME_URL + courseOverviewUrl);

        //This is the overview page, check for the number of weeks mentioned in the left navigation pane
        WebElement weekCollectionDiv = driver.findElementByClassName("rc-NavigationDrawer");
        int aTagsForWeeksSize = weekCollectionDiv.findElements(By.tagName("a")).size();
        System.out.println("Number of weeks: " + aTagsForWeeksSize);

        //Create Course Folder
        FileUtil.createCourseFolder(courseName);

        //For each week
        for(int weekIndex = 1; weekIndex <= aTagsForWeeksSize; weekIndex++) {
            String weekUrl = HOME_URL + courseOverviewUrl.replace("welcome", "week/"+weekIndex);
            driver.navigate().to(weekUrl);

            //Create folder for this week
            FileUtil.createWeekFolder(courseName, "week " + weekIndex);

            randomWait(5000);

            //Get the first video link to navigate to
            driver.findElementsByClassName("rc-ModuleSection").get(0)
                    .findElement(By.className("rc-ModuleLessons"))
                    .findElement(By.tagName("a"))
                    .click();

            randomWait(2000);

            //We are in the lectures page - left pane contains different lessons and videos
            List<WebElement> collapsibleLessons = driver.findElements(By.className("rc-CollapsibleLesson"));
            for(WebElement lesson : collapsibleLessons) {
                String lessonName = lesson.findElement(By.className("lesson-name")).getText();
                //Create folder for the videos in this lesson
                String pathTillNow = FileUtil.createLessonFolder(courseName, "week "+weekIndex, lessonName);

                List<WebElement> lessonItems = lesson.findElements(By.tagName("li"));

                //If the lesson items are not expanded - click and expand them
                if(lessonItems == null || lessonItems.size() == 0) {
                    lesson.click();
                    randomWait(1000);
                    lessonItems = lesson.findElements(By.tagName("li"));
                }
                for(WebElement item : lessonItems) {
                    WebElement aTag = item.findElement(By.tagName("a"));
                    if(aTag.getAttribute("href").contains("lecture")) {
                        //A video item
                        item.click();

                        //This will load the video on the right side - wait for a while
                        randomWait(5000);

                        String videoName = driver.findElementByClassName("video-name").getText();
                        //This will grab the first video link - mp4
                        String videoUrl = driver.findElementByTagName("source").getAttribute("src");
                        DownloadTask downloadTask = new DownloadTask(videoUrl, videoName, pathTillNow);
                        DownloadHelper.submitDownloadTask(downloadTask);

                        //Wait before clicking on something else - make it more human
                        randomWait(2000);
                    }
                }
            }

        }

        driver.quit();
        DownloadHelper.shutdownExecutorService();
    }


    private static void randomWait(int minimumWait) {
        try {
            //Wait for some time - make it natural
            Thread.sleep(minimumWait + 1000 * random.nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
