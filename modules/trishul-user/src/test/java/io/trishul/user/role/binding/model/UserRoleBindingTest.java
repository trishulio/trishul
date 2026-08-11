package io.trishul.user.role.binding.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import io.trishul.user.model.User;
import io.trishul.user.role.model.UserRole;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserRoleBindingTest {
  private UserRoleBinding binding;

  @BeforeEach
  void init() {
    binding = new UserRoleBinding();
  }

  @Test
  void testAllArgConstructor_SetsAllFields() {
    binding = new UserRoleBinding().setId(1L).setRole(new UserRole(10L)).setUser(new User(100L));

    assertEquals(1L, binding.getId());
    assertEquals(new UserRole(10L), binding.getRole());
    assertEquals(new User(100L), binding.getUser());
  }

  @Test
  void testAccessRole() {
    assertNull(binding.getRole());
    assertSame(binding, binding.setRole(new UserRole(100L)));
    assertEquals(new UserRole(100L), binding.getRole());
  }

  @Test
  void testAccessUser() {
    assertNull(binding.getUser());
    assertSame(binding, binding.setUser(new User(100L)));
    assertEquals(new User(100L), binding.getUser());
  }

  @Test
  void testAccessCreatedAt() {
    assertNull(this.binding.getCreatedAt());
    assertSame(this.binding, this.binding.setCreatedAt(LocalDateTime.of(1996, 1, 1, 1, 1, 1)));
    assertEquals(LocalDateTime.of(1996, 1, 1, 1, 1, 1), this.binding.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() {
    assertNull(this.binding.getLastUpdated());
    assertSame(this.binding, this.binding.setLastUpdated(LocalDateTime.of(1995, 1, 1, 1, 1, 1)));
    assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1, 1), this.binding.getLastUpdated());
  }

  @Test
  void testAccessVersion() {
    assertNull(this.binding.getVersion());
    assertSame(this.binding, this.binding.setVersion(1));
    assertEquals(1, this.binding.getVersion());
  }

  @Test
  void testAccessId() throws Exception {
    UserRoleBinding accessor = new UserRoleBinding();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

}
