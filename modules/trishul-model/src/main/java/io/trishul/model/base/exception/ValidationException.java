package io.trishul.model.base.exception;

public class ValidationException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public ValidationException(String msg) {
    super(msg);
  }

  public static void assertion(boolean condition, String message) {
    if (!condition) {
      throw new ValidationException(message);
    }
  }
}
