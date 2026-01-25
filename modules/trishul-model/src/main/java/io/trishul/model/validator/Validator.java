package io.trishul.model.validator;

import java.util.ArrayList;
import java.util.List;
import io.trishul.model.base.exception.ValidationException;

public class Validator {
  private final List<String> errors;

  public Validator() {
    this.errors = new ArrayList<>(0);
  }

  public boolean hasErrors() {
    return !this.errors.isEmpty();
  }

  public boolean rule(boolean pass, String err, Object... args) {
    if (!pass) {
      this.errors.add(String.format(err, args));
    }

    return pass;
  }

  public void raiseErrors() {
    String err = concatIntoNumberedList(this.errors);
    ValidationException.assertion(this.errors.isEmpty(), err);
  }

  private static String concatIntoNumberedList(List<String> msgs) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < msgs.size(); i++) {
      sb.append(i + 1);
      sb.append(". ");
      sb.append(msgs.get(i));
      sb.append(System.lineSeparator());
    }

    return sb.toString();
  }
}
