package com.binarray.spring.dev.springai.controller;


import com.binarray.spring.dev.springai.service.OpenAIService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller class for handling requests related to the OpenAI chat service using Spring-AI framework.
 *
 * @author Ashesh
 */
@Slf4j
@RestController
@RequestMapping("/openai")
public class OpenAIController {
    @Setter(onMethod_ = @Autowired)
    private OpenAIService openAIService;
    private final ChatClient chatClient;

    OpenAIController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * Handles GET requests to the "/joke" endpoint.
     *
     * @param message the input message to be processed by the chat client, with a default value of "Tell me a joke"
     * @return a map containing the generated response under the key "completion"
     */
    @GetMapping("/joke")
    Map<String, String> completion(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of(
                "completion",
                chatClient.prompt()
                        .user(message)
                        .call()
                        .content());
    }

    /**
     * Handles GET requests to the "/prompt" endpoint.
     *
     * @param question the input question to be processed by the chat client
     * @return a map containing the input question and the generated response
     */
    @GetMapping("/prompt")
    Map<String, String> prompt(@RequestParam String question) {
        String response = chatClient.prompt().user(question).call().content();
        return Map.of("question", question, "answer", response);
    }

    /**
     * *** This comment is generated by ChatGPT ***
     * Handles GET requests to the "/chat" endpoint.
     * This method receives a chat question as a request parameter and uses the OpenAI service
     * to generate a response to the question.
     *
     * @param question the chat question received
     */
    @GetMapping("/chat")
    String chat(@RequestParam String question) {
        return openAIService.chat(question);
    }

}
