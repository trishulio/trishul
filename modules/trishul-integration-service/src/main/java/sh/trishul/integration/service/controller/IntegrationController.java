package sh.trishul.integration.service.controller;

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
import sh.trishul.crud.controller.BaseController;
import sh.trishul.crud.controller.CrudControllerService;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.integration.model.AddIntegrationDto;
import sh.trishul.integration.model.BaseIntegration;
import sh.trishul.integration.model.Integration;
import sh.trishul.integration.model.IntegrationDto;
import sh.trishul.integration.model.IntegrationMapper;
import sh.trishul.integration.model.IntegrationStatus;
import sh.trishul.integration.model.IntegrationType;
import sh.trishul.integration.model.UpdateIntegration;
import sh.trishul.integration.model.UpdateIntegrationDto;
import sh.trishul.integration.service.service.IntegrationService;
import sh.trishul.model.base.dto.DeleteResultDto;
import sh.trishul.repo.jpa.repository.model.dto.PageDto;

@RestController
@RequestMapping(path = "/api/v1/integrations")
public class IntegrationController extends BaseController {
  private final CrudControllerService<Long, Integration, BaseIntegration<?>, UpdateIntegration<?>, IntegrationDto, AddIntegrationDto, UpdateIntegrationDto> controller;

  private final IntegrationService integrationService;

  protected IntegrationController(
      CrudControllerService<Long, Integration, BaseIntegration<?>, UpdateIntegration<?>, IntegrationDto, AddIntegrationDto, UpdateIntegrationDto> controller,
      IntegrationService integrationService) {
    this.controller = controller;
    this.integrationService = integrationService;
  }

  @Autowired
  public IntegrationController(IntegrationService integrationService, AttributeFilter filter) {
    this(new CrudControllerService<>(filter, IntegrationMapper.INSTANCE, integrationService,
        "Integration"), integrationService);
  }

  @GetMapping(value = "", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PageDto<IntegrationDto> getAllIntegrations(@RequestParam(required = false) Set<Long> ids,
      @RequestParam(required = false) Set<String> names,
      @RequestParam(required = false) Set<IntegrationType> types,
      @RequestParam(required = false) Set<String> providers,
      @RequestParam(required = false) Set<IntegrationStatus> statuses,
      @RequestParam(name = PROPNAME_SORT_BY,
          defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
      @RequestParam(name = PROPNAME_ORDER_ASC,
          defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
      @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
      @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
      @RequestParam(name = PROPNAME_ATTR,
          defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    Page<Integration> integrationPage = integrationService.getIntegrations(ids, names, types,
        providers, statuses, page, size, sort, orderAscending);

    return this.controller.getAll(integrationPage, attributes);
  }

  @GetMapping(value = "/{integrationId}", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public IntegrationDto getIntegration(
      @PathVariable(required = true, name = "integrationId") Long integrationId,
      @RequestParam(name = PROPNAME_ATTR,
          defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    return this.controller.get(integrationId, attributes);
  }

  @DeleteMapping(value = "", consumes = MediaType.ALL_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public DeleteResultDto deleteIntegrations(@RequestParam("ids") Set<Long> integrationIds) {
    return this.controller.delete(integrationIds);
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public List<IntegrationDto> addIntegrations(
      @Valid @NotNull @RequestBody List<AddIntegrationDto> addDtos) {
    return this.controller.add(addDtos);
  }

  @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<IntegrationDto> updateIntegrations(
      @Valid @NotNull @RequestBody List<UpdateIntegrationDto> updateDtos) {
    return this.controller.put(updateDtos);
  }

  @PatchMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<IntegrationDto> patchIntegrations(
      @Valid @NotNull @RequestBody List<UpdateIntegrationDto> updateDtos) {
    return this.controller.patch(updateDtos);
  }
}
