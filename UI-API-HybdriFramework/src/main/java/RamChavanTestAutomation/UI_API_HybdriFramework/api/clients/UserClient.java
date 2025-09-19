package RamChavanTestAutomation.UI_API_HybdriFramework.api.clients;

import RamChavanTestAutomation.UI_API_HybdriFramework.api.models.AuthRequest;
import RamChavanTestAutomation.UI_API_HybdriFramework.api.models.AuthResponse;
import RamChavanTestAutomation.UI_API_HybdriFramework.api.models.User;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient {

    private static final String BASE_PATH = "/users";  // Assuming API endpoints are /users/*

    // 🔹 Login endpoint
    public Response login(AuthRequest authRequest) {
        return given()
                .contentType("application/json")
                .body(authRequest)
                .when()
                .post(BASE_PATH + "/login");
    }

    // 🔹 Login + Deserialize token response
    public AuthResponse loginAndGetToken(AuthRequest authRequest) {
        return login(authRequest)
                .then()
                .statusCode(200)
                .extract()
                .as(AuthResponse.class);
    }

    // 🔹 Create a new user
    public Response createUser(User user) {
        return given()
                .contentType("application/json")
                .body(user)
                .when()
                .post(BASE_PATH + "/register");
    }

    // 🔹 Get a user by username
    public Response getUser(String username) {
        return given()
                .when()
                .get(BASE_PATH + "/" + username);
    }

    // 🔹 Update a user
    public Response updateUser(String username, User user) {
        return given()
                .contentType("application/json")
                .body(user)
                .when()
                .put(BASE_PATH + "/" + username);
    }

    // 🔹 Delete a user
    public Response deleteUser(String username) {
        return given()
                .when()
                .delete(BASE_PATH + "/" + username);
    }
}
