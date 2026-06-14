package io.trishul.ai.service.memory.model.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import io.trishul.ai.memory.model.AddAiChatMemoryConfigDto;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfigDto;
import io.trishul.ai.memory.model.BaseAiChatMemoryConfig;
import io.trishul.ai.memory.model.UpdateAiChatMemoryConfig;
import io.trishul.ai.memory.model.UpdateAiChatMemoryConfigDto;
import io.trishul.ai.service.memory.model.service.AiChatMemoryConfigService;
import io.trishul.crud.controller.CrudControllerService;
import io.trishul.repo.jpa.repository.model.dto.PageDto;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

class AiChatMemoryConfigControllerTest {
  private AiChatMemoryConfigController controller;

  private CrudControllerService<Long, AiChatMemoryConfig, BaseAiChatMemoryConfig<?>, UpdateAiChatMemoryConfig<?>, AiChatMemoryConfigDto, AddAiChatMemoryConfigDto, UpdateAiChatMemoryConfigDto> mCrudController;

  private AiChatMemoryConfigService mService;

  @BeforeEach
  void init() {
    this.mCrudController = mock(CrudControllerService.class);
    this.mService = mock(AiChatMemoryConfigService.class);
    this.controller = new AiChatMemoryConfigController(mCrudController, mService);
  }

  @Test
  void testGetAll_ReturnsDtosFromController() {
    AiChatMemoryConfig entity = new AiChatMemoryConfig(1L);
    AiChatMemoryConfigDto dto = new AiChatMemoryConfigDto(1L);

    doReturn(new PageImpl<>(List.of(entity))).when(mService).getChatMemoryConfigs(Set.of(1L),
        Set.of("name"), 1, 10, new TreeSet<>(List.of("id")), true);
    doReturn(new PageDto<>(List.of(dto), 1, 1)).when(mCrudController)
        .getAll(new PageImpl<>(List.of(entity)), Set.of("id"));

    PageDto<AiChatMemoryConfigDto> page = this.controller.getAll(Set.of(1L), Set.of("name"), 1, 10,
        new TreeSet<>(List.of("id")), true, Set.of("id"));

    PageDto<AiChatMemoryConfigDto> expected = new PageDto<>(List.of(dto), 1, 1);
    assertEquals(expected, page);
  }

  @Test
  void testGet_ReturnsDtoFromController() {
    AiChatMemoryConfigDto dto = new AiChatMemoryConfigDto(1L);
    doReturn(dto).when(mCrudController).get(1L, Set.of("id"));

    AiChatMemoryConfigDto result = this.controller.get(1L, Set.of("id"));

    assertEquals(dto, result);
  }

  @Test
  void testDelete_ReturnsDeleteCountFromController() {
    doReturn(1L).when(mCrudController).delete(Set.of(1L));

    assertEquals(1L, this.controller.delete(Set.of(1L)));
  }

  @Test
  void testAdd_AddsToControllerAndReturnsListOfDtos() {
    AddAiChatMemoryConfigDto addDto = new AddAiChatMemoryConfigDto();
    AiChatMemoryConfigDto dto = new AiChatMemoryConfigDto(1L);
    doReturn(List.of(dto)).when(mCrudController).add(List.of(addDto));

    List<AiChatMemoryConfigDto> dtos = this.controller.add(List.of(addDto));

    assertEquals(List.of(dto), dtos);
  }

  @Test
  void testUpdate_PutsToControllerAndReturnsListOfDtos() {
    UpdateAiChatMemoryConfigDto updateDto = new UpdateAiChatMemoryConfigDto();
    AiChatMemoryConfigDto dto = new AiChatMemoryConfigDto(1L);
    doReturn(List.of(dto)).when(mCrudController).put(List.of(updateDto));

    List<AiChatMemoryConfigDto> dtos = this.controller.update(List.of(updateDto));

    assertEquals(List.of(dto), dtos);
  }

  @Test
  void testPatch_PatchToControllerAndReturnsListOfDtos() {
    UpdateAiChatMemoryConfigDto patchDto = new UpdateAiChatMemoryConfigDto();
    AiChatMemoryConfigDto dto = new AiChatMemoryConfigDto(1L);
    doReturn(List.of(dto)).when(mCrudController).patch(List.of(patchDto));

    List<AiChatMemoryConfigDto> dtos = this.controller.patch(List.of(patchDto));

    assertEquals(List.of(dto), dtos);
  }
}
