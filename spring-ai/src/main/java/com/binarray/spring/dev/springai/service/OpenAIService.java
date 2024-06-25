package com.binarray.spring.dev.springai.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ashesh
 */
@Slf4j
@Service
public class OpenAIService {
    private final OpenAiChatModel chatModel;

    @Autowired
    OpenAIService(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String chat(String message) {
        PromptTemplate promptTemplate = null;
        String response = null;
        try {
            promptTemplate = new PromptTemplate(message);

            ChatResponse chatResponse = chatModel.call(promptTemplate.create());
            response = chatResponse.getResult().getOutput().getContent();
        } catch (Exception e) {
            log.error("Error in chat: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return response;
    }
}
