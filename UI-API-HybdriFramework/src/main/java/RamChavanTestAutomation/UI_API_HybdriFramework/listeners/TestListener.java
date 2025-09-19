package RamChavanTestAutomation.UI_API_HybdriFramework.listeners;

import RamChavanTestAutomation.UI_API_HybdriFramework.base.DriverFactory;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.ExtentManager;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.ExtentTestManager;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.Log;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestListener implements ITestListener, ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        Log.info("===== Suite Started: " + suite.getName() + " =====");
        ExtentManager.createInstance(); // init extent
    }

    @Override
    public void onFinish(ISuite suite) {
        Log.info("===== Suite Finished: " + suite.getName() + " =====");
        ExtentManager.flushReports();   // flush extent
    }

    @Override
    public void onTestStart(ITestResult result) {
        Log.startTestCase(result.getMethod().getMethodName());
        ExtentTestManager.startTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Log.info("Test Passed: " + result.getMethod().getMethodName());
        ExtentTestManager.getTest().log(Status.PASS, "Test passed successfully");

        // Capture screenshot on success
        captureScreenshot(result, "PASS");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Log.error("Test Failed: " + result.getMethod().getMethodName());
        ExtentTestManager.getTest().log(Status.FAIL, "Test failed: " + result.getThrowable());

        // Capture screenshot on failure
        captureScreenshot(result, "FAIL");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Log.warn("Test Skipped: " + result.getMethod().getMethodName());
        ExtentTestManager.getTest().log(Status.SKIP, "Test skipped: " + result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) { }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }

    private void captureScreenshot(ITestResult result, String status) {
        try {
            if (DriverFactory.getDriver() != null) {
                TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
                byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);

                String screenshotPath = "reports/screenshots/" 
                        + result.getMethod().getMethodName() + "_" + status + ".png";

                File screenshotFile = new File(screenshotPath);
                screenshotFile.getParentFile().mkdirs();
                Files.write(Paths.get(screenshotPath), screenshot);

                ExtentTestManager.getTest().addScreenCaptureFromPath(screenshotPath);
            }
        } catch (IOException e) {
            Log.error("Failed to capture screenshot for " 
                      + result.getMethod().getMethodName() + ": " + e.getMessage());
        }
    }
}
