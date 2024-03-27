package ru.danilov.order.service;

import com.example.core.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    public Order createNewOrder(Order order) {
        return order;
    }
}
