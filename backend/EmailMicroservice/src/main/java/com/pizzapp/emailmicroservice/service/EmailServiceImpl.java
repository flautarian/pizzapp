package com.pizzapp.emailmicroservice.service;

import com.pizzapp.base.dto.PizzaDto;

import jakarta.mail.MessagingException;
import jakarta.mail.Quota.Resource;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl {

    @Value("${spring.mail.username}")
    private String username;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private JavaMailSender emailSender;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void notifyPizzaPlaced(PizzaDto pizza) throws MessagingException {
        // Load and populate the template
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String htmlTemplate;
            htmlTemplate = loadTemplate("order_placed.html");
            Map<String, String> replacements = Map.of(
                    "pizzaName", pizza.getPizzaName(),
                    "clientName", pizza.getCustomerName(),
                    "items", pizza.getExtraIngredients().toString(),
                    "totalPrice", pizza.getPrice() + " €");
            String htmlContent = populateTemplate(htmlTemplate, replacements);

            helper.setFrom(username);
            helper.setTo(pizza.getEmailAddress());
            helper.setSubject("Your pizza has been ordered successfully! 🍕");
            helper.setText(htmlContent, true);

            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStreamSource imageSource = new ByteArrayResource(classloader.getResourceAsStream("static/images/logo.png").readAllBytes());

            if (imageSource != null)
                helper.addInline("pizzaImage", imageSource, "image/png");

            emailSender.send(message);
        } catch (IOException e) {
            LOGGER.error("Error loading template", e);
            e.printStackTrace();
        }
    }

    private String loadTemplate(String templateName) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("templates/" + templateName);
        assert is != null;
        byte[] bytes = is.readAllBytes();
        return new String(bytes);
    }

    private String populateTemplate(String template, Map<String, String> replacements) {
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return template;
    }

    public void notifyPizzaDelivered(PizzaDto pizzaDto) throws MessagingException {

        // Load and populate the template
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String htmlTemplate;
            htmlTemplate = loadTemplate("order_delivered.html");
            Map<String, String> replacements = Map.of(
                    "deliveryAddress", pizzaDto.getDeliveryAddress());
            String htmlContent = populateTemplate(htmlTemplate, replacements);

            helper.setFrom(username);
            helper.setTo(pizzaDto.getEmailAddress());
            helper.setSubject("Your pizza has been delivered!🏍️");
            helper.setText(htmlContent, true);

            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStreamSource imageSource = new ByteArrayResource(classloader.getResourceAsStream("static/images/logo.png").readAllBytes());

            if (imageSource != null)
                helper.addInline("pizzaImage", imageSource, "image/png");

            emailSender.send(message);
        } catch (IOException e) {
            LOGGER.error("Error loading template", e);
            e.printStackTrace();
        }
    }

    public void notifyPizzaDone(PizzaDto pizzaDto) throws MessagingException {
        // Load and populate the template
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String htmlTemplate;
            htmlTemplate = loadTemplate("order_done.html");
            String htmlContent = populateTemplate(htmlTemplate, new HashMap<>());

            helper.setFrom(username);
            helper.setTo(pizzaDto.getEmailAddress());
            helper.setSubject("Your pizza has arrived! 🏠");
            helper.setText(htmlContent);

            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStreamSource imageSource = new ByteArrayResource(classloader.getResourceAsStream("static/images/logo.png").readAllBytes());

            if (imageSource != null)
                helper.addInline("pizzaImage", imageSource, "image/png");


            emailSender.send(message);
        } catch (IOException e) {
            LOGGER.error("Error loading template", e);
            e.printStackTrace();
        }
    }
}