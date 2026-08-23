package sh.trishul.user.service.user.service.salutation.controller;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sh.trishul.crud.controller.BaseController;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.repo.jpa.repository.model.dto.PageDto;
import sh.trishul.user.salutation.model.UserSalutation;
import sh.trishul.user.salutation.model.UserSalutationDto;
import sh.trishul.user.salutation.model.UserSalutationMapper;
import sh.trishul.user.service.user.service.salutation.service.UserSalutationService;

@RestController
@RequestMapping(path = "/api/v1/users/salutations")
public class UserSalutationController extends BaseController {
  private final UserSalutationService userSalutationService;

  private final UserSalutationMapper userSalutationMapper = UserSalutationMapper.INSTANCE;

  public UserSalutationController(UserSalutationService userService, AttributeFilter filter) {
    super(filter);
    this.userSalutationService = userService;
  }

  @GetMapping(value = "", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PageDto<UserSalutationDto> getSalutations(@RequestParam(required = false) Set<Long> ids,
      @RequestParam(name = PROPNAME_SORT_BY,
          defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
      @RequestParam(name = PROPNAME_ORDER_ASC,
          defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
      @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
      @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size) {
    Page<UserSalutation> userSalutationPage
        = userSalutationService.getSalutations(ids, sort, orderAscending, page, size);

    List<UserSalutationDto> userSalutations = userSalutationPage.stream()
        .map(salutation -> userSalutationMapper.toDto(salutation)).toList();

    PageDto<UserSalutationDto> dto = new PageDto<>(userSalutations,
        userSalutationPage.getTotalPages(), userSalutationPage.getTotalElements());

    return dto;
  }

  @GetMapping(value = "/search", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PageDto<UserSalutationDto> search(@RequestParam(name = "q", required = false) String query,
      @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
      @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
      @RequestParam(name = PROPNAME_SORT_BY,
          defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
      @RequestParam(name = PROPNAME_ORDER_ASC,
          defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending) {
    Page<UserSalutation> userSalutationPage
        = userSalutationService.search(query, sort, orderAscending, page, size);

    List<UserSalutationDto> userSalutations = userSalutationPage.stream()
        .map(salutation -> userSalutationMapper.toDto(salutation)).toList();

    return new PageDto<>(userSalutations, userSalutationPage.getTotalPages(),
        userSalutationPage.getTotalElements());
  }
}
