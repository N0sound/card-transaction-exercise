package model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import lombok.Data;

@Data
public class ExpirationDate {

    private final String unformattedString;
    private final long expirationMillis;

    public static ExpirationDate of(String str) {
        // CreditCards expire after the last day of the indicated month
        long time = parseLocalDateTime(str).toInstant(ZoneOffset.UTC).toEpochMilli();
        return new ExpirationDate(str, time);
    }

    private static LocalDateTime parseLocalDateTime(String str) {
        int month = Integer.parseInt(str.substring(0, 2), 10);
        int year = 2000 + Integer.parseInt(str.substring(2, 4));
        if (month == 12) {
            month = 1;
            year++;
        } else {
            month++;
        }
        return LocalDateTime.of(year, month, 1, 0, 0);
    }
}
