package com.baymoters.users;

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
        Collections.sort(tasks); // Sort by priority
    }
    public List<Task> getTasks() {
        return tasks;
    }

    public void completeTask(Task task, String completionDetails) {
        task.complete(completionDetails);
        tasks.remove(task);
        NotificationService.getInstance().notifyCustomer(task.getVehicle().getOwner());
    }
}
