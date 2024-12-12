package com.baymoters.vehicles;

import java.util.HashMap;
import java.util.Map;

class PartsSupplier {
    private String name;
    private Map<String, Double> partsCatalog;

    public PartsSupplier(String name) {
        this.name = name;
        this.partsCatalog = new HashMap<>();
    }

    public void addPart(String partName, double price) {
        partsCatalog.put(partName, price);
    }
}