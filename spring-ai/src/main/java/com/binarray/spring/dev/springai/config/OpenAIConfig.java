package com.binarray.spring.dev.springai.config;

import lombok.Getter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for setting up OpenAI related beans and configurations.
 *
 * @author Ashesh
 */
@Configuration
@Getter
class OpenAIConfig {
    @Value("${spring.ai.openai.api-url}")
    private String apiUrl;
    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    /**
     * Creates a ChatClient bean.
     *
     * @param builder the ChatClient.Builder used to construct the ChatClient
     * @return a ChatClient instance
     */
    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }

    /**
     * Creates a RestTemplate bean with an interceptor for adding the API key to the Authorization header.
     *
     * @return a RestTemplate instance
     */
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
