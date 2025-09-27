package org.example;

import org.example.CustomerGenrator.CustomerGenerator;
import org.example.DataStructure.orderQueue;
import org.example.DataStructure.partnerQueue;
import org.example.Dispatcher.Dispatcher;
import org.example.model.DeliveryPartner;
import org.example.model.Order;
import org.example.model.Restaurant;


public class Main {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant("Pizza Place", 3,3, orderQueue.queue);
        CustomerGenerator generator = new CustomerGenerator(restaurant);

        Dispatcher dispatcher = new Dispatcher(orderQueue.queue, partnerQueue.queue);
        dispatcher.start();

        DeliveryPartner dp1 = new DeliveryPartner("Ramesh", partnerQueue.queue,2);
        DeliveryPartner dp2 = new DeliveryPartner("Suresh", partnerQueue.queue, 2);

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
            orderQueue.queue.put(new Order(true)); // poison pill for dp1
            orderQueue.queue.put(new Order(true)); // poison pill for dp2

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
