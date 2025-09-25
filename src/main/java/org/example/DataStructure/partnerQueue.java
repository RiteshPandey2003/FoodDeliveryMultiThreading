package org.example.DataStructure;

import org.example.model.Order;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class partnerQueue {
    public static BlockingQueue<Order> queue = new LinkedBlockingQueue<>(100);
}
