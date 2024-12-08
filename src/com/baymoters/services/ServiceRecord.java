package com.baymoters.services;
import com.baymoters.users.Mechanic;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
public class ServiceRecord {
    private LocalDate serviceDate;
    private String description;
    private Mechanic servicedBy;

    public ServiceRecord(LocalDate serviceDate, String description, Mechanic servicedBy) {
        this.serviceDate = serviceDate;
        this.description = description;
        this.servicedBy = servicedBy;
    }
}
