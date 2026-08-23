package sh.trishul.ai.service.skill.model.controller;

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
import sh.trishul.ai.service.skill.model.service.AiSkillService;
import sh.trishul.ai.skill.model.AddAiSkillDto;
import sh.trishul.ai.skill.model.AiSkill;
import sh.trishul.ai.skill.model.AiSkillDto;
import sh.trishul.ai.skill.model.AiSkillMapper;
import sh.trishul.ai.skill.model.BaseAiSkill;
import sh.trishul.ai.skill.model.UpdateAiSkill;
import sh.trishul.ai.skill.model.UpdateAiSkillDto;
import sh.trishul.crud.controller.BaseController;
import sh.trishul.crud.controller.CrudControllerService;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.model.base.dto.DeleteResultDto;

@RestController
@RequestMapping("/api/v1/ai/skills")
public class AiSkillController extends BaseController {

  private final CrudControllerService<Long, AiSkill, BaseAiSkill<?>, UpdateAiSkill<?>, AiSkillDto, AddAiSkillDto, UpdateAiSkillDto> controller;
  private final AiSkillService service;

  @Autowired
  public AiSkillController(AiSkillService service, AttributeFilter filter) {
    this(new CrudControllerService<>(filter, AiSkillMapper.INSTANCE, service, "AiSkill"), service);
  }

  public AiSkillController(
      CrudControllerService<Long, AiSkill, BaseAiSkill<?>, UpdateAiSkill<?>, AiSkillDto, AddAiSkillDto, UpdateAiSkillDto> controller,
      AiSkillService service) {
    super();
    this.controller = controller;
    this.service = service;
  }

  @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public AiSkillDto get(@PathVariable(required = true, name = "id") Long id,
      @RequestParam(name = PROPNAME_ATTR,
          defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    return this.controller.get(id, attributes);
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public List<AiSkillDto> add(@Valid @NotNull @RequestBody List<AddAiSkillDto> addDtos) {
    return this.controller.add(addDtos);
  }

  @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<AiSkillDto> put(@Valid @NotNull @RequestBody List<UpdateAiSkillDto> updateDtos) {
    return this.controller.put(updateDtos);
  }

  @PatchMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<AiSkillDto> patch(@Valid @NotNull @RequestBody List<UpdateAiSkillDto> updateDtos) {
    return this.controller.patch(updateDtos);
  }

  @GetMapping(value = "/search", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PageDto<AiSkillDto> search(
      @RequestParam(name = "q", required = false) String query,
      @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
      @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
      @RequestParam(name = PROPNAME_SORT_BY,
          defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
      @RequestParam(name = PROPNAME_ORDER_ASC,
          defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
      @RequestParam(name = PROPNAME_ATTR,
          defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    Page<AiSkill> entityPage
        = service.search(query, sort, orderAscending, page, size);
    return this.controller.getAll(entityPage, attributes);
  }

  @DeleteMapping(value = "", consumes = MediaType.ALL_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public DeleteResultDto delete(@RequestParam("ids") Set<Long> ids) {
    return this.controller.delete(ids);
  }
}
