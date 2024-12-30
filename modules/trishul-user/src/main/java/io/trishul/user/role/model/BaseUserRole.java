package io.trishul.user.role.model;

public interface BaseUserRole<T extends BaseUserRole<T>> {
  final String ATTR_NAME = "name";

  String getName();

  T setName(String name);
}
