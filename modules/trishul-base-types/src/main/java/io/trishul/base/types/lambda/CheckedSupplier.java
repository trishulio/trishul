package io.trishul.base.types.lambda;

public interface CheckedSupplier<R, I, T extends Throwable> {
    R get(I input) throws T;
}
