package io.trishul.base.types.lambda;

public interface CheckedRunnable<T extends Throwable> {
    void run() throws T;
}
