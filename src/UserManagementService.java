import java.util.HashMap;
import java.util.Map;


class UserManagementService {
    private static UserManagementService instance;
    private Map<String, User> users;

    private UserManagementService() {
        users = new HashMap<>();
    }

    public static UserManagementService getInstance() {
        if (instance == null) {
            instance = new UserManagementService();
        }
        return instance;
    }

    public void registerUser(User user) throws GarageManagementException {
        String emailKey = user.email.toLowerCase();
        if (users.containsKey(user.email)) {
            throw new GarageManagementException("User with this email already exists");
        }
        users.put(emailKey, user);
    }
    class InvalidCredentialsException extends GarageManagementException {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }
    public User authenticateUser(String email, String password) throws InvalidCredentialsException {
        User user = users.get(email.toLowerCase());
        if (user == null || !user.password.equals(password)) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return user;
    }

}
