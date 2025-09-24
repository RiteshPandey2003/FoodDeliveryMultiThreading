package org.example.model;

import java.util.concurrent.BlockingQueue;

public class DeliveryPartner extends Thread{
    private final String name;
    private final BlockingQueue<Order> readyQueue;

    public DeliveryPartner(String name, BlockingQueue<Order> readyQueue) {
        this.name = name;
        this.readyQueue = readyQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Order order = readyQueue.take(); // waits if empty
                System.out.println("Delivery Partner " + name + " delivering order " + order.getId());
                Thread.sleep(1000); // simulate delivery time
                order.updateStatus("DELIVERED");
                System.out.println("Delivered: " + order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
