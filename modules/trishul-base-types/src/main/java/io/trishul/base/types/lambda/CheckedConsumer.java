package io.trishul.model.lambda;

public interface CheckedConsumer<I, T extends Throwable> {
    void run(I input) throws T;
}
