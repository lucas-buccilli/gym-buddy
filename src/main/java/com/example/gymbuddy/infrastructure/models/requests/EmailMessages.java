package com.example.gymbuddy.infrastructure.models.requests;

import org.springframework.mail.SimpleMailMessage;

public class EmailMessages {
    private final static String SENDER_EMAIL = "noreply@gymbuddy.com";

    private EmailMessages() {}

    public static SimpleMailMessage welcomeEmail(String recipientEmail, String password) {
        SimpleMailMessage welcomeEmailMessage = new SimpleMailMessage();
        welcomeEmailMessage.setTo(recipientEmail);
        welcomeEmailMessage.setSubject("Gym Buddy New Account");
        welcomeEmailMessage.setText("Username: " + recipientEmail + "\nPassword: " + password);
        welcomeEmailMessage.setFrom(SENDER_EMAIL);
        return welcomeEmailMessage;
    }
}
