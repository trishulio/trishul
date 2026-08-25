package sh.trishul.tenant.service.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.UUID;
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
import sh.trishul.model.base.dto.DeleteResultDto;
import sh.trishul.repo.jpa.repository.model.dto.PageDto;
import sh.trishul.tenant.dto.AddTenantDto;
import sh.trishul.tenant.dto.TenantDto;
import sh.trishul.tenant.dto.UpdateTenantDto;
import sh.trishul.tenant.entity.BaseTenant;
import sh.trishul.tenant.entity.Tenant;
import sh.trishul.tenant.entity.TenantMapper;
import sh.trishul.tenant.entity.UpdateTenant;
import sh.trishul.tenant.service.service.TenantService;

@RestController
@RequestMapping(path = "/operations/tenants")
public class TenantController extends BaseController {
  private final TenantService tenantService;

  private final CrudControllerService<UUID, Tenant, BaseTenant<?>, UpdateTenant<?>, TenantDto, AddTenantDto, UpdateTenantDto> controller;

  @Autowired
  public TenantController(TenantService tenantService, AttributeFilter filter) {
    this(new CrudControllerService<>(filter, TenantMapper.INSTANCE, tenantService, "Tenant"),
        tenantService);
  }

  public TenantController(
      CrudControllerService<UUID, Tenant, BaseTenant<?>, UpdateTenant<?>, TenantDto, AddTenantDto, UpdateTenantDto> controller,
      TenantService tenantService) {
    super();
    this.controller = controller;
    this.tenantService = tenantService;
  }

  @GetMapping(value = "", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PageDto<TenantDto> getAll(@RequestParam(required = false, name = "ids") Set<UUID> ids,
      @RequestParam(required = false, name = "names") Set<String> names,
      @RequestParam(required = false, name = "urls") Set<URI> urls,
      @RequestParam(required = false, name = "is_ready") Boolean isReady,
      @RequestParam(name = PROPNAME_SORT_BY,
          defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
      @RequestParam(name = PROPNAME_ORDER_ASC,
          defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
      @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
      @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
      @RequestParam(name = PROPNAME_ATTR,
          defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    Page<Tenant> tenants
        = this.tenantService.getAll(ids, names, urls, isReady, sort, orderAscending, page, size);

    return this.controller.getAll(tenants, attributes);
  }

  @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public TenantDto getTenant(@PathVariable(required = true, name = "id") UUID id,
      @RequestParam(name = PROPNAME_ATTR,
          defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    return this.controller.get(id, attributes);
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public List<TenantDto> addTenant(@Valid @NotNull @RequestBody List<AddTenantDto> addDtos) {
    return this.controller.add(addDtos);
  }

  @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<TenantDto> updateTenant(
      @Valid @NotNull @RequestBody List<UpdateTenantDto> updateDtos) {
    return this.controller.put(updateDtos);
  }

  @PatchMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<TenantDto> patchTenant(
      @Valid @NotNull @RequestBody List<UpdateTenantDto> updateDtos) {
    return this.controller.patch(updateDtos);
  }

  @DeleteMapping(value = "", consumes = MediaType.ALL_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public DeleteResultDto deleteTenants(@RequestParam("ids") Set<UUID> ids) {
    return this.controller.delete(ids);
  }

  @GetMapping(value = "/search", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PageDto<TenantDto> search(@RequestParam(name = "q", required = false) String query,
      @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
      @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
      @RequestParam(name = PROPNAME_SORT_BY,
          defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
      @RequestParam(name = PROPNAME_ORDER_ASC,
          defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
      @RequestParam(name = PROPNAME_ATTR,
          defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    Page<Tenant> entityPage = tenantService.search(query, sort, orderAscending, page, size);
    return this.controller.getAll(entityPage, attributes);
  }
}
