package RamChavanTestAutomation.UI_API_HybdriFramework.api;

import RamChavanTestAutomation.UI_API_HybdriFramework.base.APITestBase;
import RamChavanTestAutomation.UI_API_HybdriFramework.api.clients.AuthClient;
import RamChavanTestAutomation.UI_API_HybdriFramework.api.models.AuthRequest;
import RamChavanTestAutomation.UI_API_HybdriFramework.api.models.AuthResponse;
import RamChavanTestAutomation.UI_API_HybdriFramework.resources.ConfigReader;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.ExtentTestManager;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.Log;

import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthAPITests extends APITestBase {

    private final AuthClient authClient = new AuthClient();

    @Test(description = "Verify login with valid credentials returns a token")
    public void testValidLogin() {
        AuthRequest request = new AuthRequest(
                ConfigReader.get("api.validEmail"),
                ConfigReader.get("api.validPassword")
        );

        AuthResponse response = authClient.login(request);

        if (response.getError() != null) {
            ExtentTestManager.log(Status.FAIL, "Login failed with error: " + response.getError());
            Log.error("API returned error during valid login: " + response.getError());
            Assert.fail("API returned error: " + response.getError());
        }

        Assert.assertNotNull(response.getAccessToken(), "Token should not be null after login");
        ExtentTestManager.log(Status.PASS, "Login successful, token received: " + response.getAccessToken());
    }

    @Test(description = "Verify login with invalid credentials fails")
    public void testInvalidLogin() {
        AuthRequest request = new AuthRequest("invalid@reqres.in", "wrongpass");

        AuthResponse response = authClient.login(request);

        if (response.getError() != null) {
            ExtentTestManager.log(Status.PASS, "Invalid login correctly returned error: " + response.getError());
            return;
        }

        Assert.assertNull(response.getAccessToken(), "Token should be null for invalid login");
        ExtentTestManager.log(Status.PASS, "Invalid login correctly did not return a token");
    }
}
