package io.trishul.base.types.lambda;

public interface CheckedFunction<R, I, T extends Throwable> {
  R apply(I input) throws T;
}
