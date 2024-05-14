package com.example.pet_clinic_jdbc.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

    @Override
    public HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }

    public NotFoundException(String msg) {
        super(msg);
    }
}
