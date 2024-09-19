package com.kamenov.wineryspringrestapp.exceptions;

public class NoSuchOrderFound extends RuntimeException {
    public NoSuchOrderFound() {

    }
    public NoSuchOrderFound(String message) {
        super(message);
    }


}
