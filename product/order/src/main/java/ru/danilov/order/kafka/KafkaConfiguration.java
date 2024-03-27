package ru.danilov.order.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic newTopic(){
        return TopicBuilder.name("new_orders")
                .partitions(2)
                .replicas(1)
                .build();
    }
}
