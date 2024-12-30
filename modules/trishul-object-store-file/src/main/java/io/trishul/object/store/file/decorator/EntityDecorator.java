package io.trishul.object.store.file.decorator;

import java.util.List;

public interface EntityDecorator<T> {
  <R extends T> void decorate(List<R> entities);
}
