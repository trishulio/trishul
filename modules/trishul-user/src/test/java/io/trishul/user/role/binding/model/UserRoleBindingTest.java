package io.trishul.user.role.binding.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.trishul.user.model.User;
import io.trishul.user.role.model.UserRole;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserRoleBindingTest {
  private UserRoleBinding binding;

  @BeforeEach
  public void init() {
    binding = new UserRoleBinding();
  }

  @Test
  public void testAllArgConstructor_SetsAllFields() {
    binding = new UserRoleBinding(1L, new UserRole(10L), new User(100L));

    assertEquals(1L, binding.getId());
    assertEquals(new UserRole(10L), binding.getRole());
    assertEquals(new User(100L), binding.getUser());
  }

  @Test
  public void testAccessRole() {
    assertNull(binding.getRole());
    binding.setRole(new UserRole(100L));
    assertEquals(new UserRole(100L), binding.getRole());
  }

  @Test
  public void testAccessUser() {
    assertNull(binding.getUser());
    binding.setUser(new User(100L));
    assertEquals(new User(100L), binding.getUser());
  }

  @Test
  public void testAccessCreatedAt() {
    assertNull(this.binding.getCreatedAt());
    this.binding.setCreatedAt(LocalDateTime.of(1996, 1, 1, 1, 1, 1));
    assertEquals(LocalDateTime.of(1996, 1, 1, 1, 1, 1), this.binding.getCreatedAt());
  }

  @Test
  public void testAccessLastUpdated() {
    assertNull(this.binding.getLastUpdated());
    this.binding.setLastUpdated(LocalDateTime.of(1995, 1, 1, 1, 1, 1));
    assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1, 1), this.binding.getLastUpdated());
  }

  @Test
  public void testAccessVersion() {
    assertNull(this.binding.getVersion());
    this.binding.setVersion(1);
    assertEquals(1, this.binding.getVersion());
  }
}
