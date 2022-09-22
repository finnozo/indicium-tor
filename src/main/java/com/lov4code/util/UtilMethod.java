package com.lov4code.util;

import com.lov4code.response.APIResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.lov4code.util.AppConstants.DATE_FORMATTING_STRING;


public class UtilMethod {
    public static String getPath() {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build().toUri();
        return location.getPath();
    }

    public static String getPath(Long id) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id).toUri();
        return location.getPath();
    }

    public static APIResponse apiResponse(
            Long id,
            int statusCode,
            String status,
            String message) {
        return APIResponse.builder()
                .statusCode(statusCode)
                .status(status)
                .id(id)
                .path(getPath())
                .message(message)
                .timestamp(Instant.now().toString())
                .build();
    }

    public static String getCurrentDateTimeInString() {
        return LocalDateTime.now().toString();
    }

    public static LocalDate convertStringToLocalDate(String localDate) {
        return LocalDate.parse(localDate, DateTimeFormatter.ofPattern(DATE_FORMATTING_STRING));
    }
}
