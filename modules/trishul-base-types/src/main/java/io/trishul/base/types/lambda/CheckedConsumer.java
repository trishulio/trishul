package io.trishul.base.types.lambda;

public interface CheckedConsumer<I, T extends Throwable> {
  void run(I input) throws T;
}
