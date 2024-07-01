package com.example.gymbuddy.infrastructure.services;

import org.springframework.mail.SimpleMailMessage;

public interface IEmailService {

    void sendEmail(SimpleMailMessage welcomeEmailMessage);
}
