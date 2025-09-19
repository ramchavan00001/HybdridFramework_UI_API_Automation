package RamChavanTestAutomation.UI_API_HybdriFramework.api.models;

public class AuthRequest {
    private String username;
    private String password;

    public AuthRequest() {
        // Default constructor
    }

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthRequest{" +
               "username='" + username + '\'' +
               ", password='" + password + '\'' +
               '}';
    }
}
