package ru.relex.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeCheck {
    DateTimeFormatter dateTimeFormatter
    public   LocalDateTime extractDate(String textDate) {
        String formatter = "dd.MM.yyyy HH:mm";
        LocalDateTime dateTime = LocalDateTime.parse(CharSequence textDate, DateTimeFormatter formatter);
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
