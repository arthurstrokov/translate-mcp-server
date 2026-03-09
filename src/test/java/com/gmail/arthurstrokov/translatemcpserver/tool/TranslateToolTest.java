package com.gmail.arthurstrokov.translatemcpserver.tool;

import com.gmail.arthurstrokov.translatemcpserver.service.OllamaChatClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TranslateToolTest {

    @Mock
    private OllamaChatClientService ollamaChatClientService;

    private TranslateTool translateTool;

    @BeforeEach
    void setUp() {
        translateTool = new TranslateTool(ollamaChatClientService);
    }

    @ParameterizedTest(name = "Should delegate {0} to service")
    @CsvSource({
            "Hello, Привет",
            "World, Мир",
            "Test, Тест"
    })
    void translateShouldDelegateToService(String input, String expectedTranslation) {
        // given
        when(ollamaChatClientService.ask(input)).thenReturn(expectedTranslation);

        // when
        String result = translateTool.translate(input);

        // then
        assertThat(result).isEqualTo(expectedTranslation);
        verify(ollamaChatClientService).ask(input);
    }
}
