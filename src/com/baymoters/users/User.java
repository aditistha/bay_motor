package com.baymoters.users;

import com.baymoters.task.Task;

public abstract class User {
    public String id;
    public String name;
    public String email;
    public String password;

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public abstract String getUserType();

    protected abstract void addTask(Task task);
}
