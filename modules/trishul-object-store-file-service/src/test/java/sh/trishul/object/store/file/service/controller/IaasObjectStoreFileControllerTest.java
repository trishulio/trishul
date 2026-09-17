package sh.trishul.object.store.file.service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import sh.trishul.crud.controller.CrudControllerService;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.model.base.dto.DeleteResultDto;
import sh.trishul.object.store.file.model.BaseIaasObjectStoreFile;
import sh.trishul.object.store.file.model.IaasObjectStoreFile;
import sh.trishul.object.store.file.model.UpdateIaasObjectStoreFile;
import sh.trishul.object.store.file.model.dto.AddIaasObjectStoreFileDto;
import sh.trishul.object.store.file.model.dto.IaasObjectStoreFileDto;
import sh.trishul.object.store.file.model.dto.UpdateIaasObjectStoreFileDto;
import sh.trishul.object.store.file.service.service.IaasObjectStoreFileService;
import sh.trishul.repo.jpa.repository.model.dto.PageDto;

class IaasObjectStoreFileControllerTest {
  private IaasObjectStoreFileController controller;

  private CrudControllerService<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>, IaasObjectStoreFileDto, AddIaasObjectStoreFileDto, UpdateIaasObjectStoreFileDto> mCrudController;

  private IaasObjectStoreFileService mService;

  @BeforeEach
  void init() {
    this.mCrudController = mock(CrudControllerService.class);
    this.mService = mock(IaasObjectStoreFileService.class);
    this.controller = new IaasObjectStoreFileController(mCrudController, mService);
  }

  @Test
  void testGetAllIaasObjectStoreFile_ReturnsDtosFromController() {
    doReturn(List.of(new IaasObjectStoreFile(URI.create("file_1.txt")))).when(mService)
        .getAll(Set.of(URI.create("file_1.txt")));

    List<IaasObjectStoreFileDto> page = this.controller.getAll(Set.of(URI.create("file_1.txt")));

    List<IaasObjectStoreFileDto> expected
        = List.of(new IaasObjectStoreFileDto(URI.create("file_1.txt")));
    assertEquals(expected, page);
  }

  @Test
  void testGetIaasObjectStoreFile_ReturnsDtoFromController() {
    doReturn(new IaasObjectStoreFileDto(URI.create("file_1.txt"))).when(mCrudController)
        .get(URI.create("file_1.txt"), Set.of(""));

    IaasObjectStoreFileDto dto
        = this.controller.getIaasObjectStoreFile(URI.create("file_1.txt"), Set.of(""));

    IaasObjectStoreFileDto expected = new IaasObjectStoreFileDto(URI.create("file_1.txt"));
    assertEquals(expected, dto);
  }

  @Test
  void testDeleteIaasObjectStoreFiles_ReturnsDeleteCountFromController() {
    doReturn(new DeleteResultDto(1L)).when(mCrudController)
        .delete(Set.of(URI.create("file_1.txt")));

    assertEquals(new DeleteResultDto(1L),
        this.controller.deleteIaasObjectStoreFiles(Set.of(URI.create("file_1.txt"))));
  }

  @Test
  void testAddIaasObjectStoreFiles_AddsToControllerAndReturnsListOfDtos() {
    doReturn(List.of(new IaasObjectStoreFileDto(URI.create("file_1.txt")))).when(mCrudController)
        .add(List.of(new AddIaasObjectStoreFileDto()));

    List<IaasObjectStoreFileDto> dtos
        = this.controller.addIaasObjectStoreFile(List.of(new AddIaasObjectStoreFileDto()));

    assertEquals(List.of(new IaasObjectStoreFileDto(URI.create("file_1.txt"))), dtos);
  }

  @Test
  void testUpdateIaasObjectStoreFiles_PutsToControllerAndReturnsListOfDtos() {
    doReturn(List.of(new IaasObjectStoreFileDto(URI.create("file_1.txt")))).when(mCrudController)
        .put(List.of(new UpdateIaasObjectStoreFileDto(URI.create("file_1.txt"))));

    List<IaasObjectStoreFileDto> dtos = this.controller.updateIaasObjectStoreFile(
        List.of(new UpdateIaasObjectStoreFileDto(URI.create("file_1.txt"))));

    assertEquals(List.of(new IaasObjectStoreFileDto(URI.create("file_1.txt"))), dtos);
  }

  @Test
  void testPatchIaasObjectStoreFiles_PatchToControllerAndReturnsListOfDtos() {
    doReturn(List.of(new IaasObjectStoreFileDto(URI.create("file_1.txt")))).when(mCrudController)
        .patch(List.of(new UpdateIaasObjectStoreFileDto(URI.create("file_1.txt"))));

    List<IaasObjectStoreFileDto> dtos = this.controller.patchIaasObjectStoreFile(
        List.of(new UpdateIaasObjectStoreFileDto(URI.create("file_1.txt"))));

    assertEquals(List.of(new IaasObjectStoreFileDto(URI.create("file_1.txt"))), dtos);
  }

  @Test
  void testAutowiredConstructor() {
    AttributeFilter filter = mock(AttributeFilter.class);
    IaasObjectStoreFileController controller = new IaasObjectStoreFileController(mService, filter);
    assertNotNull(controller);
  }

  @Test
  void testSearch_ReturnsPageOfDtosFromController() {
    Page<IaasObjectStoreFile> entityPage
        = new PageImpl<>(List.of(new IaasObjectStoreFile(URI.create("file_1.txt"))));
    PageDto<IaasObjectStoreFileDto> dtoPage
        = new PageDto<>(List.of(new IaasObjectStoreFileDto(URI.create("file_1.txt"))), 1, 1);

    doReturn(entityPage).when(mService).search("query", new TreeSet<>(List.of("id")), true, 0, 10);
    doReturn(dtoPage).when(mCrudController).getAll(entityPage, Set.of("all"));

    PageDto<IaasObjectStoreFileDto> result
        = this.controller.search("query", 0, 10, new TreeSet<>(List.of("id")), true, Set.of("all"));

    assertEquals(dtoPage, result);
  }
}
