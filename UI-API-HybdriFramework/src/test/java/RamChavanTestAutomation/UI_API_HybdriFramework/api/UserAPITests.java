package RamChavanTestAutomation.UI_API_HybdriFramework.api;

import RamChavanTestAutomation.UI_API_HybdriFramework.base.APITestBase;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.ExtentTestManager;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.Log;

import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserAPITests extends APITestBase {

    @Test(description = "Verify user list API returns users on page 2")
    public void testGetUsers() {
        Response response = RestAssured.given()
                .when()
                .get("/api/users?page=2")
                .then()
                .extract().response();

        try {
            Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
            Assert.assertTrue(response.jsonPath().getList("data").size() > 0, "Users list should not be empty");

            ExtentTestManager.log(Status.PASS,
                    "User API returned 200 with " + response.jsonPath().getList("data").size() + " users");
        } catch (AssertionError e) {
            ExtentTestManager.log(Status.FAIL, "User API validation failed: " + response.asString());
            Log.error("Assertion failed in testGetUsers", e);
            throw e;
        }
    }
}
