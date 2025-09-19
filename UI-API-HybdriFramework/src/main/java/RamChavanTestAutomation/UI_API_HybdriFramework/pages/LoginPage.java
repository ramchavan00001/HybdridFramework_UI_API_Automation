package RamChavanTestAutomation.UI_API_HybdriFramework.pages;

import org.openqa.selenium.By;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.UIUtils;
import RamChavanTestAutomation.UI_API_HybdriFramework.resources.ConfigReader;

public class LoginPage {

    // Locators
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");

    // Actions
    public void login(String username, String password) {
        UIUtils.sendKeys(usernameField, username);
        UIUtils.sendKeys(passwordField, password);
        UIUtils.click(loginButton);
    }

    // Login with default credentials from config.properties
    public void loginWithDefaultUser() {
        String username = ConfigReader.get("ui.username");
        String password = ConfigReader.get("ui.password");
        login(username, password);
    }

    public String getErrorMessage() {
        return UIUtils.getText(errorMessage);
    }
}
