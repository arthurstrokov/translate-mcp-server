package com.gmail.arthurstrokov.translatemcpserver.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfiguration {

    @Bean
    ChatClient chatClient(OllamaChatModel model) {
        return ChatClient.builder(model)
                .build();
    }
}
