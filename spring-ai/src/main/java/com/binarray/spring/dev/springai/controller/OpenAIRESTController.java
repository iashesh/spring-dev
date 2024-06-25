package com.binarray.spring.dev.springai.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.text.MessageFormat;

/**
 * @author Ashesh
 */
@Slf4j
@RestController
@RequestMapping("/openairest")
public class OpenAIRESTController {
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/chat")
    ResponseEntity<?> processRequest(@RequestBody String message) {

        HttpEntity<String> httpEntity = new HttpEntity<>(message, httpHeaders());
        ResponseEntity<String> chatCompletionResponse = ResponseEntity.ok(null);
        try {
            chatCompletionResponse = restTemplate.exchange(URI.create("https://api.openai.com/v1/chat/completions"), HttpMethod.POST, httpEntity, String.class);
            System.out.println(chatCompletionResponse.getBody());
        } catch (Exception e) {
            System.out.println("Unable to complete request ");
            e.printStackTrace();
        }
        return chatCompletionResponse;
    }

    /**
     * Set HTTP Headers here
     *
     * @return httpHeaders
     */
    private HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.set(HttpHeaders.AUTHORIZATION, MessageFormat.format("Bearer {0}", "sk-proj-JEoYj6nuhgpcYyjocJXjT3BlbkFJcL2WZRJfu9aaIRuYTQGD"));
        return headers;
    }
}
