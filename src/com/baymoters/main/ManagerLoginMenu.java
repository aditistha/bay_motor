package com.baymoters.main;

import com.baymoters.customer.Customer;
import com.baymoters.customer.RegisteredCustomer;
import com.baymoters.customer.WalkInCustomer;
import com.baymoters.exception.GarageManagementException;
import com.baymoters.services.UserManagementService;
import com.baymoters.users.Manager;
import com.baymoters.users.Mechanic;
import com.baymoters.users.User;
import com.baymoters.vehicles.Vehicle;

import java.util.List;
import java.util.Scanner;

public class ManagerLoginMenu {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    private static List<Manager> managers = Initialization.managers;
    private static List<Mechanic> mechanics = Initialization.mechanics;
    private static List<Customer> customers = Initialization.customers;

    private static int getUserChoice() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
    public static void managerMenu(User currentUser) throws GarageManagementException {

        if (!(currentUser instanceof Manager)) {
            throw new GarageManagementException("Only managers can access this menu.");
        }

        Manager manager = (Manager) currentUser;

        while (true) {
            System.out.println("\n--- Manager Menu ---");
            System.out.println("1. View Registered Customers");
            System.out.println("2. Register Customer");
            System.out.println("3. Upgrade Customer");
            System.out.println("4. Allocate Task");
            System.out.println("5. View Details");
            System.out.println("6. Manage Manufacturer");
            System.out.println("7. Manage Suppliers");
            System.out.println("8. Send Notification");
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
                    upgradeCustomer();
                    break;
                case 4:
                    allocateTask(manager);
                    break;
                case 5:
                    viewVehicleDetails();
                    break;
                case 6:
                    manageManufacturers();
                    break;
                case 7:
                    manageSuppliers();
                    break;
                case 8:
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
            System.out.println(
                    "Customer ID: " + customer.id +
                            ", Name: " + customer.name +
                            ", Email: " + customer.email +
                            ", UserType: " + customer.getUserType());
        }
    }
    private static void registerCustomerMenu() throws GarageManagementException {
        System.out.print("Please Enter Customer ID: ");
        scanner.nextLine();
        String id = scanner.nextLine();

        System.out.print("Please Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Please Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Please Enter Password: ");
        String password = scanner.nextLine();

        System.out.print("Please Enter User Type (Registered/Walk-In): ");
        String userType = scanner.nextLine();

        Customer newCustomer;
        if ("Registered".equalsIgnoreCase(userType)) {
            newCustomer = new RegisteredCustomer(id, name, email, password);
        } else if ("Walk-In".equalsIgnoreCase(userType)) {
            newCustomer = new WalkInCustomer(id, name, email, password);
        } else {
            System.out.println("User Type is invalid. Please enter 'Registered' or 'Walk-In'.");
            return;
        }

        // Add to the list of customers and register in the UserManagementService
        customers.add(newCustomer);
        UserManagementService.getInstance().registerUser(newCustomer);

        System.out.println("Customer has been registered successfully.");
    }
    private static void upgradeCustomer() throws GarageManagementException {
        System.out.print("Enter Walk-In Customer Email to Upgrade: ");
        scanner.nextLine();
        String email = scanner.nextLine();

        WalkInCustomer walkInCustomer = customers.stream()
                .filter(c -> c instanceof WalkInCustomer && c.email.equals(email))
                .map(c -> (WalkInCustomer) c)
                .findFirst()
                .orElseThrow(() -> new GarageManagementException("Walk-In Customer not found."));

        // Upgrading the customer
        RegisteredCustomer registeredCustomer = walkInCustomer.upgrade();
        customers.remove(walkInCustomer); // Remove the walk-in customer
        customers.add(registeredCustomer); // Add the registered customer

        UserManagementService.getInstance().registerUser(registeredCustomer);

        System.out.println("Customer " + registeredCustomer.name + " upgraded to Registered Customer.");
    }

    private static void allocateTask(Manager manager) throws GarageManagementException {
        System.out.print("Please Enter Mechanic Email: ");
        scanner.nextLine();
        String mechEmail = scanner.nextLine();

        System.out.print("Please Enter Customer Email: ");
        String custEmail = scanner.nextLine();

        System.out.print("Please Enter Registration Number of Vehicle: ");
        String regNumber = scanner.nextLine();

        System.out.print("Please Enter Task Description: ");
        String description = scanner.nextLine();

        Mechanic mechanic = mechanics.stream()
                .filter(m -> m.email.equals(mechEmail))
                .findFirst()
                .orElseThrow(() -> new GarageManagementException("The Mechanic email you entered is not found."));

        Customer customer = customers.stream()
                .filter(c -> c.email.equals(custEmail))
                .findFirst()
                .orElseThrow(() -> new GarageManagementException("The Customer email you entered is not found."));

        Vehicle vehicle = customer.getVehicles().stream()
                .filter(v -> v.getRegistrationNumber().equals(regNumber))
                .findFirst()
                .orElseThrow(() -> new GarageManagementException("The Registration number of Vehicle is not found."));

        manager.allocateTask(mechanic, vehicle, description);
        System.out.println("The Task for Mechanic is allocated successfully.");
    }
    private static void viewVehicleDetails() throws GarageManagementException {
        System.out.print("Please Enter Customer Email: ");
        scanner.nextLine();
        String ctEmail = scanner.nextLine();

        Customer customer = customers.stream()
                .filter(c -> c.email.equals(ctEmail))
                .findFirst()
                .orElseThrow(() -> new GarageManagementException("The Customer email entered is not found."));

        System.out.println("--- Vehicles for " + customer.name + " ---");
        customer.getVehicles().forEach(vehicle -> {
            System.out.println("Registration Number: " + vehicle.getRegistrationNumber() +
                    ", Make: " + vehicle.make + ", Model: " + vehicle.model + ", Year: " + vehicle.year);
        });
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


    private static void sendNotification() throws GarageManagementException {
        System.out.println("1. Notify Registered Customers");
        System.out.println("2. Notify Walked-In Customers");
        System.out.print("Enter your choice: ");

        int choice = getUserChoice();

        switch (choice) {
            case 1:
                System.out.print("Enter Offer for Registered Customers: ");
                scanner.nextLine(); // Consume newline
                String offer = scanner.nextLine();

                customers.stream()
                        .filter(c -> c instanceof RegisteredCustomer)
                        .forEach(c -> {
                            RegisteredCustomer registered = (RegisteredCustomer) c;
                            registered.receiveOffer(offer);
                        });

                System.out.println("Offer sent to all registered customers.");
                break;

            case 2:
                System.out.print("Enter Message for Non-Registered Customers: ");
                scanner.nextLine(); // Consume newline
                String message = scanner.nextLine();

                customers.stream()
                        .filter(c -> c instanceof WalkInCustomer)
                        .forEach(c -> {
                            System.out.println("Message sent to Walk-In Customer: " + c.name);
                            System.out.println("Message: " + message);
                        });

                System.out.println("Message sent to all non-registered customers.");
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

}
