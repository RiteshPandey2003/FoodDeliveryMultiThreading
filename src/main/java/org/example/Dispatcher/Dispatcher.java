package org.example.Dispatcher;
import org.example.model.Order;
import java.util.concurrent.BlockingQueue;


public class Dispatcher extends Thread{

    private final BlockingQueue<Order> orderQueue;
    private final BlockingQueue<Order> partnerQueue;

    public Dispatcher(BlockingQueue<Order> orderQueue, BlockingQueue<Order> partnerQueue) {
        this.orderQueue = orderQueue;
        this.partnerQueue = partnerQueue;
    }

    @Override
    public void run(){
        try {
           Order order = orderQueue.take();
            order.setDispatchedTime(java.time.LocalDateTime.now());
            System.out.println("Dispatcher assigned Order " + order.getId());
           partnerQueue.put(order);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}