package sh.trishul.ai.service.chat.model.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import sh.trishul.ai.chat.model.AddAiChatModelConfigDto;
import sh.trishul.ai.chat.model.AiChatModelConfig;
import sh.trishul.ai.chat.model.AiChatModelConfigDto;
import sh.trishul.ai.chat.model.BaseAiChatModelConfig;
import sh.trishul.ai.chat.model.UpdateAiChatModelConfig;
import sh.trishul.ai.chat.model.UpdateAiChatModelConfigDto;
import sh.trishul.ai.service.chat.model.service.AiChatModelConfigService;
import sh.trishul.crud.controller.CrudControllerService;
import sh.trishul.model.base.dto.DeleteResultDto;
import sh.trishul.repo.jpa.repository.model.dto.PageDto;

class AiChatModelConfigControllerTest {
  private AiChatModelConfigController controller;

  private CrudControllerService<Long, AiChatModelConfig, BaseAiChatModelConfig<?>, UpdateAiChatModelConfig<?>, AiChatModelConfigDto, AddAiChatModelConfigDto, UpdateAiChatModelConfigDto> mCrudController;

  private AiChatModelConfigService mService;

  @BeforeEach
  void init() {
    this.mCrudController = mock(CrudControllerService.class);
    this.mService = mock(AiChatModelConfigService.class);
    this.controller = new AiChatModelConfigController(mCrudController, mService);
  }

  @Test
  void testGetAll_ReturnsDtosFromController() {
    AiChatModelConfig entity = new AiChatModelConfig(1L);
    AiChatModelConfigDto dto = new AiChatModelConfigDto(1L);

    doReturn(new PageImpl<>(List.of(entity))).when(mService).getChatModelConfigs(Set.of(1L),
        Set.of("name"), Set.of("openai"), true, 1, 10, new TreeSet<>(List.of("id")), true);
    doReturn(new PageDto<>(List.of(dto), 1, 1)).when(mCrudController)
        .getAll(new PageImpl<>(List.of(entity)), Set.of("id"));

    PageDto<AiChatModelConfigDto> page = this.controller.getAll(Set.of(1L), Set.of("name"),
        Set.of("openai"), true, 1, 10, new TreeSet<>(List.of("id")), true, Set.of("id"));

    PageDto<AiChatModelConfigDto> expected = new PageDto<>(List.of(dto), 1, 1);
    assertEquals(expected, page);
  }

  @Test
  void testGet_ReturnsDtoFromController() {
    AiChatModelConfigDto dto = new AiChatModelConfigDto(1L);
    doReturn(dto).when(mCrudController).get(1L, Set.of("id"));

    AiChatModelConfigDto result = this.controller.get(1L, Set.of("id"));

    assertEquals(dto, result);
  }

  @Test
  void testDelete_ReturnsDeleteCountFromController() {
    doReturn(new DeleteResultDto(1L)).when(mCrudController).delete(Set.of(1L));

    assertEquals(new DeleteResultDto(1L), this.controller.delete(Set.of(1L)));
  }

  @Test
  void testAdd_AddsToControllerAndReturnsListOfDtos() {
    AddAiChatModelConfigDto addDto = new AddAiChatModelConfigDto();
    AiChatModelConfigDto dto = new AiChatModelConfigDto(1L);
    doReturn(List.of(dto)).when(mCrudController).add(List.of(addDto));

    List<AiChatModelConfigDto> dtos = this.controller.add(List.of(addDto));

    assertEquals(List.of(dto), dtos);
  }

  @Test
  void testUpdate_PutsToControllerAndReturnsListOfDtos() {
    UpdateAiChatModelConfigDto updateDto = new UpdateAiChatModelConfigDto();
    AiChatModelConfigDto dto = new AiChatModelConfigDto(1L);
    doReturn(List.of(dto)).when(mCrudController).put(List.of(updateDto));

    List<AiChatModelConfigDto> dtos = this.controller.update(List.of(updateDto));

    assertEquals(List.of(dto), dtos);
  }

  @Test
  void testPatch_PatchToControllerAndReturnsListOfDtos() {
    UpdateAiChatModelConfigDto patchDto = new UpdateAiChatModelConfigDto();
    AiChatModelConfigDto dto = new AiChatModelConfigDto(1L);
    doReturn(List.of(dto)).when(mCrudController).patch(List.of(patchDto));

    List<AiChatModelConfigDto> dtos = this.controller.patch(List.of(patchDto));

    assertEquals(List.of(dto), dtos);
  }
}
