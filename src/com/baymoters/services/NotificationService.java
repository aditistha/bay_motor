package com.baymoters.services;

import com.baymoters.customer.Customer;

public class NotificationService {
    private static NotificationService instance;

    private NotificationService() {}

    public static NotificationService getInstance() {
        if (instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }

    public void sendNotification(Customer customer, String message) {
        if (customer != null) {
            customer.addNotification(message); // Add the message to the customer's notifications
            System.out.println(customer.name + " is notified: " + message);
        } else {
            System.out.println("Error: Customer is null. Cannot send notification.");
        }
    }

    public void notifyCustomer(Customer customer) {
        sendNotification(customer, "Your vehicle service is complete. Thank You!");
    }
}
