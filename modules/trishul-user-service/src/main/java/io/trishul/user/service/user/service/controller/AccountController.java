package io.trishul.user.service.user.service.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.trishul.crud.controller.BaseController;
import io.trishul.user.model.User;
import io.trishul.user.model.UserDto;
import io.trishul.user.model.UserMapper;
import io.trishul.user.service.user.service.service.AccountService;

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
  public UserDto getCurrentUser(
      @RequestParam(name = PROPNAME_ATTR,
          defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    User user = accountService.getCurrentUser();

    return UserMapper.INSTANCE.toDto(user);
  }
}
