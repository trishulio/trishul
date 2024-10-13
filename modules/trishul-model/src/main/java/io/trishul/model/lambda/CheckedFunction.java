package io.trishul.model.lambda;

public interface CheckedFunction<R, I, T extends Throwable> {
    R apply(I input) throws T;
}
