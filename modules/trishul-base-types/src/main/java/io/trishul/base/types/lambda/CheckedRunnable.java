package io.trishul.model.lambda;

public interface CheckedRunnable<T extends Throwable> {
    void run() throws T;
}
