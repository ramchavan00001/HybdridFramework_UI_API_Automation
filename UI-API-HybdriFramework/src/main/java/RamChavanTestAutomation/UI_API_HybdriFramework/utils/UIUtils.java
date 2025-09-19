package RamChavanTestAutomation.UI_API_HybdriFramework.utils;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import RamChavanTestAutomation.UI_API_HybdriFramework.base.DriverFactory;

/**
 * UIUtils - Reusable Selenium utilities with logging, Extent reporting, and screenshots.
 * Keeps UI tests clean and avoids duplication.
 */
public class UIUtils {

    private static final int DEFAULT_WAIT = 20;

    // 🔹 Central driver accessor (always fetch from DriverFactory ThreadLocal)
    private static WebDriver driver() {
        return DriverFactory.getDriver();
    }

    // ✅ Click
    public static void click(By locator) {
        try {
            new WebDriverWait(driver(), Duration.ofSeconds(DEFAULT_WAIT))
                    .until(ExpectedConditions.elementToBeClickable(locator))
                    .click();
            logPass("Clicked on element: " + locator);
        } catch (Exception e) {
            handleError("Failed to click on element: " + locator, "click_error", e);
            throw e;
        }
    }

    // ✅ Send Keys
    public static void sendKeys(By locator, String text) {
        try {
            WebElement element = new WebDriverWait(driver(), Duration.ofSeconds(DEFAULT_WAIT))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.clear();
            element.sendKeys(text);
            logPass("Entered text '" + text + "' into element: " + locator);
        } catch (Exception e) {
            handleError("Failed to enter text into element: " + locator, "sendKeys_error", e);
            throw e;
        }
    }

    // ✅ Get Text
    public static String getText(By locator) {
        try {
            String text = new WebDriverWait(driver(), Duration.ofSeconds(DEFAULT_WAIT))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator))
                    .getText();
            logPass("Fetched text '" + text + "' from element: " + locator);
            return text;
        } catch (Exception e) {
            handleError("Failed to fetch text from element: " + locator, "getText_error", e);
            throw e;
        }
    }

    // ✅ Wait for Visibility
    public static void waitForVisibility(By locator) {
        try {
            new WebDriverWait(driver(), Duration.ofSeconds(DEFAULT_WAIT))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            logPass("Element is visible: " + locator);
        } catch (Exception e) {
            handleError("Element not visible: " + locator, "waitForVisibility_error", e);
            throw e;
        }
    }

    // ✅ Custom Wait that RETURNS WebElement
    public static WebElement waitForElement(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(timeoutInSeconds));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logPass("Element located successfully: " + locator);
            return element;
        } catch (Exception e) {
            handleError("Failed to locate element: " + locator, "waitForElement_error", e);
            throw e;
        }
    }

    // ✅ Dropdown utilities
    public static void selectDropdownByValue(By locator, String value) {
        try {
            Select dropdown = new Select(driver().findElement(locator));
            dropdown.selectByValue(value);
            logPass("Selected value '" + value + "' from dropdown: " + locator);
        } catch (Exception e) {
            handleError("Failed to select value from dropdown: " + locator, "dropdown_error", e);
            throw e;
        }
    }

    public static void selectDropdownByVisibleText(By locator, String text) {
        try {
            Select dropdown = new Select(driver().findElement(locator));
            dropdown.selectByVisibleText(text);
            logPass("Selected visible text '" + text + "' from dropdown: " + locator);
        } catch (Exception e) {
            handleError("Failed to select visible text from dropdown: " + locator, "dropdown_error", e);
            throw e;
        }
    }

    public static void selectDropdownByIndex(By locator, int index) {
        try {
            Select dropdown = new Select(driver().findElement(locator));
            dropdown.selectByIndex(index);
            logPass("Selected index '" + index + "' from dropdown: " + locator);
        } catch (Exception e) {
            handleError("Failed to select index from dropdown: " + locator, "dropdown_error", e);
            throw e;
        }
    }

    // ✅ Screenshot
    public static String takeScreenshot(String name) {
        try {
            File srcFile = ((TakesScreenshot) driver()).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String filePath = System.getProperty("user.dir") + "/screenshots/" + name + "_" + timestamp + ".png";
            File destFile = new File(filePath);
            org.openqa.selenium.io.FileHandler.copy(srcFile, destFile);
            Log.info("Screenshot captured: " + filePath);
            return filePath;
        } catch (Exception e) {
            Log.error("Failed to capture screenshot: " + name, e);
            return null;
        }
    }

    // 🔹 Helper for success logs
    private static void logPass(String message) {
        Log.info(message);
        ExtentTestManager.log(Status.PASS, message);
    }

    // 🔹 Centralized error handler
    private static void handleError(String message, String screenshotName, Exception e) {
        Log.error(message, e);
        String screenshot = takeScreenshot(screenshotName);
        if (screenshot != null) {
            ExtentTestManager.getTest().addScreenCaptureFromPath(screenshot);
        }
        ExtentTestManager.log(Status.FAIL, message);
    }
}
