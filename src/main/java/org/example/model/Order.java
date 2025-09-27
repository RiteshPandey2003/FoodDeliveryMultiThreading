package org.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Order {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private final String id;
    private final String customerName;
    private final String restaurantName;
    private String status;
    private final LocalDateTime orderPlacedTime;
    private LocalDateTime dispatchedTime;
    private LocalDateTime pickedUpTime;
    private LocalDateTime deliveredTime;
    private final boolean poisonPill;
    private boolean Cancelled = false;

    public Order(String customerName, Restaurant restaurantName) {
        this.id = UUID.randomUUID().toString();
        this.customerName = customerName;
        this.restaurantName = restaurantName.getName();
        this.status = "CREATED";
        this.poisonPill = false;
        this.orderPlacedTime = LocalDateTime.now();
    }

    public Order(boolean poisonPill) {
        this.id = "POISON"; // dummy ID
        this.customerName = "SYSTEM";
        this.restaurantName = "SYSTEM";
        this.status = "STOP";
        this.orderPlacedTime = LocalDateTime.now();
        this.poisonPill = poisonPill;
    }

    public void updateStatus(String status){
        this.status = status;
    }


    public String getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getRestaurantName() { return restaurantName; }
    public String getStatus() { return status; }
    public boolean isPoisonPill() { return poisonPill; }

    public LocalDateTime getOrderPlacedTime() { return orderPlacedTime; }
    public LocalDateTime getDispatchedTime() { return dispatchedTime; }
    public void setDispatchedTime(LocalDateTime dispatchedTime) { this.dispatchedTime = dispatchedTime; }
    public LocalDateTime getPickedUpTime() { return pickedUpTime; }
    public void setPickedUpTime(LocalDateTime pickedUpTime) { this.pickedUpTime = pickedUpTime; }
    public LocalDateTime getDeliveredTime() { return deliveredTime; }
    public void setDeliveredTime(LocalDateTime deliveredTime) { this.deliveredTime = deliveredTime; }

    private String formatTime(LocalDateTime time) {
        return time == null ? "N/A" : time.format(FORMATTER);
    }

    @Override
    public String toString() {
        if (poisonPill) {
            return "Order{POISON_PILL}";
        }
        return String.format("Order{id=%s, customer=%s, restaurant=%s, status=%s, orderPlacedTime=%s}",
                id, customerName, restaurantName, status, formatTime(orderPlacedTime));
    }

    public synchronized  void Cancel(){
        if(!status.equals("PICKED_UP") && !status.equals("DELIVERED")){
            Cancelled = true;
            status = "CANCELLED";
            System.out.println("Order " + id + " cancelled by customer.");
        }
    }

    public synchronized  boolean isCancelled(){
        return Cancelled;
    }
}
