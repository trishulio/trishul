package io.trishul.ai.service.guardrail.model.controller;

import io.trishul.ai.guardrail.model.AddAiGuardrailDto;
import io.trishul.ai.guardrail.model.AiGuardrail;
import io.trishul.ai.guardrail.model.AiGuardrailDto;
import io.trishul.ai.guardrail.model.AiGuardrailMapper;
import io.trishul.ai.guardrail.model.BaseAiGuardrail;
import io.trishul.ai.guardrail.model.UpdateAiGuardrail;
import io.trishul.ai.guardrail.model.UpdateAiGuardrailDto;
import io.trishul.ai.service.guardrail.model.service.AiGuardrailService;
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
@RequestMapping("/api/v1/ai/guardrails")
public class AiGuardrailController extends BaseController {

  private final CrudControllerService<Long, AiGuardrail, BaseAiGuardrail<?>, UpdateAiGuardrail<?>, AiGuardrailDto, AddAiGuardrailDto, UpdateAiGuardrailDto> controller;

  private final AiGuardrailService service;

  @Autowired
  public AiGuardrailController(AiGuardrailService service, AttributeFilter filter) {
    this.service = service;
    this.controller = new CrudControllerService<>(filter, AiGuardrailMapper.INSTANCE, service,
        "AiGuardrail");
  }

  @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public AiGuardrailDto get(@PathVariable("id") Long id,
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
  public List<AiGuardrailDto> add(@Valid @NotNull @RequestBody List<AddAiGuardrailDto> addDtos) {
    return this.controller.add(addDtos);
  }

  @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<AiGuardrailDto> update(
      @Valid @NotNull @RequestBody List<UpdateAiGuardrailDto> updateDtos) {
    return this.controller.put(updateDtos);
  }

  @PatchMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<AiGuardrailDto> patch(@Valid @NotNull @RequestBody List<UpdateAiGuardrailDto> updateDtos) {
    return this.controller.patch(updateDtos);
  }
}
