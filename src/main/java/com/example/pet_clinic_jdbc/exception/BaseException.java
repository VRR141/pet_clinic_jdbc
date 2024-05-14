package com.example.pet_clinic_jdbc.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {

    public abstract HttpStatus status();

    public BaseException(String message) {
        super(message, null, false, false);
    }
}
