package RamChavanTestAutomation.UI_API_HybdriFramework.pages;

import org.openqa.selenium.By;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.UIUtils;

public class HomePage {

    // Locators
    private final By productsTitle = By.cssSelector("span.title");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    // Actions
    public String getPageTitle() {
        return UIUtils.getText(productsTitle);
    }

    public void openMenu() {
        UIUtils.click(menuButton);
    }

    public void logout() {
        openMenu();
        UIUtils.click(logoutLink);
    }
}
