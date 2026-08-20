package sh.trishul.user.model;

public interface AssignedToAccessor<T extends AssignedToAccessor<T>> {
  String ATTR_ASSIGNED_TO = "assignedTo";

  User getAssignedTo();

  T setAssignedTo(User user);
}
