package RamChavanTestAutomation.UI_API_HybdriFramework.base;

import RamChavanTestAutomation.UI_API_HybdriFramework.utils.Log;
import RamChavanTestAutomation.UI_API_HybdriFramework.resources.ConfigReader;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.ExtentTestManager;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class APITestBase extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void setUpAPI() {
        String baseUrl = ConfigReader.get("api.baseUrl");
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new RuntimeException("API baseUrl missing in config.properties");
        }
        RestAssured.baseURI = baseUrl;
        Log.info("API Base URI set to: " + baseUrl);
        ExtentTestManager.log(Status.INFO, "API Base URI: " + baseUrl);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownAPI() {
        RestAssured.reset();
        Log.info("API test teardown completed");
        ExtentTestManager.log(Status.INFO, "API teardown completed");
    }
}
