package RamChavanTestAutomation.UI_API_HybdriFramework.resources;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties props = new Properties();

    static {
        try (InputStream input = new FileInputStream("src/test/resources/config.properties")) {
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    // Basic getter
    public static String get(String key) {
        return props.getProperty(key);
    }

    // Overloaded getter with default value (safe fallback)
    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
