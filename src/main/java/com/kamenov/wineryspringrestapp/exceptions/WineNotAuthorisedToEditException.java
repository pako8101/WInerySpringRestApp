package com.kamenov.wineryspringrestapp.exceptions;

public class WineNotAuthorisedToEditException extends RuntimeException {

    public WineNotAuthorisedToEditException() {
        super("Wine is not authorised");
    }
    public WineNotAuthorisedToEditException(String message) {
        super(message);
    }
    public WineNotAuthorisedToEditException(String message, Throwable cause) {
        super(message, cause);
    }
}
