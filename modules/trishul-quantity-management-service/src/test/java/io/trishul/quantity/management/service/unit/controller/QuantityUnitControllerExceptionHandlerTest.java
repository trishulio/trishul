package io.trishul.quantity.management.service.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import io.trishul.crud.controller.ErrorResponse;
import io.trishul.quantity.unit.IncompatibleQuantityUnitException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class QuantityUnitControllerExceptionHandlerTest {
  private QuantityUnitControllerExceptionHandler handler;

  @BeforeEach
  void setUp() {
    handler = new QuantityUnitControllerExceptionHandler();
  }

  @Test
  void testIncompatibleQuantityUnitException_ReturnsErrorResponseWithBadRequest() {
    IncompatibleQuantityUnitException exception
        = new IncompatibleQuantityUnitException("Incompatible unit");
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getRequestURI()).thenReturn("/test-uri");

    ErrorResponse response = handler.incompatibleQuantityUnitException(exception, request);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), response.getError());
    assertEquals("Incompatible unit", response.getMessage());
    assertEquals("/test-uri", response.getPath());
    assertNotNull(response.getTimestamp());
  }
}
