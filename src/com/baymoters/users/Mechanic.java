package com.baymoters.users;

import com.baymoters.customer.Customer;
import com.baymoters.services.NotificationService;
import com.baymoters.task.Task;
import com.baymoters.users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mechanic extends User {
    private List<Task> tasks;

    public Mechanic(String id, String name, String email, String password) {
        super(id, name, email, password);
        this.tasks = new ArrayList<>();
    }

    @Override
    public String getUserType() {
        return "MECHANIC";
    }

    public void addTask(Task task) {
        tasks.add(task);
        Collections.sort(tasks);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void completeTask(Task task, String completionDetails) {
        task.complete(completionDetails);
        tasks.remove(task);

        Customer owner = task.getVehicle().getOwner();
        if (owner != null) {
            NotificationService.getInstance().notifyCustomer(owner);
            System.out.println("Customer " + owner.getName() + " has been notified about task completion.");
        } else {
            System.out.println("Error: No owner associated with the vehicle.");
        }
    }
}

