package io.trishul.crud.controller;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ErrorResponseTest {
    @Test
    public void testErrorResponse_constructor() {
        LocalDateTime localDateTime = LocalDateTime.now();
        int status = 400;
        String error = "error";
        String message = "message";
        String path = "path";
        ErrorResponse errorResponse = new ErrorResponse(localDateTime, status, error, message, path);

        assertEquals(errorResponse.getTimestamp(), localDateTime);
        assertEquals(errorResponse.getStatus(), status);
        assertEquals(errorResponse.getError(), error);
        assertEquals(errorResponse.getMessage(), message);
        assertEquals(errorResponse.getPath(), path);
    }
}
