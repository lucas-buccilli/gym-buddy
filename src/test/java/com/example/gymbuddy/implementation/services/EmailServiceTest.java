package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.services.IEmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks
    EmailService emailService;

    @Test
    void sendEmail() {
        var message = new SimpleMailMessage();
        emailService.sendEmail(message);

        verify(javaMailSender).send(message);
    }
}