package com.kamenov.wineryspringrestapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND)
public class WineNotFoundException extends RuntimeException {
    public WineNotFoundException() {
        super("Wine not found");
    }
    public WineNotFoundException(String message) {
        super(message);
    }
    public WineNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
