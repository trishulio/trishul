package io.trishul.user.model;

public interface AssignedToAccessor {
    final String ATTR_ASSIGNED_TO = "assignedTo";

    User getAssignedTo();

    void setAssignedTo(User user);
}
