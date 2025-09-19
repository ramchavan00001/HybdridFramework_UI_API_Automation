package RamChavanTestAutomation.UI_API_HybdriFramework.base;

//package com.yourcompany.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.Platform;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.net.URL;
import java.time.Duration;

import RamChavanTestAutomation.UI_API_HybdriFramework.resources.ConfigReader;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void initDriver(String browser) {
        if (tlDriver.get() != null) return;

        boolean gridEnabled = Boolean.parseBoolean(ConfigReader.get("grid.enabled"));
        String gridUrl = ConfigReader.get("grid.url");
        WebDriver driver = null;

        try {
            if (gridEnabled && gridUrl != null && gridUrl.trim().length() > 0) {
                MutableCapabilities caps;
                if (browser == null) browser = ConfigReader.get("grid.defaultBrowser");
                if (browser.equalsIgnoreCase("chrome")) {
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
                    caps = options;
                    caps.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
                    caps.setCapability("se:screenResolution", "1280x720");
                } else {
                    FirefoxOptions options = new FirefoxOptions();
                    caps = options;
                    caps.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
                }
                caps.setCapability("browserName", browser.toLowerCase());
                // set any other capabilities (e.g., video, name) if your grid supports them
                URL hub = new URL(gridUrl); // example http://localhost:4444
                driver = new RemoteWebDriver(hub, caps);
            } else {
                // fallback to local
                if (browser == null) browser = ConfigReader.get("ui.browser");
                if (browser.equalsIgnoreCase("chrome")) {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--start-maximized");
                    driver = new ChromeDriver(options);
                } else {
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions options = new FirefoxOptions();
                    driver = new FirefoxDriver(options);
                }
            }

            // common timeouts
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(ConfigReader.get("ui.implicitWait"))));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Integer.parseInt(ConfigReader.get("ui.pageLoadTimeout"))));

            tlDriver.set(driver);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize WebDriver: " + e.getMessage(), e);
        }
    }

    public static void quitDriver() {
        if (tlDriver.get() != null) {
            try {
                tlDriver.get().quit();
            } catch (Exception ignored) {}
            tlDriver.remove();
        }
    }
}
