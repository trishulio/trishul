package io.trishul.model.base.exception;

public class IllegalArgException extends IllegalArgumentException {
  public static void assertion(boolean condition, String message) {
    if (!condition) {
      throw new IllegalArgumentException(message);
    }
  }
}
