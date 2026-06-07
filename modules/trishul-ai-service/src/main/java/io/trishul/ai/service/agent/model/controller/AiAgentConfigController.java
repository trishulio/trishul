package io.trishul.ai.service.agent.model.controller;

import io.trishul.ai.agent.model.AddAiAgentConfigDto;
import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.agent.model.AiAgentConfigDto;
import io.trishul.ai.agent.model.AiAgentConfigMapper;
import io.trishul.ai.agent.model.BaseAiAgentConfig;
import io.trishul.ai.agent.model.UpdateAiAgentConfig;
import io.trishul.ai.agent.model.UpdateAiAgentConfigDto;
import io.trishul.ai.service.agent.model.service.AiAgentConfigService;
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
@RequestMapping("/api/v1/ai/agent-configs")
public class AiAgentConfigController extends BaseController {

  private final CrudControllerService<Long, AiAgentConfig, BaseAiAgentConfig<?>, UpdateAiAgentConfig<?>, AiAgentConfigDto, AddAiAgentConfigDto, UpdateAiAgentConfigDto> controller;

  private final AiAgentConfigService service;

  @Autowired
  public AiAgentConfigController(AiAgentConfigService service, AttributeFilter filter) {
    this.service = service;
    this.controller = new CrudControllerService<>(filter, AiAgentConfigMapper.INSTANCE, service,
        "AiAgentConfig");
  }

  @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public AiAgentConfigDto get(@PathVariable("id") Long id,
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
  public List<AiAgentConfigDto> add(@Valid @NotNull @RequestBody List<AddAiAgentConfigDto> addDtos) {
    return this.controller.add(addDtos);
  }

  @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<AiAgentConfigDto> update(
      @Valid @NotNull @RequestBody List<UpdateAiAgentConfigDto> updateDtos) {
    return this.controller.put(updateDtos);
  }

  @PatchMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<AiAgentConfigDto> patch(
      @Valid @NotNull @RequestBody List<UpdateAiAgentConfigDto> updateDtos) {
    return this.controller.patch(updateDtos);
  }
}
