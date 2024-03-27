package ru.danilov.notifications.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.danilov.notifications.kafka.NotificationConsumer;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final NotificationConsumer notificationConsumer;


    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam String to, @RequestParam String subject) {
        notificationConsumer.sendEmail(to, subject);
        return "Email sent successfully";
    }
}
