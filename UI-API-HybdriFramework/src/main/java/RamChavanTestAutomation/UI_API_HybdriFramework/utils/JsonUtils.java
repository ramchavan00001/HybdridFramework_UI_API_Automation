package RamChavanTestAutomation.UI_API_HybdriFramework.utils;

import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Convert Java object to JSON string
     */
    public static String toJson(Object obj) {
        try {
            String json = mapper.writeValueAsString(obj);
            Log.info("Serialized Object to JSON: " + json);
            ExtentTestManager.log(Status.INFO, "Serialized Object to JSON: " + json);
            return json;
        } catch (JsonProcessingException e) {
            Log.error("Error serializing object to JSON", e);
            ExtentTestManager.log(Status.FAIL, "Error serializing object to JSON: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Convert JSON string to Java object
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            T obj = mapper.readValue(json, clazz);
            Log.info("Deserialized JSON to Object: " + clazz.getSimpleName());
            ExtentTestManager.log(Status.INFO, "Deserialized JSON to Object: " + clazz.getSimpleName());
            return obj;
        } catch (Exception e) {
            Log.error("Error deserializing JSON to object", e);
            ExtentTestManager.log(Status.FAIL, "Error deserializing JSON: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Parse JSON string into JsonNode (tree structure)
     */
    public static JsonNode parse(String json) {
        try {
            JsonNode node = mapper.readTree(json);
            Log.info("Parsed JSON string to JsonNode");
            ExtentTestManager.log(Status.INFO, "Parsed JSON string to JsonNode");
            return node;
        } catch (Exception e) {
            Log.error("Error parsing JSON string", e);
            ExtentTestManager.log(Status.FAIL, "Error parsing JSON string: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Extract value by JSON path (dot notation)
     */
    public static String getValue(String json, String path) {
        try {
            JsonNode node = parse(json);
            String[] keys = path.split("\\.");
            for (String key : keys) {
                node = node.path(key);
            }
            String value = node.asText();
            Log.info("Extracted value for path '" + path + "': " + value);
            ExtentTestManager.log(Status.INFO, "Extracted value for path '" + path + "': " + value);
            return value;
        } catch (Exception e) {
            Log.error("Error extracting value from JSON", e);
            ExtentTestManager.log(Status.FAIL, "Error extracting value from JSON: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
