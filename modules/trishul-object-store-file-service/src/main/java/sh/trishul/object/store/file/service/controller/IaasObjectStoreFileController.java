package sh.trishul.object.store.file.service.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
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
import sh.trishul.model.base.dto.DeleteResultDto;
import sh.trishul.object.store.file.model.BaseIaasObjectStoreFile;
import sh.trishul.object.store.file.model.IaasObjectStoreFile;
import sh.trishul.object.store.file.model.IaasObjectStoreFileMapper;
import sh.trishul.object.store.file.model.UpdateIaasObjectStoreFile;
import sh.trishul.object.store.file.model.dto.AddIaasObjectStoreFileDto;
import sh.trishul.object.store.file.model.dto.IaasObjectStoreFileDto;
import sh.trishul.object.store.file.model.dto.UpdateIaasObjectStoreFileDto;
import sh.trishul.object.store.file.service.service.IaasObjectStoreFileService;
import sh.trishul.repo.jpa.repository.model.dto.PageDto;

@RestController
@RequestMapping(path = "/api/v1/vfs/files")
public class IaasObjectStoreFileController extends BaseController {
  @SuppressWarnings("unused")
  private static final IaasObjectStoreFileMapper mapper = IaasObjectStoreFileMapper.INSTANCE;

  private final CrudControllerService<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>, IaasObjectStoreFileDto, AddIaasObjectStoreFileDto, UpdateIaasObjectStoreFileDto> controller;

  private final IaasObjectStoreFileService iaasObjectStoreFileService;

  protected IaasObjectStoreFileController(
      CrudControllerService<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>, IaasObjectStoreFileDto, AddIaasObjectStoreFileDto, UpdateIaasObjectStoreFileDto> controller,
      IaasObjectStoreFileService iaasObjectStoreFileService) {
    this.controller = controller;
    this.iaasObjectStoreFileService = iaasObjectStoreFileService;
  }

  @Autowired
  public IaasObjectStoreFileController(IaasObjectStoreFileService iaasObjectStoreFileService,
      AttributeFilter filter) {
    this(new CrudControllerService<>(filter, IaasObjectStoreFileMapper.INSTANCE,
        iaasObjectStoreFileService, "IaasObjectStoreFile"), iaasObjectStoreFileService);
  }

  @GetMapping(value = "", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public List<IaasObjectStoreFileDto> getAll(@RequestParam(name = "files") Set<URI> files) {
    final List<IaasObjectStoreFile> objectStoreFiles
        = this.iaasObjectStoreFileService.getAll(files);

    return objectStoreFiles.stream().map(IaasObjectStoreFileMapper.INSTANCE::toDto).toList();
  }

  @GetMapping(value = "/{fileId}", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public IaasObjectStoreFileDto getIaasObjectStoreFile(@PathVariable(required = true) URI fileId,
      @RequestParam(name = PROPNAME_ATTR,
          defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    return this.controller.get(fileId, attributes);
  }

  @DeleteMapping(value = "", consumes = MediaType.ALL_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public DeleteResultDto deleteIaasObjectStoreFiles(@RequestParam("fileIds") Set<URI> fileIds) {
    return this.controller.delete(fileIds);
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public List<IaasObjectStoreFileDto> addIaasObjectStoreFile(
      @Valid @NotNull @RequestBody List<AddIaasObjectStoreFileDto> addDtos) {
    return this.controller.add(addDtos);
  }

  @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<IaasObjectStoreFileDto> updateIaasObjectStoreFile(
      @Valid @NotNull @RequestBody List<UpdateIaasObjectStoreFileDto> updateDtos) {
    return this.controller.put(updateDtos);
  }

  @PatchMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public List<IaasObjectStoreFileDto> patchIaasObjectStoreFile(
      @Valid @NotNull @RequestBody List<UpdateIaasObjectStoreFileDto> updateDtos) {
    return this.controller.patch(updateDtos);
  }

  @GetMapping(value = "/search", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PageDto<IaasObjectStoreFileDto> search(
      @RequestParam(name = "q", required = false) String query,
      @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
      @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
      @RequestParam(name = PROPNAME_SORT_BY,
          defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
      @RequestParam(name = PROPNAME_ORDER_ASC,
          defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
      @RequestParam(name = PROPNAME_ATTR,
          defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
    Page<IaasObjectStoreFile> entityPage
        = iaasObjectStoreFileService.search(query, sort, orderAscending, page, size);
    return this.controller.getAll(entityPage, attributes);
  }
}
