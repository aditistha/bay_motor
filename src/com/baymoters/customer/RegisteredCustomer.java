package com.baymoters.customer;

import com.baymoters.customer.Customer;
import com.baymoters.task.Task;

import java.util.ArrayList;
import java.util.List;

public class RegisteredCustomer extends Customer {
    private final boolean isRegistered;
    private List<String> preferences;

    public RegisteredCustomer(String id, String name, String email, String password) {
        super(id, name, email, password);
        this.preferences = new ArrayList<>();
        this.isRegistered = true;
    }
    @Override
    public String getUserType() {
        return "REGISTERED_CUSTOMER";
    }

    @Override
    public void addNotification(String message) {

    }

    @Override
    public void viewNotifications() {

    }

    @Override
    protected void addTask(Task task) {

    }
    public void notifyOffer(String offer) {
        System.out.println("Special Offer for " + name + ": " + offer);
    }
    public void addPreference(String preference) {
        preferences.add(preference);
    }

    public void receiveOffer(String offer) {
        System.out.println("Special Offer for " + name + ": " + offer);
    }
}
