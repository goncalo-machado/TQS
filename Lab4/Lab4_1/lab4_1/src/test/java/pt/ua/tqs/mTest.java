package pt.ua.tqs;
import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;

public class mTest {
    WebDriver driver;
    static final Logger log = org.slf4j.LoggerFactory.getLogger(lookup().lookupClass());

    @BeforeEach
    void setUp() {
        //System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
        ChromeOptions options = new ChromeOptions()
                .setHeadless(true);
        driver = new ChromeDriver(options);
    }


    @Test
    void test() {
        // Exercise
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl);
        String title = driver.getTitle();
        log.debug("The title of {} is {}", sutUrl, title);

        assertTrue(title.equals("Hands-On Selenium WebDriver with Java"));
    }

    @AfterEach
    void teardown() {
            driver.quit();
    }
}
