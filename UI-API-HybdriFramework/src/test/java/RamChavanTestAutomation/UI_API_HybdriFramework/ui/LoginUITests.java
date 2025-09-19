package RamChavanTestAutomation.UI_API_HybdriFramework.ui;

import RamChavanTestAutomation.UI_API_HybdriFramework.base.TestBase;
import RamChavanTestAutomation.UI_API_HybdriFramework.pages.LoginPage;
import RamChavanTestAutomation.UI_API_HybdriFramework.pages.HomePage;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.ExtentTestManager;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.Log;

import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginUITests extends TestBase {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void initPages() {
        loginPage = new LoginPage();
        homePage = new HomePage();
    }

    @Test(groups = {"ui", "smoke"})
    public void verifyUserCanLoginWithValidCredentials() {
        loginPage.loginWithDefaultUser();
        String title = homePage.getPageTitle();

        Assert.assertEquals(title, "Products", "Login failed or wrong landing page!");
        ExtentTestManager.log(Status.PASS, "Login successful, user landed on Products page.");
        Log.info("Verified successful login with valid credentials.");
    }

    @Test(groups = {"ui", "regression"})
    public void verifyUserCannotLoginWithInvalidCredentials() {
        loginPage.login("invalid_user", "wrong_pass");
        String errorMsg = loginPage.getErrorMessage();

        Assert.assertTrue(errorMsg.contains("Epic sadface"), "Error message not displayed for invalid login!");
        ExtentTestManager.log(Status.PASS, "Proper error message displayed for invalid credentials: " + errorMsg);
        Log.info("Verified invalid login error message.");
    }
}
