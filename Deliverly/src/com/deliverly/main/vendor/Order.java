package com.deliverly.main.vendor;

public class Order {
    private String id;
    private String customerId;
    private String vendorId;
    private String menuItemIds;
    private String orderDate;
    private String status;
    private String deliveryOption;
    private double totalPrice;


    public Order(String id, String customerId, String vendorId, String menuItemIds,
                 String orderDate, String status, String deliveryOption, double totalPrice) {
        this.id = id;
        this.customerId = customerId;
        this.vendorId = vendorId;
        this.menuItemIds = menuItemIds;
        this.orderDate = orderDate;
        this.status = status;
        this.deliveryOption = deliveryOption;
        this.totalPrice = totalPrice;

    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getMenuItemIds() {
        return menuItemIds;
    }

    public void setMenuItemIds(String menuItemIds) {
        this.menuItemIds = menuItemIds;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliveryOption() {
        return deliveryOption;
    }

    public void setDeliveryOption(String deliveryOption) {
        this.deliveryOption = deliveryOption;
    }
        
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    @Override
    public String toString() {
        return id + ";" + customerId + ";" + vendorId + ";" + menuItemIds + ";" +
               orderDate + ";" + status + ";" + deliveryOption + ";" + totalPrice;
    }
}