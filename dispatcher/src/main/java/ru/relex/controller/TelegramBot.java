package ru.relex.controller;



import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.relex.model.MessageRepository;

import javax.annotation.PostConstruct;

@Component
@Log4j
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;
    private final UpdateController updateController;
    @Autowired
    private MessageRepository messageRepository;


    private Message message;
    Message message1 = new Message();


    public TelegramBot(UpdateController updateController, Message message) {
        this.updateController = updateController;
        this.message = message;
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


    @Override
    public void onUpdateReceived(Update update) {
        var originalMassage = update.getMessage();
        log.info(originalMassage.getText());
        if (originalMassage.getText().length() > 10) {
            registerMessage(originalMassage);
        }

        var response = new SendMessage();
        response.setChatId(originalMassage.getChatId().toString());
        response.setText("Hello from bot");
        sendAnswerMessage(response);
    }

    private void registerMessage(Message originalMassage) {
        if (messageRepository.findById(originalMassage.getChatId()).isEmpty()) {
            long chatId = originalMassage.getChatId();
            var chat = originalMassage.getText();
            Message message = new Message();
            message.setCh


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
