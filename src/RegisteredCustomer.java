import java.util.ArrayList;
import java.util.List;
import java.util.*;
class RegisteredCustomer extends Customer {
    private final boolean isRegistered;
    private List<String> preferences;

    public RegisteredCustomer(String id, String name, String password, String email) {
        super(id, name, password, email);
        this.preferences = new ArrayList<>();
        this.isRegistered = true;
    }
    @Override
    public String getUserType() {
        return "REGISTERED_CUSTOMER";
    }
    public void addPreference(String preference) {
        preferences.add(preference);
    }

    public void receiveOffer(String offer) {
        System.out.println("Special Offer for " + name + ": " + offer);
    }
}
