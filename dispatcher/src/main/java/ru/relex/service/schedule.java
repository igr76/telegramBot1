package ru.relex.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.relex.controller.TelegramBot;
import ru.relex.model.Message;
import ru.relex.model.MessageRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;




@Service
public class schedule {
   private MessageRepository messageRepository;
   private Message message;
   private TelegramBot telegramBot;

    @Scheduled(cron = "0 0/1 * * * *")
    public void scheduleUser() throws  InterruptedException {
        LocalDateTime nowDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        Message messageBD = messageRepository.findByDate(nowDateTime);
        if (messageBD != null) {
            var response = new SendMessage();
            response.setText(messageBD.getMessageString());
            telegramBot.sendAnswerMessage(response);
        }

    }
}
