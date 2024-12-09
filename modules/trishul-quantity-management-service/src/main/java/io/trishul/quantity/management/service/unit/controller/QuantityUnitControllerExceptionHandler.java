package io.trishul.quantity.management.service.unit.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.trishul.crud.controller.ErrorResponse;
import io.trishul.quantity.unit.IncompatibleQuantityUnitException;

@RestControllerAdvice
public class QuantityUnitControllerExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(QuantityUnitControllerExceptionHandler.class);

    @ExceptionHandler(value = { IncompatibleQuantityUnitException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse incompatibleQuantityUnitException(IncompatibleQuantityUnitException e, HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage(), request.getRequestURI());

        return response;
    }
}