package io.trishul.user.service.user.service.role.controller;

import io.trishul.crud.controller.BaseController;
import io.trishul.crud.controller.CrudControllerService;
import io.trishul.crud.controller.filter.AttributeFilter;
import io.trishul.repo.jpa.repository.model.dto.PageDto;
import io.trishul.user.role.model.AddUserRoleDto;
import io.trishul.user.role.model.BaseUserRole;
import io.trishul.user.role.model.UpdateUserRole;
import io.trishul.user.role.model.UpdateUserRoleDto;
import io.trishul.user.role.model.UserRole;
import io.trishul.user.role.model.UserRoleDto;
import io.trishul.user.role.model.UserRoleMapper;
import io.trishul.user.service.user.service.role.service.UserRoleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/users/roles")
public class UserRoleController extends BaseController {
  private final CrudControllerService<Long, UserRole, BaseUserRole<?>, UpdateUserRole<?>, UserRoleDto, AddUserRoleDto, UpdateUserRoleDto> controller;

  private final UserRoleService userRoleService;

  protected UserRoleController(
      CrudControllerService<Long, UserRole, BaseUserRole<?>, UpdateUserRole<?>, UserRoleDto, AddUserRoleDto, UpdateUserRoleDto> controller,
      UserRoleService userRoleService) {
    this.controller = controller;
    this.userRoleService = userRoleService;
  }

  @Autowired
  public UserRoleController(UserRoleService userRoleService, AttributeFilter filter) {
    this(new CrudControllerService<>(filter, UserRoleMapper.INSTANCE, userRoleService, "UserRole"),
        userRoleService);
  }

  @GetMapping(value = "", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PageDto<UserRoleDto> getAllUserRoles(
      @RequestParam(name = "ids", required = false) Set<Long> ids,
      @RequestParam(name = "exclude_ids", required = false) Set<Long> excludeIds,
      @RequestParam(name = "names", required = false) Set<String> names,
      @RequestParam(name = PROPNAME_SORT_BY,
          defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
      @RequestParam(name = PROPNAME_ORDER_ASC,
          defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
      @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
      @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
      @RequestParam(name = PROPNAME_ATTR,
          defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    Page<UserRole> orders
        = userRoleService.getUserRoles(ids, excludeIds, names, sort, orderAscending, page, size);

    return this.controller.getAll(orders, attributes);
  }

  @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public UserRoleDto getUserRole(@PathVariable("id") Long id, @RequestParam(name = PROPNAME_ATTR,
      defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    return this.controller.get(id, attributes);
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public List<UserRoleDto> postUserRole(@Valid @NotNull @RequestBody List<AddUserRoleDto> addDtos) {
    return this.controller.add(addDtos);
  }

  @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public List<UserRoleDto> putUserRole(
      @Valid @NotNull @RequestBody List<UpdateUserRoleDto> updateDtos) {
    return this.controller.put(updateDtos);
  }

  @PatchMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public List<UserRoleDto> patchUserRole(
      @Valid @NotNull @RequestBody List<UpdateUserRoleDto> updateDtos) {
    return this.controller.patch(updateDtos);
  }

  @DeleteMapping(value = "", consumes = MediaType.ALL_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public long deleteUserRoles(@RequestParam("ids") Set<Long> ids) {
    return this.controller.delete(ids);
  }
}
