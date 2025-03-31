package com.anyview.yjy.utils.TimeUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeJSON {
    public static String timeToJSON(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return time.format(formatter);
    }

    public static LocalDateTime JSONtoTime(String json) {
        StringBuilder str = new StringBuilder(json);
        str.setCharAt(10, 'T');
        return LocalDateTime.parse(str.toString());
    }
}
