package com.example.pet_clinic_jdbc.exception_handler;

import com.example.pet_clinic_jdbc.dto.OnErrorResponse;
import com.example.pet_clinic_jdbc.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public <CUSTOM_EXCEPTION extends BaseException> ResponseEntity<OnErrorResponse> catchException(CUSTOM_EXCEPTION ex) {
        log.warn(ex.getMessage());
        return ResponseEntity.status(ex.status()).body(response(ex));
    }

    private <CUSTOM_EXCEPTION extends BaseException> OnErrorResponse response(CUSTOM_EXCEPTION ex) {
        return OnErrorResponse.builder()
                .message(ex.getMessage())
                .now(Instant.now())
                .build();
    }
}
