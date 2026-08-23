package sh.trishul.ai.service.session.model.controller;

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
import sh.trishul.ai.service.session.model.service.AiChatSessionService;
import sh.trishul.ai.session.model.AddAiChatSessionDto;
import sh.trishul.ai.session.model.AiChatSession;
import sh.trishul.ai.session.model.AiChatSessionDto;
import sh.trishul.ai.session.model.AiChatSessionMapper;
import sh.trishul.ai.session.model.BaseAiChatSession;
import sh.trishul.ai.session.model.UpdateAiChatSession;
import sh.trishul.ai.session.model.UpdateAiChatSessionDto;
import sh.trishul.crud.controller.BaseController;
import sh.trishul.crud.controller.CrudControllerService;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.model.base.dto.DeleteResultDto;
import sh.trishul.repo.jpa.repository.model.dto.PageDto;

@RestController
@RequestMapping("/api/v1/ai/chat-sessions")
public class AiChatSessionController extends BaseController {

  private final CrudControllerService<Long, AiChatSession, BaseAiChatSession<?>, UpdateAiChatSession<?>, AiChatSessionDto, AddAiChatSessionDto, UpdateAiChatSessionDto> controller;

  private final AiChatSessionService service;

  protected AiChatSessionController(
      CrudControllerService<Long, AiChatSession, BaseAiChatSession<?>, UpdateAiChatSession<?>, AiChatSessionDto, AddAiChatSessionDto, UpdateAiChatSessionDto> controller,
      AiChatSessionService service) {
    this.controller = controller;
    this.service = service;
  }

  @Autowired
  public AiChatSessionController(AiChatSessionService service, AttributeFilter filter) {
    this(
        new CrudControllerService<>(filter, AiChatSessionMapper.INSTANCE, service, "AiChatSession"),
        service);
  }

  @GetMapping(value = "", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PageDto<AiChatSessionDto> getAll(
      @RequestParam(name = "ids", required = false) Set<Long> ids,
      @RequestParam(name = "session_keys", required = false) Set<String> sessionKeys,
      @RequestParam(name = "titles", required = false) Set<String> titles,
      @RequestParam(name = "is_active", required = false) Boolean isActive,
      @RequestParam(name = "agent_config_ids", required = false) Set<Long> agentConfigIds,
      @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
      @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
      @RequestParam(name = PROPNAME_SORT_BY,
          defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
      @RequestParam(name = PROPNAME_ORDER_ASC,
          defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
      @RequestParam(name = PROPNAME_ATTR,
          defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    Page<AiChatSession> entityPage = service.getChatSessions(ids, sessionKeys, titles, isActive,
        agentConfigIds, page, size, sort, orderAscending);
    return this.controller.getAll(entityPage, attributes);
  }

  @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public AiChatSessionDto get(@PathVariable("id") Long id, @RequestParam(name = PROPNAME_ATTR,
      defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    return this.controller.get(id, attributes);
  }

  @DeleteMapping(value = "", consumes = MediaType.ALL_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public DeleteResultDto delete(@RequestParam("ids") Set<Long> ids) {
    return this.controller.delete(ids);
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public List<AiChatSessionDto> add(
      @Valid @NotNull @RequestBody List<AddAiChatSessionDto> addDtos) {
    return this.controller.add(addDtos);
  }

  @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<AiChatSessionDto> update(
      @Valid @NotNull @RequestBody List<UpdateAiChatSessionDto> updateDtos) {
    return this.controller.put(updateDtos);
  }

  @PatchMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<AiChatSessionDto> patch(
      @Valid @NotNull @RequestBody List<UpdateAiChatSessionDto> updateDtos) {
    return this.controller.patch(updateDtos);
  }

  @GetMapping(value = "/search", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PageDto<AiChatSessionDto> search(@RequestParam(name = "q", required = false) String query,
      @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
      @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
      @RequestParam(name = PROPNAME_SORT_BY,
          defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
      @RequestParam(name = PROPNAME_ORDER_ASC,
          defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
      @RequestParam(name = PROPNAME_ATTR,
          defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    Page<AiChatSession> entityPage = service.search(query, sort, orderAscending, page, size);
    return this.controller.getAll(entityPage, attributes);
  }
}
