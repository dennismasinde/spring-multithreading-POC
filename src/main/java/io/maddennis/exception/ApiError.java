package io.maddennis.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiError {

    //error message shape
    private HttpStatus httpStatus;
    private String message;
    private String path;
    private LocalDateTime timestamp;

    public ApiError(HttpStatus httpStatus, String message, String path) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}
