package io.trishul.test.model;

public interface DummyCrudEntityAccessor<T extends DummyCrudEntityAccessor<T>> {
  DummyCrudEntity getDummyCrudEntity();

  T setDummyCrudEntity(DummyCrudEntity entity);
}
