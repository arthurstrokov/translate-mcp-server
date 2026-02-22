package com.gmail.arthurstrokov.translatemcpserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import static com.gmail.arthurstrokov.translatemcpserver.template.TranslatePromptTemplate.TRANSLATE;

@Service
@RequiredArgsConstructor
public class TranslateService {

    private final ChatClient chatClient;

    public String translate(String text) {
        return chatClient.prompt()
                .user(TRANSLATE.formatted(text))
                .call()
                .content();
    }
}
