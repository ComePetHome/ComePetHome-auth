package com.project.compethome.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Data
public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
}