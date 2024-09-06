package com.pizzapp.emailmicroservice.consumer;

import com.pizzapp.base.dto.PizzaDto;
import com.pizzapp.base.dto.PizzaOrderEvent;
import com.pizzapp.emailmicroservice.service.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    private final EmailServiceImpl emailService;

    public OrderConsumer(EmailServiceImpl emailService) {

        this.emailService = emailService;
        PizzaDto pizzaDto = new PizzaDto();
        pizzaDto.setStatus("PLACED");
        pizzaDto.setPizzaName("test");
        pizzaDto.setCustomerName("customer");
        pizzaDto.setEmailAddress("fgiacconi.dev@gmail.com");
        pizzaDto.setExtraIngredients(new ArrayList<>());
        this.emailService.notifyPizzaPlaced(pizzaDto);
    }

    @KafkaListener(topics = "order_topics", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(PizzaOrderEvent event) {
        LOGGER.info(String.format("Order event received in email service => %s", event.toString()));
        // send an email to the customer
        // "PLACED", "DELIVERED", "DONE"
        switch (event.getPizzaDto().getStatus()) {
            case "PLACED" -> emailService.notifyPizzaPlaced(event.getPizzaDto());
            case "DELIVERED" -> emailService.notifyPizzaDelivered(event.getPizzaDto());
            case "DONE" -> emailService.notifyPizzaDone(event.getPizzaDto());
        }
    }
}