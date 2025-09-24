package org.example.model;

import org.example.DataStructure.ReadyQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Restaurant {

    private final String name;
    private final ExecutorService cookPool;

    public Restaurant(String name, int cookCount) {
        this.name = name;
        this.cookPool = Executors.newFixedThreadPool(cookCount);
    }

    public String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }

    public void SubmitOrder(Order order) {
        cookPool.submit(new Runnable() {
            @Override
            public void run() {
                prepareOrder(order);
            }
        });
    }

    public void prepareOrder(Order order) {
        try {
            System.out.println("Restaurant " + name + " preparing order " + order.getId());
            Thread.sleep(1000); // simulate preparation time
            order.updateStatus("PREPARED");
            ReadyQueue.queue.put(order); // push to ready queue
            System.out.println("Prepared: " + order);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        cookPool.shutdown();
    }

}
