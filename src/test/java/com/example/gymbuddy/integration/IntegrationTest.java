package com.example.gymbuddy.integration;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@SpringBootTest
@Testcontainers
@Retention(RetentionPolicy.RUNTIME)
@AutoConfigureMockMvc
@Target(ElementType.TYPE)
@Tag("IntegrationTests")
@Import(DataManagerConfiguration.class)
public @interface IntegrationTest {
}
