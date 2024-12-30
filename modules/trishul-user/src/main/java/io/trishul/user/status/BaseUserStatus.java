package io.trishul.user.status;

public interface BaseUserStatus<T extends BaseUserStatus<T>> {
  final String ATTR_NAME = "name";

  String getName();

  T setName(String name);
}
