package com.example.gymbuddy.implementation.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestService restService;

    @Captor
    ArgumentCaptor<HttpEntity<String>> bodyCaptor;

    @Test
    void post() throws JsonProcessingException {
        var url = "www.google.com";
        var body = "body";
        var headers = CollectionUtils.toMultiValueMap(Map.of("Authorization", List.of("Bearer " + "accessToken")));
        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), ArgumentMatchers.<Class<String>>any())).thenReturn("string result");
        var response = restService.post(url, body, headers, String.class);

        assertEquals("string result", response);
        verify(restTemplate).postForObject(eq(url), bodyCaptor.capture(), eq(String.class));
        assertNotNull(bodyCaptor.getValue());
        assertEquals(body, bodyCaptor.getValue().getBody());
        assertEquals(headers, bodyCaptor.getValue().getHeaders());
    }

    @Test
    void testPost() throws JsonProcessingException {
        var url = "www.google.com";
        var body = "body";
        var headers = CollectionUtils.toMultiValueMap(Map.of("Authorization", List.of("Bearer " + "accessToken")));
        restService.post(url, body, headers);

        verify(restTemplate).postForEntity(eq(url), bodyCaptor.capture(), eq(Void.class));
        assertNotNull(bodyCaptor.getValue());
        assertEquals(body, bodyCaptor.getValue().getBody());
        assertEquals(headers, bodyCaptor.getValue().getHeaders());
    }
}