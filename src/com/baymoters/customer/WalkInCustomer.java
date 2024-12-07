package com.baymoters.customer;

import com.baymoters.customer.Customer;
import com.baymoters.customer.RegisteredCustomer;
import com.baymoters.task.Task;

public class WalkInCustomer extends Customer {
    public WalkInCustomer(String id, String name, String email, String password) {
        super(id, name, email, password);
    }
    @Override
    public String getUserType() {
        return "WALK_IN_CUSTOMER";
    }

    @Override
    protected void addTask(Task task) {

    }

    public RegisteredCustomer upgrade() {
        return new RegisteredCustomer(this.id, this.name, this.password, this.email);
    }
}
