package io.trishul.base.types.lambda;

public interface TriFunction<R, X, Y, Z> {
    R apply(X x, Y y, Z z);
}
