package sh.trishul.ai.service.tool.model.controller;

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
import sh.trishul.ai.service.tool.model.service.AiToolService;
import sh.trishul.ai.tool.model.AddAiToolDto;
import sh.trishul.ai.tool.model.AiTool;
import sh.trishul.ai.tool.model.AiToolDto;
import sh.trishul.ai.tool.model.AiToolMapper;
import sh.trishul.ai.tool.model.BaseAiTool;
import sh.trishul.ai.tool.model.UpdateAiTool;
import sh.trishul.ai.tool.model.UpdateAiToolDto;
import sh.trishul.crud.controller.BaseController;
import sh.trishul.crud.controller.CrudControllerService;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.model.base.dto.DeleteResultDto;
import sh.trishul.repo.jpa.repository.model.dto.PageDto;

@RestController
@RequestMapping("/api/v1/ai/tools")
public class AiToolController extends BaseController {

  private final CrudControllerService<Long, AiTool, BaseAiTool<?>, UpdateAiTool<?>, AiToolDto, AddAiToolDto, UpdateAiToolDto> controller;
  private final AiToolService service;

  @Autowired
  public AiToolController(AiToolService service, AttributeFilter filter) {
    this(new CrudControllerService<>(filter, AiToolMapper.INSTANCE, service, "AiTool"), service);
  }

  public AiToolController(
      CrudControllerService<Long, AiTool, BaseAiTool<?>, UpdateAiTool<?>, AiToolDto, AddAiToolDto, UpdateAiToolDto> controller,
      AiToolService service) {
    super();
    this.controller = controller;
    this.service = service;
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

  @GetMapping(value = "/search", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PageDto<AiToolDto> search(
      @RequestParam(name = "q", required = false) String query,
      @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
      @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
      @RequestParam(name = PROPNAME_SORT_BY,
          defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
      @RequestParam(name = PROPNAME_ORDER_ASC,
          defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
      @RequestParam(name = PROPNAME_ATTR,
          defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    Page<AiTool> entityPage
        = service.search(query, sort, orderAscending, page, size);
    return this.controller.getAll(entityPage, attributes);
  }

  @DeleteMapping(value = "", consumes = MediaType.ALL_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public DeleteResultDto delete(@RequestParam("ids") Set<Long> ids) {
    return this.controller.delete(ids);
  }
}
