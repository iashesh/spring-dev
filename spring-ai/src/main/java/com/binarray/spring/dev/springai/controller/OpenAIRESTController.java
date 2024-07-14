package com.binarray.spring.dev.springai.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Controller class for handling requests related to OpenAI chat completions using Spring REST framework.
 *
 * @author Ashesh
 */
@Slf4j
@RestController
@RequestMapping("/openairest")
public class OpenAIRESTController {
    @Setter(onMethod_ = @Autowired)
    private RestTemplate restTemplate;

    /**
     * Processes POST requests to the "/chat" endpoint.
     *
     * @param message the input message to be sent to the OpenAI API
     * @return the response from the OpenAI API wrapped in a ResponseEntity
     */
    @PostMapping("/chat")
    public ResponseEntity<JsonNode> processRequest(@RequestBody JsonNode message) {
        // Construct the request payload
        ObjectMapper mapper = new ObjectMapper();
        JsonNode requestBody = mapper.createObjectNode()
                .put("model", "gpt-3.5-turbo")
                .set("messages", mapper.createArrayNode()
                        .add(mapper.createObjectNode()
                                .put("role", "user")
                                .put("content", message.get("message").asText())));

        // Create HTTP Request
        HttpEntity<JsonNode> httpEntity = new HttpEntity<>(requestBody, httpHeaders());
        ResponseEntity<JsonNode> chatCompletionResponse = ResponseEntity.ok(null);
        try {
            chatCompletionResponse = restTemplate.exchange(
                    URI.create("https://api.openai.com/v1/chat/completions"),
                    HttpMethod.POST,
                    httpEntity,
                    JsonNode.class);
            log.info("Response Body: {}", chatCompletionResponse.getBody());
        } catch (Exception e) {
            log.error("Unable to complete request ", e);
            JsonNode errorResponse = createErrorResponse("Error processing chat request", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
        return chatCompletionResponse;
    }

    /**
     * Sets HTTP headers for the request.
     *
     * @return HttpHeaders object with the required headers set
     */
    private HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        return headers;
    }

    private JsonNode createErrorResponse(String message, String details) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("message", message)
                .put("details", details);
    }
}
