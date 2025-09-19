package RamChavanTestAutomation.UI_API_HybdriFramework.utils;

import com.aventstack.extentreports.Status;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;

import java.util.Map;

public class APIUtils {

    /**
     * Common method to build request with optional headers/body
     */
    private static RequestSpecification buildRequest(Map<String, String> headers, Object body) {
        RequestSpecification request = RestAssured.given().contentType(ContentType.JSON);

        if (headers != null && !headers.isEmpty()) {
            request.headers(headers);
            Log.info("Headers: " + headers);
            ExtentTestManager.log(Status.INFO, "Headers: " + headers.toString());
        }

        if (body != null) {
            request.body(body);
            Log.info("Request Body: " + body);
            ExtentTestManager.log(Status.INFO, "Request Body: " + body.toString());
        }

        return request;
    }

    /**
     * GET request
     */
    public static Response get(String endpoint, Map<String, String> headers) {
        Log.info("Sending GET request to: " + endpoint);
        ExtentTestManager.log(Status.INFO, "GET Request: " + endpoint);

        Response response = buildRequest(headers, null).when().get(endpoint);

        logResponse(response);
        return response;
    }

    /**
     * POST request
     */
    public static Response post(String endpoint, Map<String, String> headers, Object body) {
        Log.info("Sending POST request to: " + endpoint);
        ExtentTestManager.log(Status.INFO, "POST Request: " + endpoint);

        Response response = buildRequest(headers, body).when().post(endpoint);

        logResponse(response);
        return response;
    }

    /**
     * PUT request
     */
    public static Response put(String endpoint, Map<String, String> headers, Object body) {
        Log.info("Sending PUT request to: " + endpoint);
        ExtentTestManager.log(Status.INFO, "PUT Request: " + endpoint);

        Response response = buildRequest(headers, body).when().put(endpoint);

        logResponse(response);
        return response;
    }

    /**
     * DELETE request
     */
    public static Response delete(String endpoint, Map<String, String> headers) {
        Log.info("Sending DELETE request to: " + endpoint);
        ExtentTestManager.log(Status.INFO, "DELETE Request: " + endpoint);

        Response response = buildRequest(headers, null).when().delete(endpoint);

        logResponse(response);
        return response;
    }

    /**
     * Log API Response in both Console, Log4j, and ExtentReports
     */
    private static void logResponse(Response response) {
        String responseLog = "Response Code: " + response.getStatusCode()
                + " | Response Body: " + response.getBody().asPrettyString();

        Log.info(responseLog);
        ExtentTestManager.log(Status.INFO, responseLog);
    }
}
