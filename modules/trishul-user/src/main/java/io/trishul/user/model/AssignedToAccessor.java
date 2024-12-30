package io.trishul.user.model;

public interface AssignedToAccessor<T extends AssignedToAccessor<T>> {
  final String ATTR_ASSIGNED_TO = "assignedTo";

  User getAssignedTo();

  T setAssignedTo(User user);
}
