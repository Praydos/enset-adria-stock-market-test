package org.example.chatbotservice.config;


import org.example.chatbotservice.mcp.tools.StockMarketTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpConfig {

    @Bean
    public ToolCallbackProvider stockTools(StockMarketTools stockMarketTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(stockMarketTools)
                .build();
    }
}