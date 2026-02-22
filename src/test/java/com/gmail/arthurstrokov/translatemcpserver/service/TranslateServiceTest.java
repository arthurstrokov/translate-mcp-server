package com.gmail.arthurstrokov.translatemcpserver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TranslateServiceTest {

    @Mock
    private ChatClient chatClient;

    @Mock
    private ChatClient.ChatClientRequestSpec requestSpec;

    @Mock
    private ChatClient.CallResponseSpec responseSpec;

    @Captor
    private ArgumentCaptor<String> promptCaptor;

    private TranslateService translateService;

    @BeforeEach
    void setUp() {
        translateService = new TranslateService(chatClient);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("translationDataProvider")
    @DisplayName("Should translate various text inputs")
    void translateShouldHandleVariousInputs(String description, String input, String expectedTranslation) {
        // given
        when(chatClient.prompt()).thenReturn(requestSpec);
        when(requestSpec.user(anyString())).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(responseSpec);
        when(responseSpec.content()).thenReturn(expectedTranslation);

        // when
        String result = translateService.translate(input);

        // then
        assertThat(result).isEqualTo(expectedTranslation);
    }

    private static Stream<Arguments> translationDataProvider() {
        return Stream.of(
                Arguments.of("English text to Russian", "Hello, how are you?", "Привет, как дела?"),
                Arguments.of("Technical text", "The API endpoint returns JSON response.", "API эндпоинт возвращает JSON ответ."),
                Arguments.of("Short text", "Hi", "Привет"),
                Arguments.of("Long text", """
                        The quick brown fox jumps over the lazy dog.
                        This sentence contains every letter of the alphabet.
                        It is commonly used for typing practice and font demonstrations.
                        """, """
                        Быстрая коричневая лиса прыгает через ленивую собаку.
                        Это предложение содержит все буквы алфавита.
                        Оно обычно используется для практики набора шрифта и демонстраций.
                        """),
                Arguments.of("Text with punctuation", "Wait! What? Yes!", "Подожди! Что? Да!"),
                Arguments.of("Text with numbers", "I have 5 apples and 10 oranges.", "У меня есть 5 яблок и 10 апельсинов."),
                Arguments.of("Empty text", "", ""),
                Arguments.of("Whitespace text", "   ", "   "),
                Arguments.of("Null text", null, "")
        );
    }

    @Test
    @DisplayName("Should verify prompt content")
    void translateShouldVerifyPromptContent() {
        // given
        String input = "Hello";
        String expectedTranslation = "Привет";

        when(chatClient.prompt()).thenReturn(requestSpec);
        when(requestSpec.user(anyString())).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(responseSpec);
        when(responseSpec.content()).thenReturn(expectedTranslation);

        // when
        translateService.translate(input);

        // then
        verify(requestSpec).user(promptCaptor.capture());
        assertThat(promptCaptor.getValue()).contains(input);
    }

    @Test
    @DisplayName("Should throw exception when chat client fails")
    void translateShouldThrowExceptionWhenChatClientFails() {
        // given
        String input = "Hello";

        when(chatClient.prompt()).thenReturn(requestSpec);
        when(requestSpec.user(anyString())).thenReturn(requestSpec);
        when(requestSpec.call()).thenThrow(new RuntimeException("Connection error"));

        // when & then
        assertThatThrownBy(() -> translateService.translate(input))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Connection error");
    }
}
