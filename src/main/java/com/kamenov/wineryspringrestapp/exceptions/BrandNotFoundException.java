package com.kamenov.wineryspringrestapp.exceptions;

public class BrandNotFoundException extends RuntimeException{
    public BrandNotFoundException(){
        super("Brand not found");
    }
    public BrandNotFoundException(String message) {
        super(message);
    }
    public BrandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
