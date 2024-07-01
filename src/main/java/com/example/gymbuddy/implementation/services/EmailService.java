package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.services.IEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {
    private final JavaMailSender javaMailSender;
    public void sendEmail(SimpleMailMessage welcomeEmailMessage) {
        javaMailSender.send(welcomeEmailMessage);
    }
}
