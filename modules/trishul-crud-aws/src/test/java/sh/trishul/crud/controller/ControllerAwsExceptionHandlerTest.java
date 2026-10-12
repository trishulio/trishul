package sh.trishul.crud.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.amazonaws.AmazonServiceException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ControllerAwsExceptionHandlerTest {
  private ControllerAwsExceptionHandler handler;
  private HttpServletRequest mRequest;

  @BeforeEach
  void init() {
    handler = new ControllerAwsExceptionHandler();
    mRequest = mock(HttpServletRequest.class);
    when(mRequest.getRequestURI()).thenReturn("/api/test");
  }

  @Test
  void testAmazonServiceException_ReturnsInternalServerErrorResponse() {
    AmazonServiceException exception = mock(AmazonServiceException.class);
    when(exception.getErrorCode()).thenReturn("NoSuchKey");
    when(exception.getStatusCode()).thenReturn(404);
    when(exception.getMessage()).thenReturn("The specified key does not exist.");

    ErrorResponse response = handler.amazonServiceException(exception, mRequest);

    assertNotNull(response);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), response.getError());
    assertEquals(
        "Failed to call AWS, received ErrorCode: NoSuchKey; StatusCode: 404; Message: The"
            + " specified key does not exist.",
        response.getMessage());
    assertEquals("/api/test", response.getPath());
    assertNotNull(response.getTimestamp());
  }

  @Test
  void testAmazonServiceException_WithNullFields_ReturnsInternalServerErrorResponse() {
    AmazonServiceException exception = mock(AmazonServiceException.class);
    when(exception.getErrorCode()).thenReturn(null);
    when(exception.getStatusCode()).thenReturn(0);
    when(exception.getMessage()).thenReturn(null);

    ErrorResponse response = handler.amazonServiceException(exception, mRequest);

    assertNotNull(response);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), response.getError());
    assertEquals(
        "Failed to call AWS, received ErrorCode: null; StatusCode: 0; Message: null",
        response.getMessage());
    assertEquals("/api/test", response.getPath());
    assertNotNull(response.getTimestamp());
  }
}
