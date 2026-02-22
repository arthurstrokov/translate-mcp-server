package com.gmail.arthurstrokov.translatemcpserver.configuration;

import com.gmail.arthurstrokov.translatemcpserver.tool.TranslateTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolConfiguration {

    @Bean
    public ToolCallbackProvider translateToolCallbackProvider(TranslateTool translateTool) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(translateTool)
                .build();
    }
}
