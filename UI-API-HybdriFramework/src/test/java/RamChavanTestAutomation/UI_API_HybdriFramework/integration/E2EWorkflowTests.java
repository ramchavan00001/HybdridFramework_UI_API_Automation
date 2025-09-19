package RamChavanTestAutomation.UI_API_HybdriFramework.integration;

import RamChavanTestAutomation.UI_API_HybdriFramework.base.TestBase;
import RamChavanTestAutomation.UI_API_HybdriFramework.pages.LoginPage;
import RamChavanTestAutomation.UI_API_HybdriFramework.pages.HomePage;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.ExtentTestManager;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.Log;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.UIUtils;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class E2EWorkflowTests extends TestBase {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void initPages() {
        loginPage = new LoginPage();
        homePage = new HomePage();
    }

    @Test(groups = {"integration", "e2e"})
    public void verifyEndToEndPurchaseWorkflow() {
        try {
            // Step 1: Login
            loginPage.loginWithDefaultUser();
            Assert.assertEquals(homePage.getPageTitle(), "Products", "Login failed!");
            ExtentTestManager.log(Status.PASS, "Login successful, Products page displayed.");

            // Step 2: Add product to cart
            UIUtils.click(By.id("add-to-cart-sauce-labs-backpack"));
            UIUtils.click(By.className("shopping_cart_link"));

            boolean cartHasItem = getDriver().findElements(By.className("cart_item")).size() > 0;
            Assert.assertTrue(cartHasItem, "Cart is empty after adding product!");
            ExtentTestManager.log(Status.PASS, "Product added to cart successfully.");

            // Step 3: Checkout process
            UIUtils.click(By.id("checkout"));
            UIUtils.sendKeys(By.id("first-name"), "Ram");
            UIUtils.sendKeys(By.id("last-name"), "Chavan");
            UIUtils.sendKeys(By.id("postal-code"), "411001");
            UIUtils.click(By.id("continue"));

            // ✅ Add a wait before clicking finish
            WebElement finishBtn = UIUtils.waitForElement(By.id("finish"), 10);
            finishBtn.click();

            // ✅ Wait for confirmation
            WebElement confirmationElement = UIUtils.waitForElement(By.className("complete-header"), 10);
            String confirmation = confirmationElement.getText().trim();

            Assert.assertTrue(confirmation.toUpperCase().contains("THANK YOU"),
                    "Order not completed successfully! Found text: " + confirmation);

            ExtentTestManager.log(Status.PASS, "End-to-End purchase workflow completed successfully.");
            Log.info("Verified complete E2E workflow.");

        } catch (Exception e) {
            ExtentTestManager.log(Status.FAIL, "E2E workflow failed: " + e.getMessage());
            Log.error("Error during E2E workflow execution", e);
            Assert.fail("E2E workflow execution failed!", e);
        }
    }
}
