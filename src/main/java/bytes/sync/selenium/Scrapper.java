package bytes.sync.selenium;

import bytes.sync.downloader.DownloaderUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

public class Scrapper {

    private static final String CHROME_PROFILE_ARGUMENT = "user-data-dir=C:\\Users\\katakuri\\AppData\\Local\\Google\\Chrome\\User Data\\Default";
    private static final String HOME_URL = "https://www.coursera.org/";

    private static Scrapper instance = new Scrapper();
    private static ChromeDriver driver;

    private Scrapper() {
        initWebDriver();
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

    public void scrape(String courseOverviewUrl, String courseName) {

        driver.navigate().to(HOME_URL + courseOverviewUrl);

        //This is the overview page, check for the number of weeks mentioned in the left navigation pane
        WebElement weekCollectionDiv = driver.findElementByClassName("rc-NavigationDrawer");
        List<WebElement> aTagsForWeeks = weekCollectionDiv.findElements(By.tagName("a"));
        System.out.println("Number of weeks: "+aTagsForWeeks.size());

        //Create Course Folder
        DownloaderUtil.createFolder(courseName);

        //For each week
        int weekIndex = 1;
        for(WebElement element : aTagsForWeeks) {
            String weekUrl = HOME_URL + courseOverviewUrl.replace("welcome", "week/"+weekIndex);
            driver.navigate().to(weekUrl);
        }

    }

}
