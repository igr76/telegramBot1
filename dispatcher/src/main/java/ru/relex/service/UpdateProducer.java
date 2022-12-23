package ru.relex.service;

import org.telegram.telegrambots.meta.api.objects.Update;
// Сервис передачи update
public interface UpdateProducer {
    void produce(String rabbitQueue, Update update);
}
