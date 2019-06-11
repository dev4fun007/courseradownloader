package bytes.sync.selenium;

import org.openqa.selenium.chrome.ChromeDriver;

public class Scrapper {

    public static Scrapper instance = new Scrapper();
    private static ChromeDriver driver;

    private Scrapper() {
        initWebDriver();
    }

    private void initWebDriver() {
        driver = new ChromeDriver();
    }

    public static Scrapper getInstance() {
        return instance;
    }

    public static void scrape(String courseOverviewUrl) {

        driver.navigate().to(courseOverviewUrl);


    }


}
