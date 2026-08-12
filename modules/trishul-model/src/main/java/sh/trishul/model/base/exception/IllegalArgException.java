package sh.trishul.model.base.exception;

public class IllegalArgException extends IllegalArgumentException {
  public IllegalArgException(String message) {
    super(message);
  }

  public static void assertion(boolean condition, String message) {
    if (!condition) {
      throw new IllegalArgException(message);
    }
  }
}
