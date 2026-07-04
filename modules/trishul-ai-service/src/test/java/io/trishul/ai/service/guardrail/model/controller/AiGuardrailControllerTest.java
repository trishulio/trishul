package io.trishul.ai.service.guardrail.model.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import io.trishul.ai.guardrail.model.AddAiGuardrailDto;
import io.trishul.ai.guardrail.model.AiGuardrail;
import io.trishul.ai.guardrail.model.AiGuardrailDto;
import io.trishul.ai.guardrail.model.BaseAiGuardrail;
import io.trishul.ai.guardrail.model.UpdateAiGuardrail;
import io.trishul.ai.guardrail.model.UpdateAiGuardrailDto;
import io.trishul.ai.service.guardrail.model.service.AiGuardrailService;
import io.trishul.crud.controller.CrudControllerService;
import io.trishul.model.base.dto.DeleteResultDto;
import io.trishul.repo.jpa.repository.model.dto.PageDto;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

class AiGuardrailControllerTest {
  private AiGuardrailController controller;

  private CrudControllerService<Long, AiGuardrail, BaseAiGuardrail<?>, UpdateAiGuardrail<?>, AiGuardrailDto, AddAiGuardrailDto, UpdateAiGuardrailDto> mCrudController;

  private AiGuardrailService mService;

  @BeforeEach
  void init() {
    this.mCrudController = mock(CrudControllerService.class);
    this.mService = mock(AiGuardrailService.class);
    this.controller = new AiGuardrailController(mCrudController, mService);
  }

  @Test
  void testGetAll_ReturnsDtosFromController() {
    AiGuardrail entity = new AiGuardrail(1L);
    AiGuardrailDto dto = new AiGuardrailDto(1L);

    doReturn(new PageImpl<>(List.of(entity))).when(mService).getGuardrails(Set.of(1L),
        Set.of("name"), 1, 10, new TreeSet<>(List.of("id")), true);
    doReturn(new PageDto<>(List.of(dto), 1, 1)).when(mCrudController)
        .getAll(new PageImpl<>(List.of(entity)), Set.of("id"));

    PageDto<AiGuardrailDto> page = this.controller.getAll(Set.of(1L), Set.of("name"), 1, 10,
        new TreeSet<>(List.of("id")), true, Set.of("id"));

    PageDto<AiGuardrailDto> expected = new PageDto<>(List.of(dto), 1, 1);
    assertEquals(expected, page);
  }

  @Test
  void testGet_ReturnsDtoFromController() {
    AiGuardrailDto dto = new AiGuardrailDto(1L);
    doReturn(dto).when(mCrudController).get(1L, Set.of("id"));

    AiGuardrailDto result = this.controller.get(1L, Set.of("id"));

    assertEquals(dto, result);
  }

  @Test
  void testDelete_ReturnsDeleteCountFromController() {
    doReturn(new DeleteResultDto(1L)).when(mCrudController).delete(Set.of(1L));

    assertEquals(new DeleteResultDto(1L), this.controller.delete(Set.of(1L)));
  }

  @Test
  void testAdd_AddsToControllerAndReturnsListOfDtos() {
    AddAiGuardrailDto addDto = new AddAiGuardrailDto();
    AiGuardrailDto dto = new AiGuardrailDto(1L);
    doReturn(List.of(dto)).when(mCrudController).add(List.of(addDto));

    List<AiGuardrailDto> dtos = this.controller.add(List.of(addDto));

    assertEquals(List.of(dto), dtos);
  }

  @Test
  void testUpdate_PutsToControllerAndReturnsListOfDtos() {
    UpdateAiGuardrailDto updateDto = new UpdateAiGuardrailDto();
    AiGuardrailDto dto = new AiGuardrailDto(1L);
    doReturn(List.of(dto)).when(mCrudController).put(List.of(updateDto));

    List<AiGuardrailDto> dtos = this.controller.update(List.of(updateDto));

    assertEquals(List.of(dto), dtos);
  }

  @Test
  void testPatch_PatchToControllerAndReturnsListOfDtos() {
    UpdateAiGuardrailDto patchDto = new UpdateAiGuardrailDto();
    AiGuardrailDto dto = new AiGuardrailDto(1L);
    doReturn(List.of(dto)).when(mCrudController).patch(List.of(patchDto));

    List<AiGuardrailDto> dtos = this.controller.patch(List.of(patchDto));

    assertEquals(List.of(dto), dtos);
  }
}
