package io.trishul.crud.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import io.trishul.model.base.exception.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

class ControllerExceptionHandlerTest {
  private ControllerExceptionHandler handler;
  private HttpServletRequest mRequest;

  @BeforeEach
  void init() {
    handler = new ControllerExceptionHandler();
    mRequest = mock(HttpServletRequest.class);
    when(mRequest.getRequestURI()).thenReturn("/api/test");
  }

  @Test
  void testEntityNotFoundException_ReturnsNotFoundResponse() {
    EntityNotFoundException exception = new EntityNotFoundException("Entity", "id", "123");

    ErrorResponse response = handler.entityNotFoundException(exception, mRequest);

    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), response.getError());
    assertEquals("/api/test", response.getPath());
    assertNotNull(response.getTimestamp());
  }

  @Test
  void testConstraintViolationException_ReturnsConflictResponse() {
    DataIntegrityViolationException exception
        = new DataIntegrityViolationException("Constraint violation");

    ErrorResponse response = handler.constraintViolationException(exception, mRequest);

    assertNotNull(response);
    assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
    assertEquals(HttpStatus.CONFLICT.getReasonPhrase(), response.getError());
    assertEquals("/api/test", response.getPath());
  }

  @Test
  void testEmptyResultDataAccessException_ReturnsNotFoundResponse() {
    EmptyResultDataAccessException exception = new EmptyResultDataAccessException(1);

    ErrorResponse response = handler.emptyResultDataAccessException(exception, mRequest);

    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), response.getError());
  }

  @Test
  void testObjectOptimisticLockingFailureException_ReturnsConflictResponse() {
    ObjectOptimisticLockingFailureException exception
        = new ObjectOptimisticLockingFailureException("Entity", "id");

    ErrorResponse response = handler.objectOptimisticLockingFailureException(exception, mRequest);

    assertNotNull(response);
    assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
    assertEquals(HttpStatus.CONFLICT.getReasonPhrase(), response.getError());
  }

  @Test
  void testOptimisticLockException_ReturnsConflictResponse() {
    OptimisticLockException exception = new OptimisticLockException("Lock failed");

    ErrorResponse response = handler.objectOptimisticLockingFailureException(exception, mRequest);

    assertNotNull(response);
    assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
  }

  @Test
  void testMethodArgumentNotValidException_ReturnsBadRequestResponse() {
    MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError = new FieldError("object", "field", "must not be null");

    when(exception.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

    ErrorResponse response = handler.methodArgumentNotValidException(exception, mRequest);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), response.getError());
    assertEquals("field must not be null", response.getMessage());
  }

  @Test
  void testRuntimeException_ReturnsInternalServerErrorResponse() {
    RuntimeException exception = new RuntimeException("Unexpected error");

    ErrorResponse response = handler.runtimeException(exception, mRequest);

    assertNotNull(response);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), response.getError());
    assertEquals("Unexpected error", response.getMessage());
  }

  @Test
  void testJpaObjectRetrievalFailureException_ReturnsNotFoundResponse() {
    jakarta.persistence.EntityNotFoundException cause
        = new jakarta.persistence.EntityNotFoundException("Entity not found");
    JpaObjectRetrievalFailureException exception = new JpaObjectRetrievalFailureException(cause);

    ErrorResponse response = handler.jpaObjectRetrievalFailureException(exception, mRequest);

    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), response.getError());
  }

  @Test
  void testIllegalArgumentNotValidException_ReturnsBadRequestResponse() {
    IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

    ErrorResponse response = handler.illegalArgumentNotValidException(exception, mRequest);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), response.getError());
    assertEquals("Invalid argument", response.getMessage());
  }

  @Test
  void testConversionFailedException_ReturnsBadRequestResponse() {
    ConversionFailedException exception = mock(ConversionFailedException.class);
    when(exception.getMessage()).thenReturn("Conversion failed");

    ErrorResponse response = handler.conversionFailedErrorResponse(exception, mRequest);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), response.getError());
  }
}
