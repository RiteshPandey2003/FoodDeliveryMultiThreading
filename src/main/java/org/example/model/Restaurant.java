package org.example.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Restaurant {

    private final String name;
    private final ExecutorService cookPool;
    private final BlockingQueue<Order> orderQueue; // injected queue

    // Constructor now accepts the queue
    public Restaurant(String name, int cookCount, BlockingQueue<Order> orderQueue) {
        this.name = name;
        this.cookPool = Executors.newFixedThreadPool(cookCount);
        this.orderQueue = orderQueue;
    }

    public String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }

    // Submit order to be prepared
    public void SubmitOrder(Order order) {
        cookPool.submit(() -> prepareOrder(order));
    }

    // Simulate order preparation
    private void prepareOrder(Order order) {
        try {
            System.out.println("Restaurant " + name + " preparing order " + order.getId());
            Thread.sleep(1000); // simulate preparation time
            order.updateStatus("PREPARED");
            orderQueue.put(order); // push to ready queue
            System.out.println("Prepared: " + order);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Shutdown the thread pool
    public void shutdown() {
        cookPool.shutdown();
    }

}
