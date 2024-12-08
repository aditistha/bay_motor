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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Initialization {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;
    public static List<Manager> managers = new ArrayList<>();
    public static List<Mechanic> mechanics = new ArrayList<>();
    public static List<Customer> customers = new ArrayList<>();
    public static void initialize() throws GarageManagementException {

            // Create initial users
            Manager manager = new Manager("MA1", "Anita", "anita@gmail.com", "anita123");
            Mechanic mechanic = new Mechanic("ME1", "Aakriti", "aakriti@gmail.com", "aakriti123");
            WalkInCustomer walkInCustomer = new WalkInCustomer("C1", "aditi", "aditi@gmail.com", "aditi123");
            RegisteredCustomer regCustomer = new RegisteredCustomer("C2", "Ronish", "ronish@gmail.com", "ronish123");
            Vehicle vehicle1 = new Vehicle("V1", "Toyota", "Camry", 2024, walkInCustomer);
            Vehicle vehicle2 = new Vehicle("V2", "Honda", "Civic", 2023, regCustomer);


            walkInCustomer.addVehicle(vehicle1);
            regCustomer.addVehicle(vehicle2);

            managers.add(manager);
            mechanics.add(mechanic);
            customers.add(walkInCustomer);
            customers.add(regCustomer);


            UserManagementService.getInstance().registerUser(manager);
            UserManagementService.getInstance().registerUser(mechanic);
            UserManagementService.getInstance().registerUser(walkInCustomer);
            UserManagementService.getInstance().registerUser(regCustomer);


    }

}
