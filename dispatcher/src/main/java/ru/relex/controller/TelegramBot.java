package ru.relex.controller;



import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

@Component
@Log4j
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;

    //
    //
    private final UpdateController updateController;

    public TelegramBot(UpdateController updateController) {
        this.updateController = updateController;
    }

    @PostConstruct
    public void init() {
        updateController.registerBot(this);
    }
    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
    private static  SendMessage response = new SendMessage();
    //11111111111

    //    static final String HELP_TEXT = "This bot is created to demonstrate Spring capabilities.\n\n" +
//            "You can execute commands from the main menu on the left or by typing a command:\n\n" +
//            "Type /start to see a welcome message\n\n" +
//            "Type /mydata to see data stored about yourself\n\n" +
//            "Type /help to see this message again";

    @Override
    public void onUpdateReceived(Update update) {
        var originalMassage = update.getMessage().getText();
        log.info(originalMassage);

        switch (originalMassage) {
            case "/start":
                response.setText("Hello from bot");
                sendAnswerMessage(response);
                break;
//            case "/help":
//                response.setText(HELP_TEXT);
//                sendAnswerMessage(response);
//                break;
//            case "/start":
//                response.setText("Hello from bot");
//                sendAnswerMessage(response);
//                break;
            default:
                response.setText("Sorry, command was not recognized");
                sendAnswerMessage(response);
        }
    }
    public void sendAnswerMessage(SendMessage message) {
        if (message != null) {
            try {
                execute(message);
            } catch (TelegramApiException e) {
                log.error(e);
            }
        }
    }

}
