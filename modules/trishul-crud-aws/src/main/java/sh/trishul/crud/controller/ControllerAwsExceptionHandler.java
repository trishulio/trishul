package sh.trishul.crud.controller;

import com.amazonaws.AmazonServiceException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAwsExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(ControllerAwsExceptionHandler.class);

  @ExceptionHandler(value = {AmazonServiceException.class})
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse amazonServiceException(
      AmazonServiceException e, HttpServletRequest request) {
    log.error(
        "Failed to call AWS, received ErrorCode: {}; StatusCode: {}; Message: {}",
        e.getErrorCode(),
        e.getStatusCode(),
        e.getMessage());

    String message = String.format(
        "Failed to call AWS, received ErrorCode: %s; StatusCode: %s; Message: %s",
        e.getErrorCode(),
        e.getStatusCode(),
        e.getMessage());
    return new ErrorResponse(
        LocalDateTime.now(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
        message,
        request.getRequestURI());
  }
}
