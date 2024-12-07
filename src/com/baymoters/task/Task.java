package com.baymoters.task;

import com.baymoters.vehicles.Vehicle;

public class Task implements Comparable<Task> {
    private Vehicle vehicle;
    private String description;
    private int priority;
    private TaskStatus status;
    private String completionDetails;

    public enum TaskStatus {
        PENDING, IN_PROGRESS, COMPLETED
    }

    public Task(Vehicle vehicle, String description) {
        this.vehicle = vehicle;
        this.description = description;
        this.priority = 3; // Default priority
        this.status = TaskStatus.PENDING;
    }

    public void complete(String completionDetails) {
        this.completionDetails = completionDetails;
        this.status = TaskStatus.COMPLETED;
    }

    // Getter for vehicle
    public Vehicle getVehicle() {
        return vehicle;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Getter for priority
    public int getPriority() {
        return priority;
    }

    // Getter for status
    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(other.priority, this.priority);
    }
}
