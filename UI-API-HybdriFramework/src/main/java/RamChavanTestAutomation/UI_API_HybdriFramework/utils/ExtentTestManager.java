package RamChavanTestAutomation.UI_API_HybdriFramework.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ExtentTestManager {
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    /**
     * Start test with only a name.
     */
    public static synchronized void startTest(String testName) {
        ExtentTest test = ExtentManager.getReporter().createTest(testName);
        extentTest.set(test);
    }

    /**
     * Overloaded method to start test with a name and description.
     */
    public static synchronized void startTest(String testName, String description) {
        ExtentTest test = ExtentManager.getReporter().createTest(testName, description);
        extentTest.set(test);
    }

    /**
     * Get the current ExtentTest instance.
     */
    public static synchronized ExtentTest getTest() {
        return extentTest.get();
    }

    /**
     * Log a message to the current test.
     */
    public static synchronized void log(Status status, String message) {
        getTest().log(status, message);
    }
}
