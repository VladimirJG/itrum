package ru.danilov.shipping.kafka;

import com.example.core.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShippingConsumer {
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;



    public ShippingConsumer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "payed_orders", groupId = "shipping")
    public void listen(String message) {
        try {
           Order orderShip = objectMapper.readValue(message, Order.class);
            System.out.println(orderShip.getId() + " " + orderShip.getName() + " " + orderShip.getStatus());
            String value = objectMapper.writeValueAsString(orderShip);
            kafkaTemplate.send("sent_orders", value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /*@SneakyThrows
    @KafkaListener(topics = "payed_orders", groupId = "shipping")
    public void processingAndShipping(String message) {
        Order orderShip = objectMapper.readValue(message, Order.class);
        if (orderShip != null) {
            String value = objectMapper.writeValueAsString(orderShip);
            kafkaTemplate.send("sent_orders", value);
        } else {
            throw new RuntimeException("Нет данных для передачи в notification");
        }
    }*/
}
