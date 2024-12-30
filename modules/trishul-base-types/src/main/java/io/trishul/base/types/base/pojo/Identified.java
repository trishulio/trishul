package io.trishul.base.types.base.pojo;

public interface Identified<T> {
  final String ATTR_ID = "id";

  T getId();
}
