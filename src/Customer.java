import java.util.ArrayList;
import java.util.List;

abstract class Customer extends User {
    protected String id;
    protected String name;
    protected String contact;
    protected List<Vehicle> vehicles;

    public Customer(String id, String name, String email, String password) {
        super(id, name, email, password);
        this.vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }
    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}
