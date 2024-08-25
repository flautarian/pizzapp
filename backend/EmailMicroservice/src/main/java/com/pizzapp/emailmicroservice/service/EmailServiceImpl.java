package com.pizzapp.emailmicroservice.service;

import com.pizzapp.base.dto.PizzaDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {

    private JavaMailSender emailSender;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void notifyPizzaPlaced(PizzaDto pizza) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("fgiacconi.test@gmail.com");
        message.setTo(pizza.getEmailAddress());
        message.setSubject("Yur pizza has been placed");
        message.setText(String.format("Hello! We sent this email to notify you that your pizza is begin prepared to deliver to %s, We hope to got you very hungry! :D", pizza.getDeliveryAddress()));
        emailSender.send(message);
    }
}