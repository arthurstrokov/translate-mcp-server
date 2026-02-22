package com.gmail.arthurstrokov.translatemcpserver.tool;

import com.gmail.arthurstrokov.translatemcpserver.service.TranslateService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TranslateTool {

    private final TranslateService translateService;

    public TranslateTool(@Lazy TranslateService translateService) {
        this.translateService = translateService;
    }

    @Tool(name = "translate", description = "Перевод")
    public String translate(@ToolParam(description = "Текст для перевода") String text) {
        return translateService.translate(text);
    }
}
