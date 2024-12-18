package io.trishul.object.store.file.decorator;

import java.util.List;

public class NoActionDecorator<T> implements EntityDecorator<T> {
    @Override
    public <R extends T> void decorate(List<R> entities) {
        // Does nothing.
    }
}
