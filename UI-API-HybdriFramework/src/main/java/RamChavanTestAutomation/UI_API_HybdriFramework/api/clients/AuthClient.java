package RamChavanTestAutomation.UI_API_HybdriFramework.api.clients;

import RamChavanTestAutomation.UI_API_HybdriFramework.api.models.AuthRequest;
import RamChavanTestAutomation.UI_API_HybdriFramework.api.models.AuthResponse;
import RamChavanTestAutomation.UI_API_HybdriFramework.resources.ConfigReader;
import io.restassured.response.Response;
import RamChavanTestAutomation.UI_API_HybdriFramework.utils.Log;
import static io.restassured.RestAssured.given;

public class AuthClient {

    private final String baseUrl;
    private final String apiKey;

    public AuthClient() {
        this.baseUrl = ConfigReader.get("api.baseUrl");
        this.apiKey = ConfigReader.get("api.key"); // 👈 Make sure api.key is present in config.properties
    }

//    public AuthResponse login(AuthRequest request) {
//        return given()
//                .baseUri(baseUrl)
//                .header("Content-Type", "application/json")
//                .header("x-api-key", apiKey) // 👈 API key is always included
//                .body(request)
//                .when()
//                .post("/auth/login")
//                .then()
//                .extract()
//                .as(AuthResponse.class);
//    }
    public AuthResponse login(AuthRequest request) {
        Response response = given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .header("x-api-key", apiKey) // include API key
                .body(request)
                .when()
                .post("/auth/login");

        // ✅ Log response for debugging
        Log.info("Login API Response Status: " + response.getStatusCode());
        Log.info("Login API Response Body: " + response.asString());

        // ✅ Ensure we got JSON
        String contentType = response.getContentType();
        if (contentType == null || !contentType.contains("application/json")) {
            throw new IllegalStateException("Expected JSON but got: " + contentType +
                    "\nResponse body: " + response.asString());
        }

        // ✅ Deserialize safely
        try {
            return response.as(AuthResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AuthResponse. Body was: " + response.asString(), e);
        }
    }

}
