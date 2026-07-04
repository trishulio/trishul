package io.trishul.ai.service.chat.model.controller;

import io.trishul.ai.chat.model.AddAiChatModelConfigDto;
import io.trishul.ai.chat.model.AiChatModelConfig;
import io.trishul.ai.chat.model.AiChatModelConfigDto;
import io.trishul.ai.chat.model.AiChatModelConfigMapper;
import io.trishul.ai.chat.model.BaseAiChatModelConfig;
import io.trishul.ai.chat.model.UpdateAiChatModelConfig;
import io.trishul.ai.chat.model.UpdateAiChatModelConfigDto;
import io.trishul.ai.service.chat.model.service.AiChatModelConfigService;
import io.trishul.crud.controller.BaseController;
import io.trishul.crud.controller.CrudControllerService;
import io.trishul.crud.controller.filter.AttributeFilter;
import io.trishul.model.base.dto.DeleteResultDto;
import io.trishul.repo.jpa.repository.model.dto.PageDto;
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
@RequestMapping("/api/v1/ai/chat-model-configs")
public class AiChatModelConfigController extends BaseController {

  private final CrudControllerService<Long, AiChatModelConfig, BaseAiChatModelConfig<?>, UpdateAiChatModelConfig<?>, AiChatModelConfigDto, AddAiChatModelConfigDto, UpdateAiChatModelConfigDto> controller;

  private final AiChatModelConfigService service;

  protected AiChatModelConfigController(
      CrudControllerService<Long, AiChatModelConfig, BaseAiChatModelConfig<?>, UpdateAiChatModelConfig<?>, AiChatModelConfigDto, AddAiChatModelConfigDto, UpdateAiChatModelConfigDto> controller,
      AiChatModelConfigService service) {
    this.controller = controller;
    this.service = service;
  }

  @Autowired
  public AiChatModelConfigController(AiChatModelConfigService service, AttributeFilter filter) {
    this(new CrudControllerService<>(filter, AiChatModelConfigMapper.INSTANCE, service,
        "AiChatModelConfig"), service);
  }

  @GetMapping(value = "", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PageDto<AiChatModelConfigDto> getAll(
      @RequestParam(name = "ids", required = false) Set<Long> ids,
      @RequestParam(name = "names", required = false) Set<String> names,
      @RequestParam(name = "providers", required = false) Set<String> providers,
      @RequestParam(name = "is_default", required = false) Boolean isDefault,
      @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
      @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
      @RequestParam(name = PROPNAME_SORT_BY,
          defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
      @RequestParam(name = PROPNAME_ORDER_ASC,
          defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
      @RequestParam(name = PROPNAME_ATTR,
          defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    Page<AiChatModelConfig> entityPage = service.getChatModelConfigs(ids, names, providers,
        isDefault, page, size, sort, orderAscending);
    return this.controller.getAll(entityPage, attributes);
  }

  @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public AiChatModelConfigDto get(@PathVariable("id") Long id, @RequestParam(name = PROPNAME_ATTR,
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
  public List<AiChatModelConfigDto> add(
      @Valid @NotNull @RequestBody List<AddAiChatModelConfigDto> addDtos) {
    return this.controller.add(addDtos);
  }

  @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<AiChatModelConfigDto> update(
      @Valid @NotNull @RequestBody List<UpdateAiChatModelConfigDto> updateDtos) {
    return this.controller.put(updateDtos);
  }

  @PatchMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<AiChatModelConfigDto> patch(
      @Valid @NotNull @RequestBody List<UpdateAiChatModelConfigDto> updateDtos) {
    return this.controller.patch(updateDtos);
  }
}
