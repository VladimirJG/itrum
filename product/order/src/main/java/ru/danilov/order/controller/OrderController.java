package ru.danilov.order.controller;

import com.example.core.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.danilov.order.kafka.OrderProducer;
import ru.danilov.order.service.OrderService;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderProducer orderProducer;

    @PostMapping()
    public Order newOrder(@RequestBody Order order) {
        orderProducer.sendMessage(order);
        return orderService.createNewOrder(order);
    }
}
