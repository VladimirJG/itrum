package ru.danilov.payment.kafka;

import com.example.core.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public PaymentConsumer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "new_orders", groupId = "product")
    public void listen(String message) {
        System.out.println("Message " + message);
        try {
            Order order = objectMapper.readValue(message, Order.class);
            if (order.getStatus().equals("Sent")) {
                String value = objectMapper.writeValueAsString(order);
                kafkaTemplate.send("payed_orders", value);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
