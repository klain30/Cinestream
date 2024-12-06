public class LoginManager {
    public boolean isValidLogin(String username, String password) {
        // Implement logic to verify user credentials
        // For simplicity, we're just using a hardcoded check here
        return username.equals("admin") && password.equals("admin");
    }
}
