package com.baymoters.customer;

import com.baymoters.task.Task;

public class WalkInCustomer extends Customer {
    private String contactInfo;
    public WalkInCustomer(String id, String name, String email, String password) {

        super(id, name, email, password);

    }
    @Override
    public String getUserType() {
        return "WALK_IN_CUSTOMER";
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

    public RegisteredCustomer upgrade() {
        RegisteredCustomer registeredCustomer = new RegisteredCustomer(this.id, this.name, this.email, this.password);
        System.out.println("Customer " + name + " has been upgraded to a registered customer.");
        return registeredCustomer;
    }
}
