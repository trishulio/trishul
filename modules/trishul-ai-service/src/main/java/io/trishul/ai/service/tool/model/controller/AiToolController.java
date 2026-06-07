package io.trishul.ai.service.tool.model.controller;

import io.trishul.ai.service.tool.model.service.AiToolService;
import io.trishul.ai.tool.model.AddAiToolDto;
import io.trishul.ai.tool.model.AiTool;
import io.trishul.ai.tool.model.AiToolDto;
import io.trishul.ai.tool.model.AiToolMapper;
import io.trishul.ai.tool.model.BaseAiTool;
import io.trishul.ai.tool.model.UpdateAiTool;
import io.trishul.ai.tool.model.UpdateAiToolDto;
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
@RequestMapping("/api/v1/ai/tools")
public class AiToolController extends BaseController {

  private final CrudControllerService<Long, AiTool, BaseAiTool<?>, UpdateAiTool<?>, AiToolDto, AddAiToolDto, UpdateAiToolDto> controller;

  @Autowired
  public AiToolController(AiToolService service, AttributeFilter filter) {
    this(new CrudControllerService<>(filter, AiToolMapper.INSTANCE, service, "AiTool"));
  }

  public AiToolController(
      CrudControllerService<Long, AiTool, BaseAiTool<?>, UpdateAiTool<?>, AiToolDto, AddAiToolDto, UpdateAiToolDto> controller) {
    super();
    this.controller = controller;
  }

  @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public AiToolDto get(@PathVariable(required = true, name = "id") Long id,
      @RequestParam(name = PROPNAME_ATTR,
          defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    return this.controller.get(id, attributes);
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public List<AiToolDto> add(@Valid @NotNull @RequestBody List<AddAiToolDto> addDtos) {
    return this.controller.add(addDtos);
  }

  @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<AiToolDto> put(@Valid @NotNull @RequestBody List<UpdateAiToolDto> updateDtos) {
    return this.controller.put(updateDtos);
  }

  @PatchMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<AiToolDto> patch(@Valid @NotNull @RequestBody List<UpdateAiToolDto> updateDtos) {
    return this.controller.patch(updateDtos);
  }

  @DeleteMapping(value = "", consumes = MediaType.ALL_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public long delete(@RequestParam("ids") Set<Long> ids) {
    return this.controller.delete(ids);
  }
}
