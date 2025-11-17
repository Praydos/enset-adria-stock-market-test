package org.example.chatbotservice.telegram;



import lombok.RequiredArgsConstructor;
import org.example.chatbotservice.services.AIAgentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@RequiredArgsConstructor
public class TelegramBotConfig {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    private final AIAgentService aiAgentService;

    @Bean
    public TelegramBotsApi telegramBotsApi(StockMarketTelegramBot bot) throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(bot);
        return api;
    }

    @Bean
    public StockMarketTelegramBot stockMarketTelegramBot() {
        return new StockMarketTelegramBot(botToken, botUsername, aiAgentService);
    }
}