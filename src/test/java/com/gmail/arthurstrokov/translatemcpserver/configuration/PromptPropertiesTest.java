package com.gmail.arthurstrokov.translatemcpserver.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PromptPropertiesTest {

    @Mock
    private MessageSource messageSource;

    private PromptProperties promptProperties;

    @BeforeEach
    void setUp() {
        promptProperties = new PromptProperties(messageSource);
    }

    @Test
    @DisplayName("Should call messageSource with correct parameters")
    void getTranslatePromptShouldCallMessageSourceWithCorrectParameters() {
        // given
        String text = "Hello, world!";
        String expectedPrompt = "Translate: Hello, world!";
        when(messageSource.getMessage(eq("prompt.translate"), any(Object[].class), any(Locale.class)))
                .thenReturn(expectedPrompt);
        // when
        String result = promptProperties.getTranslatePrompt(text);
        // then
        assertThat(result).isEqualTo(expectedPrompt);
        verify(messageSource).getMessage(
                eq("prompt.translate"),
                eq(new Object[]{text}),
                any(Locale.class)
        );
    }

    @Test
    @DisplayName("Should include input text in the prompt")
    void getTranslatePromptShouldIncludeInputText() {
        // given
        String text = "Test input";
        when(messageSource.getMessage(eq("prompt.translate"), any(Object[].class), any(Locale.class)))
                .thenAnswer(invocation -> "Prompt with: " + invocation.getArgument(1, Object[].class)[0]);
        // when
        String result = promptProperties.getTranslatePrompt(text);
        // then
        assertThat(result).contains(text);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("textInputProvider")
    @DisplayName("Should handle various text inputs")
    void getTranslatePromptShouldHandleVariousInputs(String description, String input) {
        // given
        when(messageSource.getMessage(eq("prompt.translate"), any(Object[].class), any(Locale.class)))
                .thenAnswer(invocation -> "Prompt: " + invocation.getArgument(1, Object[].class)[0]);
        // when
        String result = promptProperties.getTranslatePrompt(input);
        // then
        assertThat(result).isNotNull();
        if (input != null) {
            assertThat(result).contains(input);
        }
    }

    private static Stream<Object[]> textInputProvider() {
        return Stream.of(
                new Object[]{"Empty text", ""},
                new Object[]{"Whitespace text", "   "},
                new Object[]{"Normal text", "Hello"},
                new Object[]{"Long text", "This is a very long text that should be handled properly"},
                new Object[]{"Text with special characters", "Hello! How are you? #@$%"},
                new Object[]{"Text with numbers", "There are 123 apples"},
                new Object[]{"Null text", null}
        );
    }

    @Test
    @DisplayName("Should use LocaleContextHolder for locale resolution")
    void getTranslatePromptShouldUseLocaleContextHolder() {
        // given
        String text = "Hello";
        when(messageSource.getMessage(eq("prompt.translate"), any(Object[].class), any(Locale.class)))
                .thenReturn("Translated");
        // when
        promptProperties.getTranslatePrompt(text);
        // then
        verify(messageSource).getMessage(
                eq("prompt.translate"),
                eq(new Object[]{text}),
                any(Locale.class)
        );
    }
}