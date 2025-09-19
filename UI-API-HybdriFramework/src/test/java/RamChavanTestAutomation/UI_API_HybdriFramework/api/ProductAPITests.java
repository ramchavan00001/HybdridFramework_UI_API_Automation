package RamChavanTestAutomation.UI_API_HybdriFramework.api;

import RamChavanTestAutomation.UI_API_HybdriFramework.base.APITestBase;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.ExtentTestManager;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.Log;

import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductAPITests extends APITestBase {

    @Test(description = "Verify that product list API returns 200 and has products")
    public void testGetProducts() {
        Response response = RestAssured.given()
                .when()
                .get("/api/unknown") // ReqRes provides 'unknown' as resource list
                .then()
                .extract().response();

        try {
            Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
            Assert.assertTrue(response.jsonPath().getList("data").size() > 0, "Product list should not be empty");

            ExtentTestManager.log(Status.PASS,
                    "Product API returned 200 with " + response.jsonPath().getList("data").size() + " products");
        } catch (AssertionError e) {
            ExtentTestManager.log(Status.FAIL, "Product API validation failed: " + response.asString());
            Log.error("Assertion failed in testGetProducts", e);
            throw e;
        }
    }
}
