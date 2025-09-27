package org.example.CustomerGenrator;

import org.example.model.Order;
import org.example.model.Restaurant;

import java.util.concurrent.ThreadLocalRandom;

public class CustomerGenerator extends Thread{

    private final Restaurant restaurant;

    public CustomerGenerator(Restaurant restaurant){
        this.restaurant = restaurant;
    }

    @Override
    public void run(){
        int orderCount = 0;
        try {
            while (orderCount < 2 ){
                String customerName = "Customer-" + ThreadLocalRandom.current().nextInt(100);
                Order order = new Order(customerName, restaurant);
                System.out.println("submit order " + order );
                restaurant.SubmitOrder(order);
                orderCount++;
                Thread.sleep(500);
            }
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
