package com.gmail.arthurstrokov.translatemcpserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OllamaChatClientService implements ChatClientService {

    @Value("${app.prompt.translate}")
    private String template;

    private final ChatClient chatClient;

    public String ask(String text) {
        return chatClient.prompt()
                .user(String.format(template, text))
                .call()
                .content();
    }
}
