package org.example.chatbotservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AIAgentService {

    @Autowired
    private final ChatClient chatClient;
    private final ToolCallbackProvider toolCallbackProvider; // Inject the ToolCallbackProvider



    public String processUserQuery(String userMessage, String chatHistory) {
        log.info("Processing user query: {}", userMessage);

        String systemPrompt = """
            You are a stock market assistant chatbot. You have access to real-time stock market data and tools.
            
            Available Tools:
            - getAllStockQuotations: Get all stock quotations
            - getStockQuotationById: Get specific quotation by ID  
            - getCurrentStockPrice: Get current stock price for a company
            - addStockQuotation: Add new stock quotation
            - deleteStockQuotation: Delete stock quotation
            
            Chat History:
            {chatHistory}
            
            User Question: {userMessage}
            
            Provide helpful, accurate responses about stock market data. If you need to use tools, do so to get real data.
            Always be precise and provide numerical values when available.
            """;

        PromptTemplate promptTemplate = new PromptTemplate(systemPrompt);
        Map<String, Object> model = Map.of(
                "chatHistory", chatHistory != null ? chatHistory : "",
                "userMessage", userMessage
        );

        Prompt prompt = promptTemplate.create(model);

        ChatResponse response = chatClient.prompt(prompt)
                .tools(toolCallbackProvider) // Use the injected ToolCallbackProvider
                .call()
                .chatResponse();

        return response.getResult().getOutput().getText();
    }
}