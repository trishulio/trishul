package sh.trishul.ai.service.skill.model.controller;

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
import sh.trishul.ai.service.skill.model.service.AiSkillService;
import sh.trishul.ai.skill.model.AddAiSkillDto;
import sh.trishul.ai.skill.model.AiSkill;
import sh.trishul.ai.skill.model.AiSkillDto;
import sh.trishul.ai.skill.model.BaseAiSkill;
import sh.trishul.ai.skill.model.UpdateAiSkill;
import sh.trishul.ai.skill.model.UpdateAiSkillDto;
import sh.trishul.crud.controller.CrudControllerService;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.model.base.dto.DeleteResultDto;
import sh.trishul.repo.jpa.repository.model.dto.PageDto;

class AiSkillControllerTest {
  private AiSkillController controller;

  private CrudControllerService<Long, AiSkill, BaseAiSkill<?>, UpdateAiSkill<?>, AiSkillDto, AddAiSkillDto, UpdateAiSkillDto> mCrudController;

  private AiSkillService mService;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void init() {
    this.mCrudController = mock(CrudControllerService.class);
    this.mService = mock(AiSkillService.class);
    this.controller = new AiSkillController(mCrudController, mService);
  }

  @Test
  void testAutowiredConstructor() {
    AttributeFilter filter = mock(AttributeFilter.class);
    AiSkillController autowiredController = new AiSkillController(mService, filter);
    assertNotNull(autowiredController);
  }

  @Test
  void testGet_ReturnsDtoFromController() {
    AiSkillDto dto = new AiSkillDto(1L);
    doReturn(dto).when(mCrudController).get(1L, Set.of("id"));

    AiSkillDto result = this.controller.get(1L, Set.of("id"));

    assertEquals(dto, result);
  }

  @Test
  void testDelete_ReturnsDeleteCountFromController() {
    doReturn(new DeleteResultDto(1L)).when(mCrudController).delete(Set.of(1L));

    assertEquals(new DeleteResultDto(1L), this.controller.delete(Set.of(1L)));
  }

  @Test
  void testAdd_AddsToControllerAndReturnsListOfDtos() {
    AddAiSkillDto addDto = new AddAiSkillDto();
    AiSkillDto dto = new AiSkillDto(1L);
    doReturn(List.of(dto)).when(mCrudController).add(List.of(addDto));

    List<AiSkillDto> dtos = this.controller.add(List.of(addDto));

    assertEquals(List.of(dto), dtos);
  }

  @Test
  void testPut_PutsToControllerAndReturnsListOfDtos() {
    UpdateAiSkillDto updateDto = new UpdateAiSkillDto();
    AiSkillDto dto = new AiSkillDto(1L);
    doReturn(List.of(dto)).when(mCrudController).put(List.of(updateDto));

    List<AiSkillDto> dtos = this.controller.put(List.of(updateDto));

    assertEquals(List.of(dto), dtos);
  }

  @Test
  void testPatch_PatchToControllerAndReturnsListOfDtos() {
    UpdateAiSkillDto patchDto = new UpdateAiSkillDto();
    AiSkillDto dto = new AiSkillDto(1L);
    doReturn(List.of(dto)).when(mCrudController).patch(List.of(patchDto));

    List<AiSkillDto> dtos = this.controller.patch(List.of(patchDto));

    assertEquals(List.of(dto), dtos);
  }

  @Test
  void testSearch_ReturnsDtosFromController() {
    AiSkill entity = new AiSkill(1L);
    AiSkillDto dto = new AiSkillDto(1L);
    PageImpl<AiSkill> entityPage = new PageImpl<>(List.of(entity));
    PageDto<AiSkillDto> expected = new PageDto<>(List.of(dto), 1, 1);

    doReturn(entityPage).when(mService).search("query", new TreeSet<>(List.of("id")), true, 1, 10);
    doReturn(expected).when(mCrudController).getAll(entityPage, Set.of("id"));

    PageDto<AiSkillDto> result
        = this.controller.search("query", 1, 10, new TreeSet<>(List.of("id")), true, Set.of("id"));

    assertEquals(expected, result);
  }
}
