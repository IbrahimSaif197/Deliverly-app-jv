package com.deliverly.main.vendor;

public class MenuItem {
    private String id;
    private String name;
    private String description;
    private double price;
    private String vendorId;

    public MenuItem(String id, String name, String description, double price, String vendorId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.vendorId = vendorId;
    }

    // Getters and setters
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }
    
    @Override
    public String toString() {
        return id + ";" + name + ";" + description + ";" + price + ";" + vendorId;
    }
}