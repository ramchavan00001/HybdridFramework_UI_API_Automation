package RamChavanTestAutomation.UI_API_HybdriFramework.ui;

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

public class ProductUITests extends TestBase {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void initPages() {
        loginPage = new LoginPage();
        homePage = new HomePage();
    }

    @Test(groups = {"ui", "regression"})
    public void verifyProductsAreDisplayed() {
        loginPage.loginWithDefaultUser();
        String title = homePage.getPageTitle();

        Assert.assertEquals(title, "Products", "User not on Products page after login!");
        ExtentTestManager.log(Status.PASS, "Products page loaded successfully.");
        Log.info("Verified Products page loaded.");

        // Verify at least one product (using UIUtils instead of raw driver)
        UIUtils.waitForVisibility(By.className("inventory_item"));
        boolean productVisible = getDriver().findElements(By.className("inventory_item")).size() > 0;

        Assert.assertTrue(productVisible, "No products displayed on Products page!");
        ExtentTestManager.log(Status.PASS, "Products are displayed on Products page.");
        Log.info("Verified products are listed on the page.");
    }
}
