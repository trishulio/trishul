package io.trishul.user.service.user.service.controller;

import io.trishul.crud.controller.BaseController;
import io.trishul.crud.controller.CrudControllerService;
import io.trishul.crud.controller.filter.AttributeFilter;
import io.trishul.repo.jpa.repository.model.dto.PageDto;
import io.trishul.user.model.AddUserDto;
import io.trishul.user.model.BaseUser;
import io.trishul.user.model.UpdateUser;
import io.trishul.user.model.UpdateUserDto;
import io.trishul.user.model.User;
import io.trishul.user.model.UserDto;
import io.trishul.user.model.UserMapper;
import io.trishul.user.service.user.service.service.UserService;
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
@RequestMapping(path = "/api/v1/users")
public class UserController extends BaseController {
    private final CrudControllerService<
                    Long, User, BaseUser, UpdateUser, UserDto, AddUserDto, UpdateUserDto>
            controller;

    private final UserService userService;

    protected UserController(
            CrudControllerService<
                            Long, User, BaseUser, UpdateUser, UserDto, AddUserDto, UpdateUserDto>
                    controller,
            UserService userService) {
        this.controller = controller;
        this.userService = userService;
    }

    @Autowired
    public UserController(
            UserService userService, AttributeFilter filter, UserDtoDecorator decorator) {
        this(
                new CrudControllerService<>(
                        filter, UserMapper.INSTANCE, userService, "User", decorator),
                userService);
    }

    @GetMapping(
            value = "",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDto<UserDto> getAllUsers(
            @RequestParam(required = false) Set<Long> ids,
            @RequestParam(required = false, name = "exclude_ids") Set<Long> excludeIds,
            @RequestParam(required = false, name = "user_names") Set<String> userNames,
            @RequestParam(required = false, name = "display_names") Set<String> displayNames,
            @RequestParam(required = false, name = "emails") Set<String> emails,
            @RequestParam(required = false, name = "phone_numbers") Set<String> phoneNumbers,
            @RequestParam(required = false, name = "status") Set<Long> statusIds,
            @RequestParam(required = false, name = "salutations") Set<Long> salutationIds,
            @RequestParam(required = false, name = "roles") Set<String> roles,
            @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY)
                    SortedSet<String> sort,
            @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC)
                    boolean orderAscending,
            @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX)
                    int page,
            @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE)
                    int size,
            @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR)
                    Set<String> attributes) {
        Page<User> userPage =
                userService.getUsers(
                        ids,
                        excludeIds,
                        userNames,
                        displayNames,
                        emails,
                        phoneNumbers,
                        statusIds,
                        salutationIds,
                        roles,
                        page,
                        size,
                        sort,
                        orderAscending);

        return this.controller.getAll(userPage, attributes);
    }

    @GetMapping(
            value = "/{userId}",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getUser(
            @PathVariable(required = true, name = "userId") Long userId,
            @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR)
                    Set<String> attributes) {
        return this.controller.get(userId, attributes);
    }

    @DeleteMapping(value = "", consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public long deleteUsers(@RequestParam("ids") Set<Long> userIds) {
        return this.controller.delete(userIds);
    }

    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<UserDto> addUser(@Valid @NotNull @RequestBody List<AddUserDto> addDtos) {
        return this.controller.add(addDtos);
    }

    @PutMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<UserDto> updateUser(@Valid @NotNull @RequestBody List<UpdateUserDto> updateDtos) {
        return this.controller.put(updateDtos);
    }

    @PatchMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<UserDto> patchUser(@Valid @NotNull @RequestBody List<UpdateUserDto> updateDtos) {
        return this.controller.patch(updateDtos);
    }
}
