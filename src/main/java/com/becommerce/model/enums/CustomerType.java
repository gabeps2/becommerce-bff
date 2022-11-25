package com.becommerce.model.enums;

import lombok.Data;

public enum CustomerType {
    CUSTOMER("Customer"),
    PARTNER("Partner");

    private final String name;
    
    CustomerType(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
}
