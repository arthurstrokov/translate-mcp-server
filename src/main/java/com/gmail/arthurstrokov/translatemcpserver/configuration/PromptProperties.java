package com.gmail.arthurstrokov.translatemcpserver.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromptProperties {

    private final MessageSource messageSource;

    public String getTranslatePrompt(String text) {
        return messageSource.getMessage(
                "prompt.translate",
                new Object[]{text},
                LocaleContextHolder.getLocale()
        );
    }
}