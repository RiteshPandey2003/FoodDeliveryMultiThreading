package org.example;

import org.example.CustomerGenrator.CustomerGenerator;
import org.example.DataStructure.ReadyQueue;
import org.example.model.DeliveryPartner;
import org.example.model.Restaurant;


public class Main {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant("Pizza Place", 3);
        CustomerGenerator generator = new CustomerGenerator(restaurant);

        DeliveryPartner dp1 = new DeliveryPartner("Ramesh", ReadyQueue.queue);
        DeliveryPartner dp2 = new DeliveryPartner("Suresh", ReadyQueue.queue);

        // Start threads
        generator.start();
        dp1.start();
        dp2.start();

        try {
            generator.join(); // wait for order creation
            // Wait until all orders are delivered
            while (!ReadyQueue.queue.isEmpty()) {
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        restaurant.shutdown();
        dp1.interrupt();
        dp2.interrupt();
    }
}
