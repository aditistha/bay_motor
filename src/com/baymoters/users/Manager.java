package com.baymoters.users;

import com.baymoters.customer.Customer;
import com.baymoters.services.NotificationService;
import com.baymoters.task.Task;
import com.baymoters.vehicles.Vehicle;

public class Manager extends User {
    public Manager(String id, String name, String email, String password) {

        super(id, name, email, password);
    }
    @Override
    public String getUserType() {
        return "MANAGER";
    }

    @Override
    protected void addTask(Task task) {

    }

    public void allocateTask(Mechanic mechanic, Vehicle vehicle, String description) {
        Task task = new Task(vehicle, description);
        mechanic.addTask(task);
    }


}

