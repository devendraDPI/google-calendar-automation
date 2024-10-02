package xgooglecalendar;

public class App {
    public void getGreeting() throws InterruptedException {
        // Removes unnecessary warnings from console
        System.setProperty("java.util.logging.config.file", "logging.properties");

        TestCases tests = new TestCases(); // Initialize your test class

        // TODO: call your test case functions one after other here
        // START Tests
        tests.testCase01();

        // END Tests
        tests.endTest(); // End your test by clearing connections and closing browser
    }

    public static void main(String[] args) throws InterruptedException {
        new App().getGreeting();
    }
}
