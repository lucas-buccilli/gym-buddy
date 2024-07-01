package com.example.gymbuddy.implementation.values;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class MailSenderValues {
    @Value("${EMAIL_USERNAME}")
    private String username;
    @Value("${EMAIL_PASSWORD}")
    private String password;
}