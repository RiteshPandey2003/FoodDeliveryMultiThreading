package org.example.DataStructure;

import org.example.model.Order;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class partnerQueue {
    public static BlockingQueue<Order> queue = new PriorityBlockingQueue<>(100);
}
