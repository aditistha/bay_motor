package com.baymoters.vehicles;

import java.util.ArrayList;
import java.util.List;

class Manufacturer {
    private String name;
    private List<PartsSupplier> suppliers;

    public Manufacturer(String name) {
        this.name = name;
        this.suppliers = new ArrayList<>();
    }

    public void addSupplier(PartsSupplier supplier) {
        suppliers.add(supplier);
    }
}
