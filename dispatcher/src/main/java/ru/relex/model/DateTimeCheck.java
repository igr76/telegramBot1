package ru.relex.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeCheck {
    DateTimeFormatter dateTimeFormatter;
    public   LocalDateTime extractDate(String textDate) {

        LocalDateTime dateTime = LocalDateTime.parse( textDate,DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

        return dateTime;
    }
    public boolean isValid(String dateStr) {
        try {
            LocalDateTime.parse(dateStr, this.dateTimeFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
