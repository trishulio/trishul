package sh.trishul.user.service.user.service.controller;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sh.trishul.crud.controller.BaseController;
import sh.trishul.repo.jpa.repository.model.dto.PageDto;
import sh.trishul.user.model.User;
import sh.trishul.user.model.UserDto;
import sh.trishul.user.model.UserMapper;
import sh.trishul.user.service.user.service.service.AccountService;

@RestController
@RequestMapping(path = "/api/v1/account")
public class AccountController extends BaseController {
  private final AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping(value = "/me", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDto getCurrentUser(@RequestParam(name = PROPNAME_ATTR,
      defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    User user = accountService.getCurrentUser();

    return UserMapper.INSTANCE.toDto(user);
  }

  @GetMapping(value = "/search", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PageDto<UserDto> search(@RequestParam(name = "q", required = false) String query,
      @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
      @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
      @RequestParam(name = PROPNAME_SORT_BY,
          defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
      @RequestParam(name = PROPNAME_ORDER_ASC,
          defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending) {
    Page<User> userPage = accountService.search(query, sort, orderAscending, page, size);

    List<UserDto> users = userPage.stream().map(user -> UserMapper.INSTANCE.toDto(user)).toList();

    return new PageDto<>(users, userPage.getTotalPages(), userPage.getTotalElements());
  }
}
