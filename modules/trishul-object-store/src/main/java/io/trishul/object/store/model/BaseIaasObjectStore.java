package io.trishul.object.store.model;

public interface BaseIaasObjectStore<T extends BaseIaasObjectStore<T>> {
  final String ATTR_NAME = "name";

  String getName();

  T setName(String name);
}
