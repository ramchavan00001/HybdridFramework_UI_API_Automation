package RamChavanTestAutomation.UI_API_HybdriFramework.integration;

import RamChavanTestAutomation.UI_API_HybdriFramework.base.TestBase;
import RamChavanTestAutomation.UI_API_HybdriFramework.pages.LoginPage;
import RamChavanTestAutomation.UI_API_HybdriFramework.pages.HomePage;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.ExtentTestManager;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.Log;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.UIUtils;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UserJourneyTests extends TestBase {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void initPages() {
        loginPage = new LoginPage();
        homePage = new HomePage();
    }

    @Test(groups = {"integration", "journey"})
    public void verifyUserBrowsingJourney() {
        // Step 1: Login
        loginPage.loginWithDefaultUser();
        Assert.assertEquals(homePage.getPageTitle(), "Products", "Login failed!");
        ExtentTestManager.log(Status.PASS, "Login successful.");

        // Step 2: Apply sort filter
        UIUtils.click(By.className("product_sort_container"));
        UIUtils.selectDropdownByValue(By.className("product_sort_container"), "lohi");
        String firstProduct = UIUtils.getText(By.className("inventory_item_name"));
        Assert.assertNotNull(firstProduct, "Sorting failed, product not visible!");
        ExtentTestManager.log(Status.PASS, "Sorting applied, first product: " + firstProduct);

        // Step 3: View product details
        UIUtils.click(By.xpath("//div[text()='Sauce Labs Backpack']"));
        String productDetail = UIUtils.getText(By.className("inventory_details_name"));
        Assert.assertEquals(productDetail, "Sauce Labs Backpack", "Product details page not opened!");
        ExtentTestManager.log(Status.PASS, "Product details page opened: " + productDetail);

        // Step 4: Logout
        UIUtils.click(By.id("react-burger-menu-btn"));
        UIUtils.click(By.id("logout_sidebar_link"));
        boolean isOnLoginPage = getDriver().getCurrentUrl().contains("saucedemo.com");
        Assert.assertTrue(isOnLoginPage, "Logout failed!");
        ExtentTestManager.log(Status.PASS, "User logged out successfully.");
        Log.info("Verified complete user journey.");
    }
}
