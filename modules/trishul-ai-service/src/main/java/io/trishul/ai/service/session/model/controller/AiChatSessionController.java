package io.trishul.ai.service.session.model.controller;

import io.trishul.ai.service.session.model.service.AiChatSessionService;
import io.trishul.ai.session.model.AddAiChatSessionDto;
import io.trishul.ai.session.model.AiChatSession;
import io.trishul.ai.session.model.AiChatSessionDto;
import io.trishul.ai.session.model.AiChatSessionMapper;
import io.trishul.ai.session.model.BaseAiChatSession;
import io.trishul.ai.session.model.UpdateAiChatSession;
import io.trishul.ai.session.model.UpdateAiChatSessionDto;
import io.trishul.crud.controller.BaseController;
import io.trishul.crud.controller.CrudControllerService;
import io.trishul.crud.controller.filter.AttributeFilter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/v1/ai/chat-sessions")
public class AiChatSessionController extends BaseController {

  private final CrudControllerService<Long, AiChatSession, BaseAiChatSession<?>, UpdateAiChatSession<?>, AiChatSessionDto, AddAiChatSessionDto, UpdateAiChatSessionDto> controller;

  private final AiChatSessionService service;

  @Autowired
  public AiChatSessionController(AiChatSessionService service, AttributeFilter filter) {
    this.service = service;
    this.controller = new CrudControllerService<>(filter, AiChatSessionMapper.INSTANCE, service,
        "AiChatSession");
  }

  @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public AiChatSessionDto get(@PathVariable("id") Long id,
      @RequestParam(name = PROPNAME_ATTR,
          defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    return this.controller.get(id, attributes);
  }

  @DeleteMapping(value = "", consumes = MediaType.ALL_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public long delete(@RequestParam("ids") Set<Long> ids) {
    return this.controller.delete(ids);
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public List<AiChatSessionDto> add(@Valid @NotNull @RequestBody List<AddAiChatSessionDto> addDtos) {
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
}
