package com.gmail.arthurstrokov.translatemcpserver.tool;

import com.gmail.arthurstrokov.translatemcpserver.service.OllamaChatClientService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TranslateTool {

    private final OllamaChatClientService ollamaChatClientService;

    public TranslateTool(@Lazy OllamaChatClientService ollamaChatClientService) {
        this.ollamaChatClientService = ollamaChatClientService;
    }

    @Tool(name = "translate", description = "Translate text from English to Russian")
    public String translate(@ToolParam(description = "Text to translate") String text) {
        return ollamaChatClientService.ask(text);
    }
}
