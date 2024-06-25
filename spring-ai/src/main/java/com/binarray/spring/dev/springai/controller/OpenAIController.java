package com.binarray.spring.dev.springai.controller;


import com.binarray.spring.dev.springai.service.OpenAIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Ashesh
 */
@Slf4j
@RestController
@RequestMapping("/openai")
public class OpenAIController {
    @Autowired
    private OpenAIService openAIService;
    private final ChatClient chatClient;

    OpenAIController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/joke")
    Map<String, String> completion(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of(
                "completion",
                chatClient.prompt()
                        .user(message)
                        .call()
                        .content());
    }

    @GetMapping("/prompt")
    Map<String, String> prompt(@RequestParam String question) {
        String response = chatClient.prompt().user(question).call().content();
        return Map.of("question", question, "answer", response);
    }

    @GetMapping("/chat")
    String chat(@RequestParam String question) {
        return openAIService.chat(question);
    }

}
