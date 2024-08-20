package com.pizzapp.ordermicroservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic topic(){
        String topicName = "order_topics";
        return TopicBuilder.name(topicName)
                .build();
    }
}