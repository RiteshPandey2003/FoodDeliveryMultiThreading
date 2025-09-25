package org.example;

import org.example.CustomerGenrator.CustomerGenerator;
import org.example.DataStructure.ReadyQueue;
import org.example.model.DeliveryPartner;
import org.example.model.Order;
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
            // After generator finishes, no more orders will come.
            // Now we insert "poison pills" to tell delivery partners to stop
            // Number of poison pills = number of consumer threads (2 here)
            ReadyQueue.queue.put(new Order(true)); // poison pill for dp1
            ReadyQueue.queue.put(new Order(true)); // poison pill for dp2

            // Wait until both delivery partners finish their work and consume poison pills
            dp1.join();
            dp2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        restaurant.shutdown();
        dp1.interrupt();
        dp2.interrupt();
    }
}
