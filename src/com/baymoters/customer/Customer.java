package com.baymoters.customer;

import com.baymoters.users.User;
import com.baymoters.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

public abstract class Customer extends User {

    protected List<Vehicle> vehicles;
    private List<String> notifications;

    public Customer(String id, String name, String email, String password) {
        super(id, name, email, password);
        this.name = name;
        this.vehicles = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public abstract String getUserType();

    public void addNotification(String message) {
        notifications.add(message);
        System.out.println("Notification added for " + name + ": " + message);
    }

    public void viewNotifications() {
        if (notifications.isEmpty()) {
            System.out.println("No notifications for " + name + ".");
        } else {
            System.out.println("Notifications for " + name + ":");
            for (String notification : notifications) {
                System.out.println("- " + notification);
            }
        }
    }
}
