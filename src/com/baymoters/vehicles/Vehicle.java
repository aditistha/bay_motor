package com.baymoters.vehicles;

import com.baymoters.customer.Customer;
import com.baymoters.services.ServiceRecord;

import java.util.ArrayList;
import java.util.List;
public class Vehicle {
    private String registrationNumber;
    public String make;
    public String model;
    public int year;
    private Customer owner;
    private List<ServiceRecord> serviceHistory;
    public Vehicle(String registrationNumber, String make, String model, int year, Customer owner) {
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.owner = owner;
        this.serviceHistory = new ArrayList<>();

    }

    public Customer getOwner() {
        return owner;
    }

    public void addServiceRecord(ServiceRecord record) {
        serviceHistory.add(record);
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
}