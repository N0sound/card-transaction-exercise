package model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExpirationDate {

	private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder().appendPattern("ddMMyyyy")
			.parseDefaulting(ChronoField.HOUR_OF_DAY, 0).parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
			.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0).toFormatter();;

	private final String unformattedString;
	private final long expirationMillis;

	public static ExpirationDate of(String str) {
		LocalDateTime date = LocalDateTime.parse(createDateStr(str), FORMATTER);
		long time = date.toInstant(ZoneOffset.UTC).toEpochMilli() - 1;
		return new ExpirationDate(str, time);
	}

	// CreditCards expire on the last day of the indicated month
	private static String createDateStr(String str) {
		int month = Integer.parseInt(str.substring(0, 2), 10);
		int year = 2000 + Integer.parseInt(str.substring(2, 4));
		if (month == 12) {
			month = 1;
			year++;
		} else {
			month++;
		}
		return "01" + String.format("%2s", month).replace(' ', '0') + year;
	}
}