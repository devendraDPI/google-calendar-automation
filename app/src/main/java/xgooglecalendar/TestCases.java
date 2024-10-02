package xgooglecalendar;

import java.io.File;
import java.time.Duration;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

public class TestCases {
    WebDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");
        System.out.println("Start Tests: TestCases");

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222"); // Connect to the chrome-window running on debugging port

        // Set path for log file
        File theDir = new File("logs");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "logs" + File.separator + "chromedriver.log");

        driver = new ChromeDriver(options);

        // Implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    public void endTest() {
        System.out.println("End Tests: TestCases");
        driver.quit();
    }

    public void testCase01() throws InterruptedException {
        System.out.println("\nTestCase01: START");
        driver.get("https://www.google.com");
        Thread.sleep(5000);
        System.out.println("TestCase01: END\n");
    }
}
