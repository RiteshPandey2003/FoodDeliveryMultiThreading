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
                boolean express = ThreadLocalRandom.current().nextBoolean();
                String customerName = "Customer-" + ThreadLocalRandom.current().nextInt(100);
                Order order = new Order(customerName, restaurant, express);
                System.out.println("submit order " + order + "express  " + express );
                restaurant.SubmitOrder(order);
                orderCount++;
                Thread.sleep(500);
            }
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
