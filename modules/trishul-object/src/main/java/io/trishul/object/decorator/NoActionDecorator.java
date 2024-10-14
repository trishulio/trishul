package io.trishul.object.decorator;

import java.util.List;

public class NoActionDecorator<T> implements EntityDecorator<T>{
    @Override
    public <R extends T> void decorate(List<R> entities) {
        // Does nothing.
    }
}
