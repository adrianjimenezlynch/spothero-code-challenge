package com.spothero.challenge.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spothero.challenge.bo.Rate;
import com.spothero.challenge.bo.Rates;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RateUtils {

    private static final String FILE_PATH = "src/main/resources/Rates.json";
    private static Rates rates;

    public static void initializeRates() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        rates = mapper.readValue(new File(FILE_PATH), Rates.class);
    }

    public static int getRateForDates(String startDate, String endDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

            LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);
            String startDayOfWeekName = startDateTime.getDayOfWeek().toString().substring(0, 3).toLowerCase();
            String endDayOfWeekName = endDateTime.getDayOfWeek().toString().substring(0, 3).toLowerCase();

            if (areValidDates(startDateTime, endDateTime, startDayOfWeekName, endDayOfWeekName)) {
                return getPrice(startDayOfWeekName, startDateTime.getHour(), endDateTime.getHour());
            } else {
                throw new BadRequestException("Dates must must be in in the correct order and same day");
            }
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Dates must be in ISO format");
        }
    }

    private static boolean areValidDates(LocalDateTime startDateTime, LocalDateTime endDateTime, String startDayOfWeek, String endDayOfWeek) {
        return startDateTime.isBefore(endDateTime) && startDateTime.plusHours(24).isAfter(endDateTime) && startDayOfWeek.equals(endDayOfWeek);
    }

    public static int getPrice(String day, int startTime, int endTime) {
        if (rates != null) {
            for (int i = 0; i < rates.getRates().size(); i++) {
                Rate rate = rates.getRates().get(i);
                if (rate.getDays().contains(day)) {
                    int startTimeRates = Integer.parseInt(rate.getTimes().split("-")[0]) / 100;
                    int endTimeRates = Integer.parseInt(rate.getTimes().split("-")[1]) / 100;
                    if (startTime >= startTimeRates && (endTime < endTimeRates)) {
                        return rate.getPrice();
                    }
                }
            }
        }
        throw new NotFoundException();
    }
}
