package com.gmail.arthurstrokov.translatemcpserver.service;

import com.gmail.arthurstrokov.translatemcpserver.configuration.PromptProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OllamaChatClientService implements ChatClientService {

    private final ChatClient chatClient;
    private final PromptProperties promptProperties;

    public String ask(String text) {
        return chatClient.prompt()
                .user(promptProperties.getTranslatePrompt(text))
                .call()
                .content();
    }
}
