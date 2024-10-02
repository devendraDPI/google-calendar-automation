package xgooglecalendar;

import java.io.File;
import java.time.Duration;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    /**
     *  <STRONG>Test Case 01</STRONG>: Verify the Google Calendar Homepage URL <p>
     *  <STRONG>Description</STRONG>: Verify that the Google Calendar homepage URL contains "calendar" <p>
     *  <STRONG>Expected Result</STRONG>: The URL of the Calendar homepage contains "calendar" <p>
     *
     *  1. Launch chrome browser <p>
     *  2. Navigate to Google Calendar https://calendar.google.com <p>
     *  3. Verify that the current URL contains "calendar" <p>
     */
    public void testCase01() {
        System.out.println("\nTestCase01: START");

        // Launch chrome browser
        // Navigate to Google Calendar https://calendar.google.com
        driver.get("https://calendar.google.com");

        // Verify that the current URL contains "calendar"
        String actualUrl = driver.getCurrentUrl();
        String expectedUrlContent = "calendar";

        if (actualUrl.contains(expectedUrlContent)) {
            System.out.println("TestCase01: The URL contains 'calendar': PASS");
        } else {
            System.out.println("TestCase01: The URL does not contains 'calendar': FAIL");
        }

        System.out.println("TestCase01: END\n");
    }

    /**
     *  <STRONG>Test Case 02</STRONG>: Verify Calendar Navigation and Add Task <p>
     *  <STRONG>Description</STRONG>: Switch to the month view, and add a task for today <p>
     *  <STRONG>Expected Result</STRONG>: The Calendar switched to month view and task is successfully created, and the confirmation message indicates "Task created" <p>
     *
     *  1. Launch chrome browser <p>
     *  2. Navigate to Google Calendar https://calendar.google.com <p>
     *  3. Switch to the month view <p>
     *  4. Click on the calendar to add a task <p>
     *  5. Navigate to the Tasks tab <p>
     *  6. Select a date and enter task details. Task Details: <p>
     *  7. Title: Crio INTV Task Automation <p>
     *  8. Description: Crio INTV Calendar Task Automation <p>
     *  9. Click on save <p>
     *  10. Confirm the task creation, by verifying "Task created" is displayed <p>
     */
    public void testCase02() throws InterruptedException {
        System.out.println("\nTestCase02: START");

        // Launch chrome browser
        // Navigate to Google Calendar https://calendar.google.com
        driver.get("https://calendar.google.com");

        // Switch to the month view
        WebElement viewSwitcherMenuButton = driver.findElement(By.xpath("//span[contains(text(), 'View switcher menu')]/parent::div//button"));
        viewSwitcherMenuButton.click();
        Thread.sleep(2000);

        WebElement monthViewButton = driver.findElement(By.xpath("//li[contains(@data-viewkey, 'month')]"));
        monthViewButton.click();
        Thread.sleep(2000);

        WebElement monthView = driver.findElement(By.xpath("//span[contains(text(), 'Month')]"));
        if (monthView.getText().equals("Month")) {
            System.out.println("TestCase02: Switched to month view: PASS");
        } else {
            System.out.println("TestCase02: Unable to switched to month view: FAIL");
        }

        // Click on the calendar to add a task
        WebElement createButton = driver.findElement(By.xpath("//div[contains(text(), 'Create')]"));
        createButton.click();
        Thread.sleep(2000);

        // Navigate to the Tasks tab
        WebElement taskButton = driver.findElement(By.xpath("//div[contains(@role, 'menu')]//div[contains(text(), 'Task')]"));
        taskButton.click();
        Thread.sleep(2000);

        // Select a date and enter task details. Task Details:
        // Title: Crio INTV Task Automation
        String taskTitle = "Crio INTV Task Automation";
        WebElement taskTitleTextBox = driver.findElement(By.xpath("//input[contains(@placeholder, 'Add title')]"));
        taskTitleTextBox.sendKeys(taskTitle);
        Thread.sleep(2000);

        // Description: Crio INTV Calendar Task Automation
        String taskDescription = "Crio INTV Calendar Task Automation";
        WebElement taskDescriptionTextArea = driver.findElement(By.xpath("//textarea[contains(@placeholder, 'Add description')]"));
        taskDescriptionTextArea.sendKeys(taskDescription);
        Thread.sleep(2000);

        // Click on save
        WebElement saveButton = driver.findElement(By.xpath("//span[contains(text(), 'Save')]/parent::button"));
        saveButton.click();

        // Confirm the task creation, by verifying "Task created" is displayed
        WebElement taskCreatedPopup = driver.findElement(By.xpath("//div[contains(text(), 'Task created')]"));

        if (taskCreatedPopup.isDisplayed() && taskCreatedPopup.getText().equals("Task created")) {
            System.out.println("TestCase02: Task created: PASS");
        } else {
            System.out.println("TestCase02: Task not created: FAIL");
        }
        Thread.sleep(2000);

        System.out.println("TestCase02: END\n");
    }

    /**
     *  <STRONG>Test Case 03</STRONG>: Verify the Task Updation <p>
     *  <STRONG>Description</STRONG>: Select an existing task, update its description, and verify the successful update <p>
     *  <STRONG>Expected Result</STRONG>: The task is successfully updated, and the confirmation message indicates "Task updated" <p>
     *
     *  1. Launch chrome browser <p>
     *  2. Navigate to Google Calendar https://calendar.google.com <p>
     *  3. Switch to the month view <p>
     *  4. Click on an existing task <p>
     *  5. Open the task details <p>
     *  6. Click on edit (pencil) icon <p>
     *  7. Clear the description textarea <p>
     *  8. Update the task description to "Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application" <p>
     *  9. Click on save <p>
     *  10. Verify that the updated description is displayed <p>
     *  11. Confirm the task updation, by verifying "Task updated" is displayed <p>
     */
    public void testCase03() throws InterruptedException {
        System.out.println("\nTestCase03: START");

        // Launch chrome browser
        // Navigate to Google Calendar https://calendar.google.com
        driver.get("https://calendar.google.com");

        // Switch to the month view
        WebElement viewSwitcherMenuButton = driver.findElement(By.xpath("//span[contains(text(), 'View switcher menu')]/parent::div//button"));
        viewSwitcherMenuButton.click();
        Thread.sleep(2000);

        WebElement monthViewButton = driver.findElement(By.xpath("//li[contains(@data-viewkey, 'month')]"));
        monthViewButton.click();
        Thread.sleep(2000);

        // Click on an existing task
        WebElement oldTask = driver.findElement(By.xpath("//span[contains(text(), 'Crio INTV Task Automation')]"));
        oldTask.click();
        Thread.sleep(2000);

        // Open the task details
        // Click on edit (pencil) icon
        WebElement editButton = driver.findElement(By.xpath("//button[contains(@aria-label, 'Edit task')]"));
        editButton.click();
        Thread.sleep(2000);

        // Clear the description textarea
        WebElement taskDescriptionTextArea = driver.findElement(By.xpath("//textarea[contains(@placeholder, 'Add description')]"));
        taskDescriptionTextArea.clear();
        Thread.sleep(2000);

        // Update the task description to "Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application"
        String taskDescription = "Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application";
        taskDescriptionTextArea.sendKeys(taskDescription);
        Thread.sleep(2000);

        // Click on save
        WebElement saveButton = driver.findElement(By.xpath("//span[contains(text(), 'Save')]/parent::button"));
        saveButton.click();

        // Confirm the task updation, by verifying "Task updated" is displayed
        WebElement taskUpdatedPopup = driver.findElement(By.xpath("//div[contains(text(), 'Task updated')]"));

        if (taskUpdatedPopup.isDisplayed() && taskUpdatedPopup.getText().equals("Task updated")) {
            System.out.println("TestCase03: Task updated: PASS");
        } else {
            System.out.println("TestCase03: Task not updated: FAIL");
        }
        Thread.sleep(2000);

        // Click on an existing task
        oldTask = driver.findElement(By.xpath("//span[contains(text(), 'Crio INTV Task Automation')]"));
        oldTask.click();
        Thread.sleep(2000);

        // Verify that the updated description is displayed
        String actualUpdatedDescription = driver.findElement(By.xpath("//span[contains(text(), 'Description')]/parent::div")).getText();
        String expectedUpdatedDescription = "Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application";
        if (actualUpdatedDescription.contains(expectedUpdatedDescription)) {
            System.out.println("TestCase03: The updated description is displayed: PASS");
        } else {
            System.out.println("TestCase03: The updated description is not displayed: FAIL");
        }

        System.out.println("TestCase03: END\n");
    }

    /**
     *  <STRONG>Test Case 04</STRONG>: Verify the Task deletion <p>
     *  <STRONG>Description</STRONG>: Delete an existing task and confirm the deletion <p>
     *  <STRONG>Expected Result</STRONG>: The task is successfully deleted, and the confirmation message indicates "Task deleted" <p>
     *
     *  1. Launch chrome browser <p>
     *  2. Navigate to Google Calendar https://calendar.google.com <p>
     *  3. Switch to the month view <p>
     *  4. Click on an existing task <p>
     *  5. Open the task details <p>
     *  6. Verify the title of the task is "Crio INTV Task Automation" <p>
     *  7. Delete the task <p>
     *  8. Confirm the task deletion, by verifying "Task deleted" is displayed <p>
     */
    public void testCase04() throws InterruptedException {
        System.out.println("\nTestCase04: START");

        // Launch chrome browser
        // Navigate to Google Calendar https://calendar.google.com
        driver.get("https://calendar.google.com");

        // Switch to the month view
        WebElement viewSwitcherMenuButton = driver.findElement(By.xpath("//span[contains(text(), 'View switcher menu')]/parent::div//button"));
        viewSwitcherMenuButton.click();
        Thread.sleep(2000);

        WebElement monthViewButton = driver.findElement(By.xpath("//li[contains(@data-viewkey, 'month')]"));
        monthViewButton.click();
        Thread.sleep(2000);

        // Click on an existing task
        // Open the task details
        WebElement oldTask = driver.findElement(By.xpath("//span[contains(text(), 'Crio INTV Task Automation')]"));
        oldTask.click();
        Thread.sleep(2000);

        // Verify the title of the task is "Crio INTV Task Automation"
        String actualTaskTitle = driver.findElement(By.xpath("(//span[contains(text(), 'Crio INTV Task Automation')])[last()]")).getText();
        String expectedTaskTitle = "Crio INTV Task Automation";
        if (actualTaskTitle.contains(expectedTaskTitle)) {
            System.out.println("TestCase04: The title of the task is 'Crio INTV Task Automation': PASS");
        } else {
            System.out.println("TestCase04: The title of the task is not 'Crio INTV Task Automation': FAIL");
        }

        // Delete the task
        WebElement editButton = driver.findElement(By.xpath("//button[contains(@aria-label, 'Delete task')]"));
        editButton.click();
        Thread.sleep(2000);

        // Confirm the task deletion, by verifying "Task deleted" is displayed
        WebElement taskDeletedPopup = driver.findElement(By.xpath("//div[contains(text(), 'Task deleted')]"));

        if (taskDeletedPopup.isDisplayed() && taskDeletedPopup.getText().equals("Task deleted")) {
            System.out.println("TestCase04: Task deleted: PASS");
        } else {
            System.out.println("TestCase04: Task not deleted: FAIL");
        }
        Thread.sleep(2000);

        System.out.println("TestCase04: END\n");
    }
}
