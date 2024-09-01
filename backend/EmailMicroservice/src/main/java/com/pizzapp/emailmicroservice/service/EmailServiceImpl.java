package com.pizzapp.emailmicroservice.service;

import com.pizzapp.base.dto.PizzaDto;
import com.pizzapp.emailmicroservice.consumer.OrderConsumer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {

    @Value("${spring.mail.username}")
    private String username;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private JavaMailSender emailSender;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void notifyPizzaPlaced(PizzaDto pizza) {
        SimpleMailMessage message = new SimpleMailMessage();

        // Load and populate the template
        try {
            String htmlTemplate;
            htmlTemplate = loadTemplate("order_placed.html");
            Map<String, String> replacements = Map.of(
                    "pizzaName", pizza.getPizzaName(),
                    "clientName", pizza.getCustomerName(),
                    "items", pizza.getExtraIngredients().toString(),
                    "totalPrice", pizza.getPrice() + " €"
            );
            String htmlContent = populateTemplate(htmlTemplate, replacements);
    
            message.setFrom(username);
            message.setTo(pizza.getEmailAddress());
            message.setSubject("Your pizza has been ordered successfully");
            message.setText(htmlContent);
            emailSender.send(message);
        } catch (IOException e) {
            LOGGER.error("Error loading template", e);
            e.printStackTrace();
        }
    }


    private String loadTemplate(String templateName) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/" + templateName);
        byte[] bytes = Files.readAllBytes(Paths.get(resource.getURI()));
        return new String(bytes);
    }

    private String populateTemplate(String template, Map<String, String> replacements) {
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return template;
    }

    public void notifyPizzaDelivered(PizzaDto pizzaDto) {
        SimpleMailMessage message = new SimpleMailMessage();

        // Load and populate the template
        try {
            String htmlTemplate;
            htmlTemplate = loadTemplate("order_delivered.html");
            Map<String, String> replacements = Map.of(
                    "deliveryAddress", pizzaDto.getDeliveryAddress()
            );
            String htmlContent = populateTemplate(htmlTemplate, replacements);
    
            message.setFrom(username);
            message.setTo(pizzaDto.getEmailAddress());
            message.setSubject("Your pizza has been delivered!");
            message.setText(htmlContent);
            emailSender.send(message);
        } catch (IOException e) {
            LOGGER.error("Error loading template", e);
            e.printStackTrace();
        }
    }

    public void notifyPizzaDone(PizzaDto pizzaDto) {
        SimpleMailMessage message = new SimpleMailMessage();

        // Load and populate the template
        try {
            String htmlTemplate;
            htmlTemplate = loadTemplate("order_done.html");
            String htmlContent = populateTemplate(htmlTemplate, new HashMap<>());
    
            message.setFrom(username);
            message.setTo(pizzaDto.getEmailAddress());
            message.setSubject("Your pizza has been delivered!");
            message.setText(htmlContent);
            emailSender.send(message);
        } catch (IOException e) {
            LOGGER.error("Error loading template", e);
            e.printStackTrace();
        }
    }
}