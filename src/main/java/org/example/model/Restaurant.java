package org.example.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Restaurant {

    private final String name;
    private final ExecutorService cookPool;
    private final BlockingQueue<Order> orderQueue; // injected queue
    private final Semaphore cookingStations;

    // Constructor now accepts the queue
    public Restaurant(String name, int cookCount, int stationCount, BlockingQueue<Order> orderQueue) {
        this.name = name;
        this.cookPool = Executors.newFixedThreadPool(cookCount);
        this.cookingStations = new Semaphore(stationCount);
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
            cookingStations.acquire();
            System.out.println("Restaurant " + name + " preparing order " + order.getId());
            Thread.sleep(1000); // simulate preparation time
            order.updateStatus("PREPARED");
            orderQueue.put(order); // push to ready queue
            System.out.println("Prepared: " + order);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            cookingStations.release();
        }
    }

    // Shutdown the thread pool
    public void shutdown() {
        cookPool.shutdown();
    }

}



//A semaphore maintains a set of permits. A thread must acquire a permit before accessing the resource and release it afterward.
//
//Types of Semaphores:
//
//Counting Semaphore: Has multiple permits (e.g., 3 permits → up to 3 threads can access simultaneously).
//
//Binary Semaphore: Has only 1 permit (similar to a mutex/lock).
//
//Core Methods:
//
//acquire(): A thread tries to acquire a permit. If no permit is available, the thread blocks until one is released.
//
//release(): Releases a permit, potentially unblocking a waiting thread.
//
//availablePermits(): Returns the number of permits currently available.
