package ru.relex.controller;


import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.relex.service.UpdateProducer;
import ru.relex.utils.MessageUtils;


@Component
@Log4j
public class UpdateController {
    private TelegramBot telegramBot;
    private final MessageUtils messageUtils;


    public UpdateController(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;

    }

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }
    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null");
            return;
        }

        if (update.hasMessage()) {
            distributeMessagesByType(update);
        } else {
            log.error("Unsupported message type is received: " + update);
        }
    }
    private void distributeMessagesByType(Update update) {
        var message = update.getMessage();

            setUnsupportedMessageTypeView(update);
    }
    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Неподдерживаемый тип сообщения!");
        setView(sendMessage);
    }

    private void setFileIsReceivedView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Файл получен! Обрабатывается...");
        setView(sendMessage);
    }

    public void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }



}
