package io.trishul.ai.service.memory.model.controller;

import io.trishul.ai.memory.model.AddAiChatMemoryConfigDto;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfigDto;
import io.trishul.ai.memory.model.AiChatMemoryConfigMapper;
import io.trishul.ai.memory.model.BaseAiChatMemoryConfig;
import io.trishul.ai.memory.model.UpdateAiChatMemoryConfig;
import io.trishul.ai.memory.model.UpdateAiChatMemoryConfigDto;
import io.trishul.ai.service.memory.model.service.AiChatMemoryConfigService;
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
@RequestMapping("/api/v1/ai/chat-memory-configs")
public class AiChatMemoryConfigController extends BaseController {

  private final CrudControllerService<Long, AiChatMemoryConfig, BaseAiChatMemoryConfig<?>, UpdateAiChatMemoryConfig<?>, AiChatMemoryConfigDto, AddAiChatMemoryConfigDto, UpdateAiChatMemoryConfigDto> controller;

  private final AiChatMemoryConfigService service;

  @Autowired
  public AiChatMemoryConfigController(AiChatMemoryConfigService service, AttributeFilter filter) {
    this.service = service;
    this.controller = new CrudControllerService<>(filter, AiChatMemoryConfigMapper.INSTANCE, service,
        "AiChatMemoryConfig");
  }

  @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public AiChatMemoryConfigDto get(@PathVariable("id") Long id,
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
  public List<AiChatMemoryConfigDto> add(
      @Valid @NotNull @RequestBody List<AddAiChatMemoryConfigDto> addDtos) {
    return this.controller.add(addDtos);
  }

  @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<AiChatMemoryConfigDto> update(
      @Valid @NotNull @RequestBody List<UpdateAiChatMemoryConfigDto> updateDtos) {
    return this.controller.put(updateDtos);
  }

  @PatchMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<AiChatMemoryConfigDto> patch(
      @Valid @NotNull @RequestBody List<UpdateAiChatMemoryConfigDto> updateDtos) {
    return this.controller.patch(updateDtos);
  }
}
