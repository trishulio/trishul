package io.trishul.crud.controller;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.amazonaws.AmazonServiceException;

@RestControllerAdvice
public class ControllerAwsExceptionHandler {
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(ControllerAwsExceptionHandler.class);

    @ExceptionHandler(value = { AmazonServiceException.class })
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse amazonServiceException(AmazonServiceException e, HttpServletRequest request) {
        String message = String.format("Failed to call AWS, received ErrorCode: %s; StatusCode: %s; Message: %s", e.getErrorCode(), e.getStatusCode(), e.getMessage());
        log.error(message);

        ErrorResponse response = new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), message, request.getRequestURI());

        return response;
    }
}