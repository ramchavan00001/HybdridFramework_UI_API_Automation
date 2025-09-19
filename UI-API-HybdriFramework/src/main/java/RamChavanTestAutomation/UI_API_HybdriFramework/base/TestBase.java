package RamChavanTestAutomation.UI_API_HybdriFramework.base;

import RamChavanTestAutomation.UI_API_HybdriFramework.resources.ConfigReader;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.Log;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.ExtentManager;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.ExtentTestManager;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class TestBase {

    protected WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        Log.info("=== Test Suite Started ===");
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        Log.startTestCase(method.getName());
        ExtentTestManager.startTest(method.getName());

        String testType = ConfigReader.get("testType", "UI"); // default UI

        if (testType.equalsIgnoreCase("UI")) {
            String browser = ConfigReader.get("ui.browser", "chrome");
            DriverFactory.initDriver(browser);
            driver = DriverFactory.getDriver();
            Log.info("Driver initialized for browser: " + browser);
            ExtentTestManager.log(Status.INFO, "Driver started with " + browser);

            String appUrl = ConfigReader.get("ui.baseUrl");
            driver.get(appUrl);
            Log.info("Navigated to: " + appUrl);

        } else if (testType.equalsIgnoreCase("API")) {
            initAPI();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result, Method method) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                ExtentTestManager.getTest().fail("Test Failed: " + result.getThrowable());
                Log.error("Test Failed: " + method.getName() + " - " + result.getThrowable());
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                ExtentTestManager.getTest().pass("Test Passed");
                Log.info("Test Passed: " + method.getName());
            } else if (result.getStatus() == ITestResult.SKIP) {
                ExtentTestManager.getTest().skip("Test Skipped");
                Log.warn("Test Skipped: " + method.getName());
            }

            if (driver != null) {
                DriverFactory.quitDriver();
                Log.info("Driver quit successfully");
            }

        } catch (Exception e) {
            Log.error("Error in tearDown: " + e.getMessage());
        } finally {
            ExtentManager.flushReports(); // ensure flush always happens
            Log.endTestCase(method.getName());
        }
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        ExtentManager.flushReports();
        Log.info("=== Test Suite Finished ===");
    }

    protected WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    private void initAPI() {
        String baseUrl = ConfigReader.get("api.baseUrl");
        if (baseUrl != null && !baseUrl.isEmpty()) {
            RestAssured.baseURI = baseUrl;
            Log.info("Initialized RestAssured with BaseURI: " + baseUrl);
            ExtentTestManager.log(Status.INFO, "API Base URI set: " + baseUrl);
        } else {
            throw new RuntimeException("API baseUrl not configured in config.properties");
        }
    }
}
