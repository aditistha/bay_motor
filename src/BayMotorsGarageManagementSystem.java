import java.util.List;
import java.util.Scanner;

public class BayMotorsGarageManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        initializeSystem();
        mainMenu();
    }
    class UserNotFoundException extends GarageManagementException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
    private static void initializeSystem() {
        try {
            // Create initial users
            Manager manager = new Manager("M001", "John", "john@baymotors.com", "manager123");
            Mechanic mechanic = new Mechanic("ME001", "Aakriti", "aakriti@baymotors.com", "aakriti123");
            WalkInCustomer walkInCustomer = new WalkInCustomer("C001", "Aditi", "aditi@gmail.com", "aditi123");

            // Register initial users
            UserManagementService.getInstance().registerUser(manager);
            UserManagementService.getInstance().registerUser(mechanic);
            UserManagementService.getInstance().registerUser(walkInCustomer);
        } catch (GarageManagementException e) {
            System.out.println("Error initializing system: " + e.getMessage());
        }
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\n--- Bay Motors Garage Management System ---");
            System.out.println("1. Login");
            System.out.println("2. Register Customer");
            System.out.println("3. Register Vehicle");
            System.out.println("4. View Tasks");
            System.out.println("5. Allocate Task");
            System.out.println("6. Complete Task");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice();

            try {
                switch (choice) {
                    case 1:
                        loginMenu();
                        break;
                    case 2:
                        registerCustomerMenu();
                        break;
                    case 3:
                        registerVehicleMenu();
                        break;
                    case 4:
                        viewTasksMenu();
                        break;
                    case 5:
                        allocateTaskMenu();
                        break;
                    case 6:
                        completeTaskMenu();
                        break;
                    case 0:
                        System.out.println("Exiting Bay Motors Garage Management System. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (GarageManagementException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    private static int getUserChoice() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Clear buffer
            return -1;
        }
    }
    class InvalidCredentialsException extends GarageManagementException {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

    private static void loginMenu() throws InvalidCredentialsException, UserManagementService.InvalidCredentialsException {
        System.out.print("Enter Email: ");
        scanner.nextLine(); // Consume newline
        String email = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        currentUser = UserManagementService.getInstance().authenticateUser(email, password);
        System.out.println("Login successful. Welcome, " + currentUser.name);
    }

    private static void registerCustomerMenu() throws GarageManagementException {
        System.out.print("Enter Name: ");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        RegisteredCustomer newCustomer = new RegisteredCustomer (
                "C" + System.currentTimeMillis(),
                name,
                email,
                password
                );

        UserManagementService.getInstance().registerUser(newCustomer);
        System.out.println("Customer registered successfully. Customer ID: " + newCustomer.id);
    }

    private static void registerVehicleMenu() throws GarageManagementException {
        if (!(currentUser instanceof Customer)) {
            throw new GarageManagementException("Only customers can register vehicles");
        }

        Customer customer = (Customer) currentUser;

        System.out.print("Enter Registration Number: ");
        scanner.nextLine(); // Consume newline
        String regNumber = scanner.nextLine();

        System.out.print("Enter Make: ");
        String make = scanner.nextLine();

        System.out.print("Enter Model: ");
        String model = scanner.nextLine();

        System.out.print("Enter Year: ");
        int year = scanner.nextInt();

        Vehicle newVehicle = new Vehicle(regNumber, make, model, year, customer);
        customer.addVehicle(newVehicle);

        System.out.println("Vehicle registered successfully.");
    }

    private static void viewTasksMenu() throws GarageManagementException {
        if (!(currentUser instanceof Mechanic)) {
            throw new GarageManagementException("Only mechanics can view tasks");
        }

        Mechanic mechanic = (Mechanic) currentUser;
        List<Task> tasks = mechanic.getTasks();

        if (tasks.isEmpty()) {
            System.out.println("No tasks assigned.");
            return;
        }

        System.out.println("Current Tasks:");
        for (Task task : tasks) {
            System.out.println("Vehicle: " + task.getVehicle().getRegistrationNumber());
        }
    }

    private static void allocateTaskMenu() throws GarageManagementException {
        if (!(currentUser instanceof Manager)) {
            throw new GarageManagementException("Only managers can allocate tasks");
        }

        Manager manager = (Manager) currentUser;

        System.out.println("Select Mechanic:");
        // In a real system, you'd list available mechanics
        Mechanic mechanic = (Mechanic) UserManagementService.getInstance()
                .authenticateUser("mike@baymotors.com", "mechanic123");

        System.out.println("Select Vehicle:");
        // In a real system, you'd list available vehicles
        Vehicle vehicle = new Vehicle("ABC123", "Toyota", "Corolla", 2020,
                new WalkInCustomer("C002", "Sample Customer", "stha123", "stha@gmail.com"));

        System.out.print("Enter Task Description: ");
        scanner.nextLine(); // Consume newline
        String description = scanner.nextLine();

        manager.allocateTask(mechanic, vehicle, description);
        System.out.println("Task allocated successfully.");
    }

    private static void completeTaskMenu() throws GarageManagementException {
        if (!(currentUser instanceof Mechanic)) {
            throw new GarageManagementException("Only mechanics can complete tasks");
        }

        Mechanic mechanic = (Mechanic) currentUser;
        List<Task> tasks = mechanic.getTasks();

        if (tasks.isEmpty()) {
            System.out.println("No tasks to complete.");
            return;
        }

        // Select first task for simplicity
        Task selectedTask = tasks.get(0);

        System.out.print("Enter Completion Details: ");
        scanner.nextLine(); // Consume newline
        String completionDetails = scanner.nextLine();

        mechanic.completeTask(selectedTask, completionDetails);
        System.out.println("Task completed successfully.");
    }
}
