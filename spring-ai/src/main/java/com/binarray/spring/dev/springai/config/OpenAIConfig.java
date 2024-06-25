package com.binarray.spring.dev.springai.config;

import lombok.Getter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Ashesh
 */
@Configuration
@Getter
class OpenAIConfig {
    @Value("spring.ai.openai.api-url")
    private String apiUrl;
    @Value("spring.ai.openai.api-key")
    private String apiKey;

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }

    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + apiKey);
            return execution.execute(request, body);
        });

        return restTemplate;
    }
}
