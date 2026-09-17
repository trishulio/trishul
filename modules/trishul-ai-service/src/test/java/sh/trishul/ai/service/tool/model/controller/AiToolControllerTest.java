package sh.trishul.ai.service.tool.model.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import sh.trishul.ai.service.tool.model.service.AiToolService;
import sh.trishul.ai.tool.model.AddAiToolDto;
import sh.trishul.ai.tool.model.AiTool;
import sh.trishul.ai.tool.model.AiToolDto;
import sh.trishul.ai.tool.model.BaseAiTool;
import sh.trishul.ai.tool.model.UpdateAiTool;
import sh.trishul.ai.tool.model.UpdateAiToolDto;
import sh.trishul.crud.controller.CrudControllerService;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.model.base.dto.DeleteResultDto;
import sh.trishul.repo.jpa.repository.model.dto.PageDto;

class AiToolControllerTest {
  private AiToolController controller;

  private CrudControllerService<Long, AiTool, BaseAiTool<?>, UpdateAiTool<?>, AiToolDto, AddAiToolDto, UpdateAiToolDto> mCrudController;

  private AiToolService mService;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void init() {
    this.mCrudController = mock(CrudControllerService.class);
    this.mService = mock(AiToolService.class);
    this.controller = new AiToolController(mCrudController, mService);
  }

  @Test
  void testAutowiredConstructor() {
    AttributeFilter filter = mock(AttributeFilter.class);
    AiToolController autowiredController = new AiToolController(mService, filter);
    assertNotNull(autowiredController);
  }

  @Test
  void testGet_ReturnsDtoFromController() {
    AiToolDto dto = new AiToolDto(1L);
    doReturn(dto).when(mCrudController).get(1L, Set.of("id"));

    AiToolDto result = this.controller.get(1L, Set.of("id"));

    assertEquals(dto, result);
  }

  @Test
  void testDelete_ReturnsDeleteCountFromController() {
    doReturn(new DeleteResultDto(1L)).when(mCrudController).delete(Set.of(1L));

    assertEquals(new DeleteResultDto(1L), this.controller.delete(Set.of(1L)));
  }

  @Test
  void testAdd_AddsToControllerAndReturnsListOfDtos() {
    AddAiToolDto addDto = new AddAiToolDto();
    AiToolDto dto = new AiToolDto(1L);
    doReturn(List.of(dto)).when(mCrudController).add(List.of(addDto));

    List<AiToolDto> dtos = this.controller.add(List.of(addDto));

    assertEquals(List.of(dto), dtos);
  }

  @Test
  void testPut_PutsToControllerAndReturnsListOfDtos() {
    UpdateAiToolDto updateDto = new UpdateAiToolDto();
    AiToolDto dto = new AiToolDto(1L);
    doReturn(List.of(dto)).when(mCrudController).put(List.of(updateDto));

    List<AiToolDto> dtos = this.controller.put(List.of(updateDto));

    assertEquals(List.of(dto), dtos);
  }

  @Test
  void testPatch_PatchToControllerAndReturnsListOfDtos() {
    UpdateAiToolDto patchDto = new UpdateAiToolDto();
    AiToolDto dto = new AiToolDto(1L);
    doReturn(List.of(dto)).when(mCrudController).patch(List.of(patchDto));

    List<AiToolDto> dtos = this.controller.patch(List.of(patchDto));

    assertEquals(List.of(dto), dtos);
  }

  @Test
  void testSearch_ReturnsDtosFromController() {
    AiTool entity = new AiTool(1L);
    AiToolDto dto = new AiToolDto(1L);
    PageImpl<AiTool> entityPage = new PageImpl<>(List.of(entity));
    PageDto<AiToolDto> expected = new PageDto<>(List.of(dto), 1, 1);

    doReturn(entityPage).when(mService).search("query", new TreeSet<>(List.of("id")), true, 1, 10);
    doReturn(expected).when(mCrudController).getAll(entityPage, Set.of("id"));

    PageDto<AiToolDto> result
        = this.controller.search("query", 1, 10, new TreeSet<>(List.of("id")), true, Set.of("id"));

    assertEquals(expected, result);
  }
}
