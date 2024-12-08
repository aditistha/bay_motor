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
        System.out.println(  customer.name + "is Notified: " + message);
    }

    public void notifyCustomer(Customer customer) {
        sendNotification(customer, "Your vehicle service is complete. Thank You!");
    }
}
