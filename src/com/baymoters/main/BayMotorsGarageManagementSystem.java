package com.baymoters.main;

import com.baymoters.customer.Customer;
import com.baymoters.customer.RegisteredCustomer;
import com.baymoters.customer.WalkInCustomer;
import com.baymoters.exception.GarageManagementException;
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

    private static List<Manager> managers = Initialization.managers;  // Use shared lists
    private static List<Mechanic> mechanics = Initialization.mechanics;
    private static List<Customer> customers = Initialization.customers;
    public static void main(String[] args) {
        initializeSystem();
        mainMenu();
    }
    private static Manager currentManager = null;
    private static Mechanic currentMechanic = null;
    private static Customer currentCustomer = null;

    private static void initializeSystem() {
        try {
            Initialization.initialize();

        } catch (GarageManagementException e) {
            System.out.println("Error initializing system: " + e.getMessage());
        }
    }

   //Main menu Login

    private static void mainMenu() {
        while (true) {
            System.out.println("\n------- Bay Motors Garage Management System ------");
            System.out.println("\n------- Login --------");
            System.out.println("1. Manager Login");
            System.out.println("2. Mechanic Login");
            System.out.println("3. Customer Login");
            System.out.print("Enter login type: ");


            int loginType = getUserChoice();

            System.out.print("Please Enter Email: ");
            scanner.nextLine();
            String email = scanner.nextLine();

            System.out.print("Please Enter Password: ");
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
                        System.out.println("Login type is invalid.");
                }
        }
    }
    private static int getUserChoice() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }



    //Manager

    private static void loginManager(String email, String password) {
        for (Manager manager : managers) {
            if (manager.email.equals(email) && manager.password.equals(password)) {
                currentManager = manager;
                currentUser = manager;
                System.out.println("Manager login successful: " + manager.name);
                try {
                    ManagerLoginMenu.managerMenu(currentUser);
                } catch (GarageManagementException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                return;
            }
        }
        System.out.println("Login failed. ");
    }


   //MECHANIC

    private static void loginMechanic(String email, String password) {
        for (Mechanic mechanic : mechanics) {
            if (mechanic.email.equals(email) && mechanic.password.equals(password)) {
                currentMechanic = mechanic;
                currentUser = mechanic; // Set the currentUser to the mechanic
                System.out.println("Mechanic login successful: " + mechanic.name);

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
            System.out.println("\n--- Mechanic Menu ---");
            System.out.println("1. Manage Manufacturers");
            System.out.println("2. Manage Vehicle Details");
            System.out.println("3. Manage Suppliers");
            System.out.println("4. Complete Tasks");
            System.out.println("5. View Tasks");
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
        System.out.println("1. Add Manufacturer");
        System.out.println("2. View Manufacturers");
        System.out.println("3. Remove Manufacturer");
        System.out.print("Enter your choice: ");

        int choice = getUserChoice();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter Manufacturer Name: ");
                String manufacturerName = scanner.nextLine();
                // Logic to add manufacturer (example implementation)
                System.out.println("Manufacturer '" + manufacturerName + "' added successfully.");
                break;
            case 2:
                System.out.println("List of Manufacturers:");
                // Logic to display manufacturers
                System.out.println("1. Toyota\n2. Honda\n3. Ford");
                break;
            case 3:
                System.out.print("Enter Manufacturer Name to Remove: ");
                String manufacturerToRemove = scanner.nextLine();
                // Logic to remove manufacturer
                System.out.println("Manufacturer '" + manufacturerToRemove + "' removed successfully.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    private static void manageVehicleDetails() {
        System.out.println("\n--- Manage Vehicle Details ---");
        System.out.println("1. Add Vehicle Details");
        System.out.println("2. Update Vehicle Details");
        System.out.println("3. Remove Vehicle Details");
        System.out.print("Enter your choice: ");

        int choice = getUserChoice();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Registration Number: ");
                String regNumber = scanner.nextLine();
                System.out.print("Enter Make: ");
                String make = scanner.nextLine();
                System.out.print("Enter Model: ");
                String model = scanner.nextLine();
                System.out.print("Enter Year: ");
                int year = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                // Logic to add vehicle
                System.out.println("Vehicle added: " + regNumber + " (" + make + " " + model + ", " + year + ")");
                break;
            case 2:
                System.out.print("Enter Vehicle Registration Number to Update: ");
                String regNumberToUpdate = scanner.nextLine();
                // Logic to update vehicle
                System.out.println("Vehicle " + regNumberToUpdate + " updated successfully.");
                break;
            case 3:
                System.out.print("Vehicle Registration Number to Remove: ");
                String regNumberToRemove = scanner.nextLine();
                // Logic to remove vehicle
                System.out.println("Vehicle " + regNumberToRemove + " has been removed sucessfully.");
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
        scanner.nextLine();

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
            System.out.println(
                    mechanic.name + ", you have been assigned the following task:\n" +
                            "Task Description: " + task.getDescription() + "\n" +
                            "Priority: " + task.getPriority() + "\n" +
                            "Status: " + task.getStatus() + "\n" +
                            "Registration Number: " + task.getVehicle().getRegistrationNumber()
            );
            System.out.println("-----------------------------");
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
        scanner.nextLine();
        String completionDetails = scanner.nextLine();

        mechanic.completeTask(selectedTask, completionDetails);
        System.out.println("Task completed successfully.");
    }

    //Customer

    private static void loginCustomer(String email, String password) {
        for (Customer customer : customers) {
            if (customer.email.equals(email) && customer.password.equals(password)) {
                currentCustomer = customer;
                currentUser = customer; // Set the currentUser to the customer
                System.out.println("Customer login successful: "+ customer.name );

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
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Register Vehicle");
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
        scanner.nextLine();
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
}







