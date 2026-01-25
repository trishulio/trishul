package io.trishul.user.service.user.service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;
import io.trishul.auth.session.context.PrincipalContext;
import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.model.base.exception.EntityNotFoundException;
import io.trishul.user.model.User;
import io.trishul.user.service.user.service.repository.UserRepository;

class AccountServiceTest {
  private AccountService service;
  private UserRepository mRepository;
  private ContextHolder mContextHolder;

  @BeforeEach
  void init() {
    this.mRepository = mock(UserRepository.class);
    this.mContextHolder = mock(ContextHolder.class);
    this.service = new AccountService(mRepository, mContextHolder);
  }

  @Test
  void testGetCurrentUser_ReturnsUserFromRepository_WhenUserExists() {
    PrincipalContext principalContext = mock(PrincipalContext.class);
    doReturn("john.doe").when(principalContext).getUsername();
    doReturn(principalContext).when(mContextHolder).getPrincipalContext();

    User expectedUser = new User(1L, "john.doe", "John Doe", "John", "Doe",
        "john.doe@example.com", "5551234567", null, null, null, null, List.of(), null, null, null);
    expectedUser.setIaasUsername("iaas_john.doe");

    doReturn(List.of(expectedUser)).when(mRepository).findAll(any(Specification.class));

    User user = this.service.getCurrentUser();

    assertEquals(expectedUser, user);
    assertEquals("john.doe", user.getUserName());
    assertEquals("iaas_john.doe", user.getIaasUsername());
  }

  @Test
  void testGetCurrentUser_ReturnsFirstUserFromMultipleMatches() {
    PrincipalContext principalContext = mock(PrincipalContext.class);
    doReturn("test.user").when(principalContext).getUsername();
    doReturn(principalContext).when(mContextHolder).getPrincipalContext();

    User user1 = new User(1L, "test.user", "Test User 1", "Test", "User 1",
        "test1@example.com", "1111111111", null, null, null, null, List.of(), null, null, null);
    User user2 = new User(2L, "test.user", "Test User 2", "Test", "User 2",
        "test2@example.com", "2222222222", null, null, null, null, List.of(), null, null, null);

    doReturn(List.of(user1, user2)).when(mRepository).findAll(any(Specification.class));

    User result = this.service.getCurrentUser();

    assertEquals(user1, result);
    assertEquals(1L, result.getId());
  }

  @Test
  void testGetCurrentUser_ThrowsNotFoundException_WhenContextIsNull() {
    doReturn(null).when(mContextHolder).getPrincipalContext();

    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
        () -> this.service.getCurrentUser());
    assertEquals("User not found with context: current", exception.getMessage());
  }

  @Test
  void testGetCurrentUser_ThrowsNotFoundException_WhenUsernameIsNull() {
    PrincipalContext principalContext = mock(PrincipalContext.class);
    doReturn(null).when(principalContext).getUsername();
    doReturn(principalContext).when(mContextHolder).getPrincipalContext();

    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
        () -> this.service.getCurrentUser());
    assertEquals("User not found with username: current", exception.getMessage());
  }

  @Test
  void testGetCurrentUser_ThrowsNotFoundException_WhenUsernameIsEmpty() {
    PrincipalContext principalContext = mock(PrincipalContext.class);
    doReturn("").when(principalContext).getUsername();
    doReturn(principalContext).when(mContextHolder).getPrincipalContext();

    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
        () -> this.service.getCurrentUser());
    assertEquals("User not found with username: current", exception.getMessage());
  }

  @Test
  void testGetCurrentUser_ThrowsNotFoundException_WhenUserNotFound() {
    PrincipalContext principalContext = mock(PrincipalContext.class);
    doReturn("nonexistent.user").when(principalContext).getUsername();
    doReturn(principalContext).when(mContextHolder).getPrincipalContext();

    doReturn(List.of()).when(mRepository).findAll(any(Specification.class));

    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
        () -> this.service.getCurrentUser());
    assertEquals("User not found with userName or iaasUsername: nonexistent.user",
        exception.getMessage());
  }

  @Test
  void testGetCurrentUser_FindsUserByIaasUsername() {
    PrincipalContext principalContext = mock(PrincipalContext.class);
    doReturn("iaas_jane.smith").when(principalContext).getUsername();
    doReturn(principalContext).when(mContextHolder).getPrincipalContext();

    User expectedUser = new User(2L, "jane.smith", "Jane Smith", "Jane", "Smith",
        "jane.smith@example.com", "5559876543", null, null, null, null, List.of(), null, null, null);
    expectedUser.setIaasUsername("iaas_jane.smith");

    doReturn(List.of(expectedUser)).when(mRepository).findAll(any(Specification.class));

    User user = this.service.getCurrentUser();

    assertEquals(expectedUser, user);
    assertEquals("iaas_jane.smith", user.getIaasUsername());
  }
}
