package com.gmail.arthurstrokov.translatemcpserver;

import com.gmail.arthurstrokov.translatemcpserver.service.OllamaChatClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TranslationIntegrationTest {

    @Autowired
    private OllamaChatClientService ollamaChatClientService;

    @Test
    void testTranslation() {
        String input = "Hello";
        String result = ollamaChatClientService.ask(input);

        assertThat(result).isNotBlank();
        // Check if the result contains Cyrillic characters, which indicates it was translated to Russian
        assertThat(result.chars().anyMatch(c ->
                Character.UnicodeBlock.of((char) c) == Character.UnicodeBlock.CYRILLIC))
                .as("Translation result should contain Cyrillic characters for input '%s'. Result: '%s'", input, result)
                .isTrue();
    }
}
