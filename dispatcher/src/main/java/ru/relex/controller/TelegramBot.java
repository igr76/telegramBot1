package ru.relex.controller;



import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.relex.model.DateTimeCheck;
import ru.relex.model.Message;
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
    private DateTimeCheck dateTimeCheck;

    public TelegramBot(UpdateController updateController,MessageRepository messageRepository,DateTimeCheck dateTimeCheck) {
        this.updateController = updateController;
        this.messageRepository = messageRepository;
        this.dateTimeCheck = dateTimeCheck;
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
    @Override
    public void onUpdateReceived(Update update) {
        var originalMassage = update.getMessage().getText();
        log.info(originalMassage);
        if (dateTimeCheck.extractDate(originalMassage) != null) {
            registerMessage(originalMassage);
        }

        switch (originalMassage) {
            case "/start":
                         response.setText("Hello from bot");
                sendAnswerMessage(response);
                break;
            default:
                response.setText("Sorry, command was not recognized");
                sendAnswerMessage(response);
        }
    }
    private void registerMessage(String originalMassage) {

        if (messageRepository.findByData(dateTimeCheck.extractDate(originalMassage)) == null) {
            Message message = new Message();
            message.setData(dateTimeCheck.extractDate(originalMassage));
            message.setMessageString(originalMassage);
            messageRepository.save(message);
            log.info("Message save" + message);
        }  else {response.setText("Такая запись уже есть");
            sendAnswerMessage(response);}
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
