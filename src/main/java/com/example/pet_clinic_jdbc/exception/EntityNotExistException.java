package com.example.pet_clinic_jdbc.exception;

public class EntityNotExistException extends NotFoundException {
    private static final String MESSAGE = "Resource %s not found with id %s";

    public EntityNotExistException(Class<?> clazz, Long id) {
        super(String.format(MESSAGE, clazz.getSimpleName(), id));
    }
}
