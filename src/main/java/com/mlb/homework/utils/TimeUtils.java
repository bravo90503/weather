package com.mlb.homework.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component("timeUtils")
public class TimeUtils {

	public static final String EST = "US/Eastern";

	public static LocalDate parseToISOLocalDate(String date) {
		return LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(date));
	}

	public static LocalDateTime parseToISOLocalDateTime(String date) {
		return LocalDateTime.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(date));
	}

	public static String formatISOLocalDate(Temporal date) {
		return DateTimeFormatter.ISO_LOCAL_DATE.format(date);
	}

	public static String formatISOLocalDateTime(Temporal date) {
		return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(date);
	}

	public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
		Date out = Date.from(localDateTime.atZone(ZoneId.of(EST)).toInstant());
		return out;
	}

	public static LocalDateTime convertDateToLocalDateTime(Date date) {
		LocalDateTime out = LocalDateTime.ofInstant(date.toInstant(), ZoneId.of(EST));
		return out;
	}

}
