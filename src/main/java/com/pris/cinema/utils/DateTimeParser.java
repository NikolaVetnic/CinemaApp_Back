package com.pris.cinema.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {

    public static LocalDateTime dateTimeFromString(String dateTimeString) {

        if (!dateTimeString.matches("(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})T(?<hour>\\d{2})\\:(?<min>\\d{2})"))
            throw new IllegalArgumentException("Expected format is \"yyyy-MM-dd'T'HH:mm\", eg. \"2020-05-05T20:00\"");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, dtf);

        return dateTime;
    }
}
