package io.trishul.user.service.user.service.role.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

@SuppressWarnings("unchecked")
public class UserRoleControllerTest {
    private UserRoleController controller;

    private CrudControllerService<
                Long,
                UserRole,
                BaseUserRole,
                UpdateUserRole,
                UserRoleDto,
                AddUserRoleDto,
                UpdateUserRoleDto
            > mCrudController;

    private UserRoleService mService;

    @BeforeEach
    public void init() {
        this.mCrudController = mock(CrudControllerService.class);
        this.mService = mock(UserRoleService.class);
        this.controller = new UserRoleController(mCrudController, mService);
    }

    @Test
    public void testGetAllUserRole_ReturnsDtosFromController() {
        doReturn(new PageImpl<>(List.of(new UserRole(1L)))).when(mService).getUserRoles(
            Set.of(1L),
            Set.of(2L),
            Set.of("name"),
            new TreeSet<>(List.of("userRolename")),
            true,
            1,
            100
        );
        doReturn(new PageDto<>(List.of(new UserRoleDto(1L)), 1, 1)).when(mCrudController).getAll(new PageImpl<>(List.of(new UserRole(1L))), Set.of(""));

        PageDto<UserRoleDto> page = this.controller.getAllUserRoles(
            Set.of(1L),
            Set.of(2L),
            Set.of("name"),
            new TreeSet<>(List.of("userRolename")),
            true,
            1,
            100,
            Set.of("")
        );

        PageDto<UserRoleDto> expected = new PageDto<>(List.of(new UserRoleDto(1L)), 1, 1);
        assertEquals(expected, page);
    }

    @Test
    public void testGetUserRole_ReturnsDtoFromController() {
        doReturn(new UserRoleDto(1L)).when(mCrudController).get(1L, Set.of(""));

        UserRoleDto dto = this.controller.getUserRole(1L, Set.of(""));

        UserRoleDto expected = new UserRoleDto(1L);
        assertEquals(expected, dto);
    }

    @Test
    public void testDeleteUserRoles_ReturnsDeleteCountFromController() {
        doReturn(1L).when(mCrudController).delete(Set.of(1L));

        assertEquals(1L, this.controller.deleteUserRoles(Set.of(1L)));
    }

    @Test
    public void testAddUserRoles_AddsToControllerAndReturnsListOfDtos() {
        doReturn(List.of(new UserRoleDto(1L))).when(mCrudController).add(List.of(new AddUserRoleDto()));

        List<UserRoleDto> dtos = this.controller.postUserRole(List.of(new AddUserRoleDto()));

        assertEquals(List.of(new UserRoleDto(1L)), dtos);
    }

    @Test
    public void testUpdateUserRoles_PutsToControllerAndReturnsListOfDtos() {
        doReturn(List.of(new UserRoleDto(1L))).when(mCrudController).put(List.of(new UpdateUserRoleDto(1L)));

        List<UserRoleDto> dtos = this.controller.putUserRole(List.of(new UpdateUserRoleDto(1L)));

        assertEquals(List.of(new UserRoleDto(1L)), dtos);
    }

    @Test
    public void testPatchUserRoles_PatchToControllerAndReturnsListOfDtos() {
        doReturn(List.of(new UserRoleDto(1L))).when(mCrudController).patch(List.of(new UpdateUserRoleDto(1L)));

        List<UserRoleDto> dtos = this.controller.patchUserRole(List.of(new UpdateUserRoleDto(1L)));

        assertEquals(List.of(new UserRoleDto(1L)), dtos);
    }
}