package ru.danilov.notifications.kafka;

import com.example.core.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {
    private final ObjectMapper objectMapper;
    private JavaMailSender javaMailSender;
    private Order orderForMail;

    @SneakyThrows
    @KafkaListener(topics = "sent_orders", groupId = "notify")
    public void listen(String orderShip) {
        orderForMail = objectMapper.readValue(orderShip, Order.class);
        System.out.println(orderForMail);
    }

    public void sendEmail(String to, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText("Заказ успешно доставлен" + orderForMail.getId());
        javaMailSender.send(message);
    }
}