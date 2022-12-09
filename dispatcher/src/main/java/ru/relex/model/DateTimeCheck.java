package ru.relex.model;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeCheck {
    public   LocalDateTime extractDate(String textDate) {

        Pattern messageRegex = Pattern.compile("([0-9.:\s]{16})(\s)([\\W+]+)");
        Matcher matcher = messageRegex.matcher(textDate);
        if (matcher.matches()){
            return LocalDateTime.parse(matcher.group(1));
        } else return null;
    }

}
