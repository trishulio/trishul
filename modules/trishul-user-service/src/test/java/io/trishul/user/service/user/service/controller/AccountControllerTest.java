package io.trishul.user.service.user.service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import io.trishul.user.model.User;
import io.trishul.user.model.UserDto;
import io.trishul.user.service.user.service.service.AccountService;
import java.net.URI;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountControllerTest {
  private AccountController controller;
  private AccountService mService;

  @BeforeEach
  public void init() {
    this.mService = mock(AccountService.class);
    this.controller = new AccountController(mService);
  }

  @Test
  public void testGetCurrentUser_ReturnsUserDtoFromService() {
    User user = new User(1L, "john.doe", "John Doe", "John", "Doe", "john.doe@example.com",
        "5551234567", URI.create("http://example.com/avatar.jpg"), null, null, List.of(),
        null, null, null);
    user.setIaasUsername("iaas_john.doe");

    doReturn(user).when(mService).getCurrentUser();

    UserDto dto = this.controller.getCurrentUser(Set.of(""));

    UserDto expected =
        new UserDto(1L, "john.doe", "John Doe", "John", "Doe", "john.doe@example.com",
            "5551234567", URI.create("http://example.com/avatar.jpg"), null, null, null,
            List.of(), null, null, null);
    expected.setIaasUsername("iaas_john.doe");
    assertEquals(expected, dto);
  }

  @Test
  public void testGetCurrentUser_ReturnsMappedDtoWithIaasUsername() {
    User user = new User(2L, "jane.smith", "Jane Smith", "Jane", "Smith", "jane.smith@example.com",
        "5559876543", URI.create("http://example.com/jane.jpg"), null, null, List.of(),
        null, null, null);
    user.setIaasUsername("iaas_jane.smith");

    doReturn(user).when(mService).getCurrentUser();

    UserDto dto = this.controller.getCurrentUser(Set.of(""));

    assertEquals("jane.smith", dto.getUserName());
    assertEquals("Jane Smith", dto.getDisplayName());
    assertEquals("jane.smith@example.com", dto.getEmail());
    assertEquals("iaas_jane.smith", dto.getIaasUsername());
  }

  @Test
  public void testGetCurrentUser_IgnoresAttributeParameter() {
    User user = new User(1L, "user1", "User One", "User", "One", "user1@example.com",
        "1234567890", URI.create("http://example.com/image.jpg"), null, null, List.of(),
        null, null, null);
    user.setIaasUsername("iaas_user1");

    doReturn(user).when(mService).getCurrentUser();

    UserDto dto1 = this.controller.getCurrentUser(Set.of(""));
    UserDto dto2 = this.controller.getCurrentUser(Set.of("id", "userName"));

    assertEquals(dto1, dto2);
  }
}
