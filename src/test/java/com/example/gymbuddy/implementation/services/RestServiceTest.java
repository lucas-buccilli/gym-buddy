package com.example.gymbuddy.implementation.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
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

    @Captor
    ArgumentCaptor<HttpEntity<Object>> getBodyCaptor;

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

    @Test
    void testGet() throws URISyntaxException, JsonProcessingException {
        URI url = new URI("www.google.com");
        var headers = CollectionUtils.toMultiValueMap(Map.of("Authorization", List.of("Bearer " + "accessToken")));
        when(restTemplate.exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), ArgumentMatchers.<Class<Object[]>>any()))
                .thenReturn(new ResponseEntity<Object[]>(List.of(new Object()).toArray(), HttpStatus.OK));

        var response = restService.get(url, headers, Object[].class);
        assertEquals(1, response.length);
        verify(restTemplate).exchange(eq(url), eq(HttpMethod.GET), getBodyCaptor.capture(), eq(Object[].class));
        assertNotNull(getBodyCaptor.getValue().getHeaders().get("Authorization"));
        assertEquals(headers.get("Authorization"), getBodyCaptor.getValue().getHeaders().get("Authorization"));
    }
}