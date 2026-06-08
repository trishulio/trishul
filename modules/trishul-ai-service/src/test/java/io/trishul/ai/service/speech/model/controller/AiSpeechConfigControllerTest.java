package io.trishul.ai.service.speech.model.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import io.trishul.ai.service.speech.model.service.AiSpeechConfigService;
import io.trishul.ai.speech.model.AddAiSpeechConfigDto;
import io.trishul.ai.speech.model.AiSpeechConfig;
import io.trishul.ai.speech.model.AiSpeechConfigDto;
import io.trishul.ai.speech.model.BaseAiSpeechConfig;
import io.trishul.ai.speech.model.UpdateAiSpeechConfig;
import io.trishul.ai.speech.model.UpdateAiSpeechConfigDto;
import io.trishul.crud.controller.CrudControllerService;
import io.trishul.repo.jpa.repository.model.dto.PageDto;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

class AiSpeechConfigControllerTest {
  private AiSpeechConfigController controller;

  private CrudControllerService<Long, AiSpeechConfig, BaseAiSpeechConfig<?>, UpdateAiSpeechConfig<?>, AiSpeechConfigDto, AddAiSpeechConfigDto, UpdateAiSpeechConfigDto> mCrudController;

  private AiSpeechConfigService mService;

  @BeforeEach
  void init() {
    this.mCrudController = mock(CrudControllerService.class);
    this.mService = mock(AiSpeechConfigService.class);
    this.controller = new AiSpeechConfigController(mCrudController, mService);
  }

  @Test
  void testGetAll_ReturnsDtosFromController() {
    AiSpeechConfig entity = new AiSpeechConfig(1L);
    AiSpeechConfigDto dto = new AiSpeechConfigDto(1L);

    doReturn(new PageImpl<>(List.of(entity))).when(mService).getSpeechConfigs(Set.of(1L),
        Set.of("name"), Set.of("openai"), true, 1, 10, new TreeSet<>(List.of("id")), true);
    doReturn(new PageDto<>(List.of(dto), 1, 1)).when(mCrudController)
        .getAll(new PageImpl<>(List.of(entity)), Set.of("id"));

    PageDto<AiSpeechConfigDto> page = this.controller.getAll(Set.of(1L), Set.of("name"),
        Set.of("openai"), true, 1, 10, new TreeSet<>(List.of("id")), true, Set.of("id"));

    PageDto<AiSpeechConfigDto> expected = new PageDto<>(List.of(dto), 1, 1);
    assertEquals(expected, page);
  }

  @Test
  void testGet_ReturnsDtoFromController() {
    AiSpeechConfigDto dto = new AiSpeechConfigDto(1L);
    doReturn(dto).when(mCrudController).get(1L, Set.of("id"));

    AiSpeechConfigDto result = this.controller.get(1L, Set.of("id"));

    assertEquals(dto, result);
  }

  @Test
  void testDelete_ReturnsDeleteCountFromController() {
    doReturn(1L).when(mCrudController).delete(Set.of(1L));

    assertEquals(1L, this.controller.delete(Set.of(1L)));
  }

  @Test
  void testAdd_AddsToControllerAndReturnsListOfDtos() {
    AddAiSpeechConfigDto addDto = new AddAiSpeechConfigDto();
    AiSpeechConfigDto dto = new AiSpeechConfigDto(1L);
    doReturn(List.of(dto)).when(mCrudController).add(List.of(addDto));

    List<AiSpeechConfigDto> dtos = this.controller.add(List.of(addDto));

    assertEquals(List.of(dto), dtos);
  }

  @Test
  void testUpdate_PutsToControllerAndReturnsListOfDtos() {
    UpdateAiSpeechConfigDto updateDto = new UpdateAiSpeechConfigDto();
    AiSpeechConfigDto dto = new AiSpeechConfigDto(1L);
    doReturn(List.of(dto)).when(mCrudController).put(List.of(updateDto));

    List<AiSpeechConfigDto> dtos = this.controller.update(List.of(updateDto));

    assertEquals(List.of(dto), dtos);
  }

  @Test
  void testPatch_PatchToControllerAndReturnsListOfDtos() {
    UpdateAiSpeechConfigDto patchDto = new UpdateAiSpeechConfigDto();
    AiSpeechConfigDto dto = new AiSpeechConfigDto(1L);
    doReturn(List.of(dto)).when(mCrudController).patch(List.of(patchDto));

    List<AiSpeechConfigDto> dtos = this.controller.patch(List.of(patchDto));

    assertEquals(List.of(dto), dtos);
  }
}
