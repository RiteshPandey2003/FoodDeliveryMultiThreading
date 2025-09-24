package org.example.model;

import java.util.UUID;

public class Order {

    private final String id;
    private final String customerName;
    private final String restaurantName;
    private String status;

    public Order(String customerName, Restaurant restaurantName) {
        this.id = UUID.randomUUID().toString();
        this.customerName = customerName;
        this.restaurantName = restaurantName.getName();
        this.status = "CREATED";
    }

    public void updateStatus(String status){
        this.status = status;
    }

    public String getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getRestaurantName() { return restaurantName; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("Order{id=%s, customer=%s, restaurant=%s, status=%s}",
                id, customerName, restaurantName, status);
    }

}
