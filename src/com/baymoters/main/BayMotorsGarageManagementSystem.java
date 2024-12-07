package com.baymoters.main;

import com.baymoters.customer.Customer;
import com.baymoters.customer.RegisteredCustomer;
import com.baymoters.customer.WalkInCustomer;
import com.baymoters.exception.GarageManagementException;
import com.baymoters.services.NotificationService;
import com.baymoters.services.UserManagementService;
import com.baymoters.task.Task;
import com.baymoters.users.Manager;
import com.baymoters.users.Mechanic;
import com.baymoters.users.User;
import com.baymoters.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BayMotorsGarageManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;
    private static List<Manager> managers = new ArrayList<>();
    private static List<Mechanic> mechanics = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    public static void main(String[] args) {
        initializeSystem();
        mainMenu();
    }
    private static Manager currentManager = null;
    private static Mechanic currentMechanic = null;
    private static Customer currentCustomer = null;
    private static void initializeSystem() {
        try {
            // Create initial users
            Manager manager = new Manager("M001", "Anita", "anita@gmail.com", "anita123");
            Mechanic mechanic = new Mechanic("ME001", "Aakriti", "aakriti@gmail.com", "aakriti123");
            WalkInCustomer walkInCustomer = new WalkInCustomer("C001", "aditi", "aditi@gmail.com", "aditi123");
            RegisteredCustomer regCustomer = new RegisteredCustomer("C002", "Rajesh", "rajesh@gmail.com", "rajesh123");
            managers.add(manager);
            mechanics.add(mechanic);
            customers.add(walkInCustomer);

            // Register initial users
            UserManagementService.getInstance().registerUser(manager);
            UserManagementService.getInstance().registerUser(mechanic);
            UserManagementService.getInstance().registerUser(walkInCustomer);

        } catch (GarageManagementException e) {
            System.out.println("Error initializing system: " + e.getMessage());
        }
    }
   //Main menu
    private static void mainMenu() {
        while (true) {
            System.out.println("\n--- Bay Motors Garage Management System ---");
            System.out.println("\n--- Login ---");
            System.out.println("1. Manager Login");
            System.out.println("2. Mechanic Login");
            System.out.println("3. Customer Login");
            System.out.print("Enter login type: ");


            int loginType = getUserChoice();

            System.out.print("Enter Email: ");
            scanner.nextLine(); // Consume newline
            String email = scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

                switch (loginType) {
                    case 1:
                        loginManager(email, password);
                        break;
                    case 2:
                        loginMechanic(email, password);
                        break;
                    case 3:
                        loginCustomer(email, password);
                        break;
                    default:
                        System.out.println("Invalid login type.");
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

    //Login manager mechanic customer

    private static void loginMenu() {
        System.out.println("\n--- Login ---");
        System.out.println("1. Manager Login");
        System.out.println("2. Mechanic Login");
        System.out.println("3. Customer Login");
        System.out.print("Enter login type: ");

        int loginType = getUserChoice();

        System.out.print("Enter Email: ");
        scanner.nextLine(); // Consume newline
        String email = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        switch (loginType) {
            case 1:
                loginManager(email, password);
                break;
            case 2:
                loginMechanic(email, password);
                break;
            case 3:
                loginCustomer(email, password);
                break;
            default:
                System.out.println("Invalid login type.");
        }
    }

    //com.baymoters.users.Manager

    private static void loginManager(String email, String password) {
        for (Manager manager : managers) {
            if (manager.email.equals(email) && manager.password.equals(password)) {
                currentManager = manager;
                currentUser = manager; // Set the currentUser to the manager
                System.out.println("com.baymoters.users.Manager login successful: " + manager.name);
                try {
                    managerMenu(); // Show manager menu after successful login
                } catch (GarageManagementException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                return;
            }
        }
        System.out.println("Login failed. Invalid credentials.");
    }

    private static void managerMenu() throws GarageManagementException {
        if (!(currentUser instanceof Manager)) {
            throw new GarageManagementException("Only managers can access this menu.");
        }

        Manager manager = (Manager) currentUser;

        while (true) {
            System.out.println("\n--- com.baymoters.users.Manager Menu ---");
            System.out.println("1. View Registered Customers");
            System.out.println("2. Register com.baymoters.customer.Customer");
            System.out.println("3. Allocate com.baymoters.task.Task");
            System.out.println("4. View com.baymoters.vehicles.Vehicle Details");
            System.out.println("5. Send Notification");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    viewRegisteredCustomers();
                    break;
                case 2:
                    registerCustomerMenu();
                    break;
                case 3:
                    allocateTask(manager);
                    break;
                case 4:
                    viewVehicleDetails();
                    break;
                case 5:
                    sendNotification();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void viewRegisteredCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No registered customers found.");
            return;
        }

        System.out.println("--- Registered Customers ---");
        for (Customer customer : customers) {
            System.out.println("Name: " + customer.name +
                    ", Email: " + customer.email +
                    ", UserType: " + customer.getUserType());
        }
    }
    private static void registerCustomerMenu() throws GarageManagementException {
        System.out.print("Enter com.baymoters.customer.Customer ID: ");
        scanner.nextLine(); // Consume newline
        String id = scanner.nextLine();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.print("Enter com.baymoters.users.User Type (Registered/Walk-In): ");
        String userType = scanner.nextLine();

        Customer newCustomer;
        if ("Registered".equalsIgnoreCase(userType)) {
            newCustomer = new RegisteredCustomer(id, name, email, password);
        } else if ("Walk-In".equalsIgnoreCase(userType)) {
            newCustomer = new WalkInCustomer(id, name, email, password);
        } else {
            System.out.println("Invalid user type. Please enter 'Registered' or 'Walk-In'.");
            return;
        }

        // Add to the list of customers and register in the com.baymoters.services.UserManagementService
        customers.add(newCustomer);
        UserManagementService.getInstance().registerUser(newCustomer);

        System.out.println("com.baymoters.customer.Customer registered successfully. com.baymoters.customer.Customer ID: " + newCustomer.id);
    }

    private static void allocateTask(Manager manager) throws GarageManagementException {
        System.out.print("Enter com.baymoters.users.Mechanic Email: ");
        scanner.nextLine(); // Consume newline
        String mechEmail = scanner.nextLine();

        System.out.print("Enter com.baymoters.customer.Customer Email: ");
        String custEmail = scanner.nextLine();

        System.out.print("Enter com.baymoters.vehicles.Vehicle Registration Number: ");
        String regNumber = scanner.nextLine();

        System.out.print("Enter com.baymoters.task.Task Description: ");
        String description = scanner.nextLine();

        Mechanic mechanic = mechanics.stream()
                .filter(m -> m.email.equals(mechEmail))
                .findFirst()
                .orElseThrow(() -> new GarageManagementException("com.baymoters.users.Mechanic not found."));

        Customer customer = customers.stream()
                .filter(c -> c.email.equals(custEmail))
                .findFirst()
                .orElseThrow(() -> new GarageManagementException("com.baymoters.customer.Customer not found."));

        Vehicle vehicle = customer.getVehicles().stream()
                .filter(v -> v.getRegistrationNumber().equals(regNumber))
                .findFirst()
                .orElseThrow(() -> new GarageManagementException("com.baymoters.vehicles.Vehicle not found."));

        manager.allocateTask(mechanic, vehicle, description);
        System.out.println("com.baymoters.task.Task allocated successfully.");
    }
    private static void viewVehicleDetails() throws GarageManagementException {
        System.out.print("Enter com.baymoters.customer.Customer Email: ");
        scanner.nextLine(); // Consume newline
        String custEmail = scanner.nextLine();

        Customer customer = customers.stream()
                .filter(c -> c.email.equals(custEmail))
                .findFirst()
                .orElseThrow(() -> new GarageManagementException("com.baymoters.customer.Customer not found."));

        System.out.println("--- Vehicles for " + customer.name + " ---");
        customer.getVehicles().forEach(vehicle -> {
            System.out.println("Registration Number: " + vehicle.getRegistrationNumber() +
                    ", Make: " + vehicle.make + ", Model: " + vehicle.model + ", Year: " + vehicle.year);
        });
    }
    private static void sendNotification() throws GarageManagementException {
        System.out.print("Enter com.baymoters.customer.Customer Email: ");
        scanner.nextLine(); // Consume newline
        String custEmail = scanner.nextLine();

        System.out.print("Enter Notification Message: ");
        String message = scanner.nextLine();

        Customer customer = customers.stream()
                .filter(c -> c.email.equals(custEmail))
                .findFirst()
                .orElseThrow(() -> new GarageManagementException("com.baymoters.customer.Customer not found."));

        NotificationService.getInstance().sendNotification(customer, message);
        System.out.println("Notification sent to " + customer.name + ".");
    }

   //com.baymoters.users.Mechanic

    private static void loginMechanic(String email, String password) {
        for (Mechanic mechanic : mechanics) {
            if (mechanic.email.equals(email) && mechanic.password.equals(password)) {
                currentMechanic = mechanic;
                currentUser = mechanic; // Set the currentUser to the mechanic
                System.out.println("com.baymoters.users.Mechanic login successful: " + mechanic.name);

                try {
                    mechanicMenu(); // Show mechanic menu after successful login
                } catch (GarageManagementException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                return;
            }
        }
        System.out.println("Login failed. Invalid credentials.");
    }
    private static void mechanicMenu() throws GarageManagementException {
        if (!(currentUser instanceof Mechanic)) {
            throw new GarageManagementException("Only mechanics can access this menu.");
        }

        Mechanic mechanic = (Mechanic) currentUser;

        while (true) {
            System.out.println("\n--- com.baymoters.users.Mechanic Menu ---");
            System.out.println("1. Manage Manufacturers");
            System.out.println("2. Manage com.baymoters.vehicles.Vehicle Details");
            System.out.println("3. View Tasks");
            System.out.println("4. Complete Tasks");
            System.out.println("5. Manage Suppliers");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    manageManufacturers();
                    break;
                case 2:
                    manageVehicleDetails();
                    break;
                case 3:
                     manageSuppliers();
                    break;
                case 4:
                    completeTaskMenu();
                    break;
                case 5:
                    viewTasksMenu();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    currentUser = null;
                    currentMechanic = null;
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void manageManufacturers() {
        System.out.println("\n--- Manage Manufacturers ---");
        System.out.println("1. Add com.baymoters.vehicles.Manufacturer");
        System.out.println("2. View Manufacturers");
        System.out.println("3. Remove com.baymoters.vehicles.Manufacturer");
        System.out.print("Enter your choice: ");

        int choice = getUserChoice();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter com.baymoters.vehicles.Manufacturer Name: ");
                String manufacturerName = scanner.nextLine();
                // Logic to add manufacturer (example implementation)
                System.out.println("com.baymoters.vehicles.Manufacturer '" + manufacturerName + "' added successfully.");
                break;
            case 2:
                System.out.println("List of Manufacturers:");
                // Logic to display manufacturers
                System.out.println("1. Toyota\n2. Honda\n3. Ford");
                break;
            case 3:
                System.out.print("Enter com.baymoters.vehicles.Manufacturer Name to Remove: ");
                String manufacturerToRemove = scanner.nextLine();
                // Logic to remove manufacturer
                System.out.println("com.baymoters.vehicles.Manufacturer '" + manufacturerToRemove + "' removed successfully.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    private static void manageVehicleDetails() {
        System.out.println("\n--- Manage com.baymoters.vehicles.Vehicle Details ---");
        System.out.println("1. Add com.baymoters.vehicles.Vehicle Details");
        System.out.println("2. Update com.baymoters.vehicles.Vehicle Details");
        System.out.println("3. Remove com.baymoters.vehicles.Vehicle");
        System.out.print("Enter your choice: ");

        int choice = getUserChoice();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter com.baymoters.vehicles.Vehicle Registration Number: ");
                String regNumber = scanner.nextLine();
                System.out.print("Enter Make: ");
                String make = scanner.nextLine();
                System.out.print("Enter Model: ");
                String model = scanner.nextLine();
                System.out.print("Enter Year: ");
                int year = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                // Logic to add vehicle
                System.out.println("com.baymoters.vehicles.Vehicle added: " + regNumber + " (" + make + " " + model + ", " + year + ")");
                break;
            case 2:
                System.out.print("Enter com.baymoters.vehicles.Vehicle Registration Number to Update: ");
                String regNumberToUpdate = scanner.nextLine();
                // Logic to update vehicle
                System.out.println("com.baymoters.vehicles.Vehicle " + regNumberToUpdate + " updated successfully.");
                break;
            case 3:
                System.out.print("Enter com.baymoters.vehicles.Vehicle Registration Number to Remove: ");
                String regNumberToRemove = scanner.nextLine();
                // Logic to remove vehicle
                System.out.println("com.baymoters.vehicles.Vehicle " + regNumberToRemove + " removed successfully.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    private static void manageSuppliers() {
        System.out.println("\n--- Manage Suppliers ---");
        System.out.println("1. Add Supplier");
        System.out.println("2. View Suppliers");
        System.out.println("3. Remove Supplier");
        System.out.print("Enter your choice: ");

        int choice = getUserChoice();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter Supplier Name: ");
                String supplierName = scanner.nextLine();
                System.out.print("Enter Supplier Contact: ");
                String contact = scanner.nextLine();
                // Logic to add supplier
                System.out.println("Supplier '" + supplierName + "' added successfully.");
                break;
            case 2:
                System.out.println("List of Suppliers:");
                // Logic to display suppliers
                System.out.println("1. ABC Suppliers\n2. XYZ Parts\n3. Garage Essentials");
                break;
            case 3:
                System.out.print("Enter Supplier Name to Remove: ");
                String supplierToRemove = scanner.nextLine();
                // Logic to remove supplier
                System.out.println("Supplier '" + supplierToRemove + "' removed successfully.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    //com.baymoters.customer.Customer

    private static void loginCustomer(String email, String password) {
        for (Customer customer : customers) {
            if (customer.email.equals(email) && customer.password.equals(password)) {
                currentCustomer = customer;
                currentUser = customer; // Set the currentUser to the customer
                System.out.println("com.baymoters.customer.Customer login successful: "+ customer.name );

                try {
                    customerMenu(); // Navigate to the customer-specific menu
                } catch (GarageManagementException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                return;
            }
        }
        System.out.println("Login failed. Invalid credentials or not a registered customer.");
    }

    private static void customerMenu() throws GarageManagementException {
        if (!(currentUser instanceof Customer)) {
            throw new GarageManagementException("Only customers can access this menu.");
        }

        Customer customer = (Customer) currentUser;

        while (true) {
            System.out.println("\n--- com.baymoters.customer.Customer Menu ---");
            System.out.println("1. Register com.baymoters.vehicles.Vehicle");
            System.out.println("2. View My Vehicles");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    registerVehicleMenu();
                    break;
                case 2:
                    viewMyVehicles(customer);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    currentUser = null;
                    currentCustomer = null;
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static void viewMyVehicles(Customer customer) {
        List<Vehicle> vehicles = customer.getVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles registered.");
            return;
        }

        System.out.println("--- My Vehicles ---");
        for (Vehicle vehicle : vehicles) {
            System.out.println("Registration Number: " + vehicle.getRegistrationNumber() +
                    ", Make: " + vehicle.make + ", Model: " + vehicle.model + ", Year: " + vehicle.year);
        }
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

        System.out.println("com.baymoters.vehicles.Vehicle registered successfully.");
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
            System.out.println("com.baymoters.vehicles.Vehicle: " + task.getVehicle().getRegistrationNumber());
        }
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
        System.out.println("com.baymoters.task.Task completed successfully.");
    }
}