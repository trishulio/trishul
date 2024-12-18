package io.trishul.crud.controller;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAwsExceptionHandler {
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