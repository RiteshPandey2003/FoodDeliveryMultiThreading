package org.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;

public class DeliveryPartner extends Thread {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private final String name;
    private final BlockingQueue<Order> partnerQueue;
    private final int capacity; // max orders at once

    public DeliveryPartner(String name, BlockingQueue<Order> partnerQueue, int capacity) {
        this.name = name;
        this.partnerQueue = partnerQueue;
        this.capacity = capacity;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Take up to 'capacity' orders
                for (int i = 0; i < capacity; i++) {
                    Order order = partnerQueue.take(); // blocks until order available

//                    // Stop the thread if poison pill received
//                    if (order.isPoisonPill()) {
//                        System.out.println("DeliveryPartner " + name + " received POISON_PILL. Shutting down.");
//                        return; // exit thread
//                    }

                    // Skip cancelled orders
                    if (order.isCancelled()) {
                        System.out.println("DeliveryPartner " + name + " skipped CANCELLED Order " + order.getId());
                        continue;
                    }

                    // Pickup order
                    order.setPickedUpTime(LocalDateTime.now());
                    order.updateStatus("PICKED_UP");
                    System.out.println("DeliveryPartner " + name + " picked Order " + order.getId() +
                            " at " + order.getPickedUpTime().format(FORMATTER));

                    // Simulate delivery
                    Thread.sleep(1000 + (int) (Math.random() * 2000));
                    order.setDeliveredTime(LocalDateTime.now());
                    order.updateStatus("DELIVERED");
                    System.out.println("DeliveryPartner " + name + " delivered Order " + order.getId() +
                            " at " + order.getDeliveredTime().format(FORMATTER));
                }
            }
        } catch (InterruptedException e) {
            System.out.println("DeliveryPartner " + name + " interrupted/stopped.");
            Thread.currentThread().interrupt();
        }
    }
}
