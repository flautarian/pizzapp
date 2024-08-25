package com.pizzapp.ordermicroservice.producer;

import com.pizzapp.base.dto.PizzaOrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class PizzaOrderProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PizzaOrderProducer.class);

    private NewTopic topic;

    private KafkaTemplate<String, PizzaOrderEvent> kafkaTemplate;

    public PizzaOrderProducer(NewTopic topic, KafkaTemplate<String, PizzaOrderEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(PizzaOrderEvent event){
        LOGGER.info(String.format("Order event => %s", event.toString()));

        // create order message
        Message<PizzaOrderEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
    }
}