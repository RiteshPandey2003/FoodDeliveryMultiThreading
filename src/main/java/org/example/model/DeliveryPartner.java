package org.example.model;

import java.util.concurrent.BlockingQueue;

public class DeliveryPartner extends Thread{
    private final String name;
    private final BlockingQueue<Order> partnerQueue;
    public DeliveryPartner(String name, BlockingQueue<Order> partnerQueue) {
        this.name = name;
        this.partnerQueue = partnerQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Order order = partnerQueue.take(); // get order
                order.setPickedUpTime(java.time.LocalDateTime.now());
                System.out.println("Partner " + name + " picked Order " + order.getId());

                // simulate pickup & delivery
                Thread.sleep(1000 + (int)(Math.random() * 2000));
                order.setDeliveredTime(java.time.LocalDateTime.now());
                System.out.println("Partner " + name + " delivered Order " + order.getId());
            }
        } catch (InterruptedException e) {
            System.out.println("Partner " + name + " stopped.");
            Thread.currentThread().interrupt();
        }
    }
}
