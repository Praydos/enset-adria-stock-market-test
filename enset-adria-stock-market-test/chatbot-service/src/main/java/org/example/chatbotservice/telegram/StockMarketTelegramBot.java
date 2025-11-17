package org.example.chatbotservice.telegram;



import lombok.extern.slf4j.Slf4j;
import org.example.chatbotservice.services.AIAgentService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class StockMarketTelegramBot extends TelegramLongPollingBot {

    private final String botToken;
    private final String botUsername;
    private final AIAgentService aiAgentService;
    private final Map<Long, String> chatHistories;

    public StockMarketTelegramBot(String botToken, String botUsername, AIAgentService aiAgentService) {
        this.botToken = botToken;
        this.botUsername = botUsername;
        this.aiAgentService = aiAgentService;
        this.chatHistories = new HashMap<>();
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String userMessage = update.getMessage().getText();

            log.info("Received message from chat {}: {}", chatId, userMessage);

            // Get or initialize chat history
            String chatHistory = chatHistories.getOrDefault(chatId, "");

            try {
                // Process with AI agent
                String response = aiAgentService.processUserQuery(userMessage, chatHistory);

                // Update chat history
                String updatedHistory = chatHistory + "\nUser: " + userMessage + "\nBot: " + response;
                if (updatedHistory.length() > 4000) { // Limit history length
                    updatedHistory = updatedHistory.substring(updatedHistory.length() - 4000);
                }
                chatHistories.put(chatId, updatedHistory);

                // Send response
                sendResponse(chatId, response);
            } catch (Exception e) {
                log.error("Error processing message", e);
                sendResponse(chatId, "Sorry, I encountered an error processing your request.");
            }
        }
    }

    private void sendResponse(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error sending message to Telegram", e);
        }
    }
}