package io.trishul.user.service.user.service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import io.trishul.crud.controller.CrudControllerService;
import io.trishul.repo.jpa.repository.model.dto.PageDto;
import io.trishul.user.model.AddUserDto;
import io.trishul.user.model.BaseUser;
import io.trishul.user.model.UpdateUser;
import io.trishul.user.model.UpdateUserDto;
import io.trishul.user.model.User;
import io.trishul.user.model.UserDto;
import io.trishul.user.service.user.service.service.UserService;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

public class UserControllerTest {
  private UserController controller;

  private CrudControllerService<Long, User, BaseUser<?>, UpdateUser<?>, UserDto, AddUserDto, UpdateUserDto> mCrudController;

  private UserService mService;

  @BeforeEach
  public void init() {
    this.mCrudController = mock(CrudControllerService.class);
    this.mService = mock(UserService.class);
    this.controller = new UserController(mCrudController, mService);
  }

  @Test
  public void testGetAllUsers_ReturnsDtosFromController() {
    doReturn(new PageImpl<>(
        List.of(new User(1L, "user1", "User One", "User", "One", "user1@example.com",
            "1234567890", URI.create("http://example.com/image.jpg"), null, null, null,
            List.of(), null, null, null))))
            .when(mService).getUsers(Set.of(1L), Set.of(2L), Set.of("user1"), Set.of("User One"),
                Set.of("user1@example.com"), Set.of("1234567890"), Set.of(1L), Set.of(2L),
                Set.of("role1"), 1, 10, new TreeSet<>(List.of("id")), true);
    doReturn(new PageDto<>(
        List.of(new UserDto(1L, "user1", "User One", "User", "One", "user1@example.com",
            "1234567890", URI.create("http://example.com/image.jpg"), null, null, null,
            List.of(), null, null, null)),
        1, 1))
            .when(mCrudController).getAll(
                new PageImpl<>(
                    List.of(new User(1L, "user1", "User One", "User", "One", "user1@example.com",
                        "1234567890", URI.create("http://example.com/image.jpg"), null, null, null,
                        List.of(), null, null, null))),
                Set.of(""));

    PageDto<UserDto> page = this.controller.getAllUsers(Set.of(1L), Set.of(2L), Set.of("user1"),
        Set.of("User One"), Set.of("user1@example.com"), Set.of("1234567890"), Set.of(1L),
        Set.of(2L), Set.of("role1"), new TreeSet<>(List.of("id")), true, 1, 10, Set.of(""));

    PageDto<UserDto> expected = new PageDto<>(
        List.of(new UserDto(1L, "user1", "User One", "User", "One", "user1@example.com",
            "1234567890", URI.create("http://example.com/image.jpg"), null, null, null,
            List.of(), null, null, null)),
        1, 1);
    assertEquals(expected, page);
  }

  @Test
  public void testGetUser_ReturnsDtoFromController() {
    doReturn(new UserDto(1L, "user1", "User One", "User", "One", "user1@example.com",
        "1234567890", URI.create("http://example.com/image.jpg"), null, null, null, List.of(),
        null, null, null))
            .when(mCrudController).get(1L, Set.of(""));

    UserDto dto = this.controller.getUser(1L, Set.of(""));

    UserDto expected = new UserDto(1L, "user1", "User One", "User", "One", "user1@example.com",
        "1234567890", URI.create("http://example.com/image.jpg"), null, null, null, List.of(),
        null, null, null);
    assertEquals(expected, dto);
  }

  @Test
  public void testDeleteUsers_ReturnsDeleteCountFromController() {
    doReturn(1L).when(mCrudController).delete(Set.of(1L));

    assertEquals(1L, this.controller.deleteUsers(Set.of(1L)));
  }

  @Test
  public void testAddUser_AddsToControllerAndReturnsListOfDtos() {
    AddUserDto addDto = new AddUserDto("user1", "User One", "User", "One", "user1@example.com",
        1L, 1L, "1234567890", URI.create("http://example.com/image.jpg"), List.of(1L));

    doReturn(List.of(new UserDto(1L, "user1", "User One", "User", "One", "user1@example.com",
        "1234567890", URI.create("http://example.com/image.jpg"), null, null, null, List.of(),
        null, null, null)))
            .when(mCrudController).add(List.of(addDto));

    List<UserDto> dtos = this.controller.addUser(List.of(addDto));

    assertEquals(List.of(new UserDto(1L, "user1", "User One", "User", "One", "user1@example.com",
        "1234567890", URI.create("http://example.com/image.jpg"), null, null, null, List.of(),
        null, null, null)),
        dtos);
  }

  @Test
  public void testUpdateUser_PutsToControllerAndReturnsListOfDtos() {
    UpdateUserDto updateDto =
        new UpdateUserDto(1L, "user1", "User One", "User", "One", "user1@example.com", 1L, 1L,
            "1234567890", URI.create("http://example.com/image.jpg"), List.of(1L), 1);

    doReturn(List.of(new UserDto(1L, "user1", "User One", "User", "One", "user1@example.com",
        "1234567890", URI.create("http://example.com/image.jpg"), null, null, null, List.of(),
        null, null, null)))
            .when(mCrudController).put(List.of(updateDto));

    List<UserDto> dtos = this.controller.updateUser(List.of(updateDto));

    assertEquals(List.of(new UserDto(1L, "user1", "User One", "User", "One", "user1@example.com",
        "1234567890", URI.create("http://example.com/image.jpg"), null, null, null, List.of(),
        null, null, null)),
        dtos);
  }

  @Test
  public void testPatchUser_PatchToControllerAndReturnsListOfDtos() {
    UpdateUserDto patchDto =
        new UpdateUserDto(1L, "user1", "User One", "User", "One", "user1@example.com", 1L, 1L,
            "1234567890", URI.create("http://example.com/image.jpg"), List.of(1L), 1);

    doReturn(List.of(new UserDto(1L, "user1", "User One", "User", "One", "user1@example.com",
        "1234567890", URI.create("http://example.com/image.jpg"), null, null, null, List.of(),
        null, null, null)))
            .when(mCrudController).patch(List.of(patchDto));

    List<UserDto> dtos = this.controller.patchUser(List.of(patchDto));

    assertEquals(List.of(new UserDto(1L, "user1", "User One", "User", "One", "user1@example.com",
        "1234567890", URI.create("http://example.com/image.jpg"), null, null, null, List.of(),
        null, null, null)),
        dtos);
  }
}
