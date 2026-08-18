package sh.trishul.ai.service.agent.model.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import sh.trishul.ai.agent.model.AddAiAgentConfigDto;
import sh.trishul.ai.agent.model.AiAgentConfig;
import sh.trishul.ai.agent.model.AiAgentConfigDto;
import sh.trishul.ai.agent.model.BaseAiAgentConfig;
import sh.trishul.ai.agent.model.UpdateAiAgentConfig;
import sh.trishul.ai.agent.model.UpdateAiAgentConfigDto;
import sh.trishul.ai.service.agent.model.service.AiAgentConfigService;
import sh.trishul.crud.controller.CrudControllerService;
import sh.trishul.model.base.dto.DeleteResultDto;
import sh.trishul.repo.jpa.repository.model.dto.PageDto;

class AiAgentConfigControllerTest {
  private AiAgentConfigController controller;

  private CrudControllerService<Long, AiAgentConfig, BaseAiAgentConfig<?>, UpdateAiAgentConfig<?>, AiAgentConfigDto, AddAiAgentConfigDto, UpdateAiAgentConfigDto> mCrudController;

  private AiAgentConfigService mService;

  @BeforeEach
  void init() {
    this.mCrudController = mock(CrudControllerService.class);
    this.mService = mock(AiAgentConfigService.class);
    this.controller = new AiAgentConfigController(mCrudController, mService);
  }

  @Test
  void testGetAll_ReturnsDtosFromController() {
    AiAgentConfig entity = new AiAgentConfig(1L);
    AiAgentConfigDto dto = new AiAgentConfigDto(1L);

    doReturn(new PageImpl<>(List.of(entity))).when(mService).getAgentConfigs(Set.of(1L),
        Set.of("name"), true, 1, 10, new TreeSet<>(List.of("id")), true);
    doReturn(new PageDto<>(List.of(dto), 1, 1)).when(mCrudController)
        .getAll(new PageImpl<>(List.of(entity)), Set.of("id"));

    PageDto<AiAgentConfigDto> page = this.controller.getAll(Set.of(1L), Set.of("name"), true, 1, 10,
        new TreeSet<>(List.of("id")), true, Set.of("id"));

    PageDto<AiAgentConfigDto> expected = new PageDto<>(List.of(dto), 1, 1);
    assertEquals(expected, page);
  }

  @Test
  void testGet_ReturnsDtoFromController() {
    AiAgentConfigDto dto = new AiAgentConfigDto(1L);
    doReturn(dto).when(mCrudController).get(1L, Set.of("id"));

    AiAgentConfigDto result = this.controller.get(1L, Set.of("id"));

    assertEquals(dto, result);
  }

  @Test
  void testDelete_ReturnsDeleteCountFromController() {
    doReturn(new DeleteResultDto(1L)).when(mCrudController).delete(Set.of(1L));

    assertEquals(new DeleteResultDto(1L), this.controller.delete(Set.of(1L)));
  }

  @Test
  void testAdd_AddsToControllerAndReturnsListOfDtos() {
    AddAiAgentConfigDto addDto = new AddAiAgentConfigDto();
    AiAgentConfigDto dto = new AiAgentConfigDto(1L);
    doReturn(List.of(dto)).when(mCrudController).add(List.of(addDto));

    List<AiAgentConfigDto> dtos = this.controller.add(List.of(addDto));

    assertEquals(List.of(dto), dtos);
  }

  @Test
  void testUpdate_PutsToControllerAndReturnsListOfDtos() {
    UpdateAiAgentConfigDto updateDto = new UpdateAiAgentConfigDto();
    AiAgentConfigDto dto = new AiAgentConfigDto(1L);
    doReturn(List.of(dto)).when(mCrudController).put(List.of(updateDto));

    List<AiAgentConfigDto> dtos = this.controller.update(List.of(updateDto));

    assertEquals(List.of(dto), dtos);
  }

  @Test
  void testPatch_PatchToControllerAndReturnsListOfDtos() {
    UpdateAiAgentConfigDto patchDto = new UpdateAiAgentConfigDto();
    AiAgentConfigDto dto = new AiAgentConfigDto(1L);
    doReturn(List.of(dto)).when(mCrudController).patch(List.of(patchDto));

    List<AiAgentConfigDto> dtos = this.controller.patch(List.of(patchDto));

    assertEquals(List.of(dto), dtos);
  }
}
