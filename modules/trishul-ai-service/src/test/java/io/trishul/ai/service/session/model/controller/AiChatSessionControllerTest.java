package io.trishul.ai.service.session.model.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import io.trishul.ai.service.session.model.service.AiChatSessionService;
import io.trishul.ai.session.model.AddAiChatSessionDto;
import io.trishul.ai.session.model.AiChatSession;
import io.trishul.ai.session.model.AiChatSessionDto;
import io.trishul.ai.session.model.BaseAiChatSession;
import io.trishul.ai.session.model.UpdateAiChatSession;
import io.trishul.ai.session.model.UpdateAiChatSessionDto;
import io.trishul.crud.controller.CrudControllerService;
import io.trishul.model.base.dto.DeleteResultDto;
import io.trishul.repo.jpa.repository.model.dto.PageDto;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

class AiChatSessionControllerTest {
  private AiChatSessionController controller;

  private CrudControllerService<Long, AiChatSession, BaseAiChatSession<?>, UpdateAiChatSession<?>, AiChatSessionDto, AddAiChatSessionDto, UpdateAiChatSessionDto> mCrudController;

  private AiChatSessionService mService;

  @BeforeEach
  void init() {
    this.mCrudController = mock(CrudControllerService.class);
    this.mService = mock(AiChatSessionService.class);
    this.controller = new AiChatSessionController(mCrudController, mService);
  }

  @Test
  void testGetAll_ReturnsDtosFromController() {
    AiChatSession entity = new AiChatSession(1L);
    AiChatSessionDto dto = new AiChatSessionDto(1L);

    doReturn(new PageImpl<>(List.of(entity))).when(mService).getChatSessions(Set.of(1L),
        Set.of("key"), Set.of("title"), true, Set.of(2L), 1, 10, new TreeSet<>(List.of("id")),
        true);
    doReturn(new PageDto<>(List.of(dto), 1, 1)).when(mCrudController)
        .getAll(new PageImpl<>(List.of(entity)), Set.of("id"));

    PageDto<AiChatSessionDto> page = this.controller.getAll(Set.of(1L), Set.of("key"),
        Set.of("title"), true, Set.of(2L), 1, 10, new TreeSet<>(List.of("id")), true, Set.of("id"));

    PageDto<AiChatSessionDto> expected = new PageDto<>(List.of(dto), 1, 1);
    assertEquals(expected, page);
  }

  @Test
  void testGet_ReturnsDtoFromController() {
    AiChatSessionDto dto = new AiChatSessionDto(1L);
    doReturn(dto).when(mCrudController).get(1L, Set.of("id"));

    AiChatSessionDto result = this.controller.get(1L, Set.of("id"));

    assertEquals(dto, result);
  }

  @Test
  void testDelete_ReturnsDeleteCountFromController() {
    doReturn(new DeleteResultDto(1L)).when(mCrudController).delete(Set.of(1L));

    assertEquals(new DeleteResultDto(1L), this.controller.delete(Set.of(1L)));
  }

  @Test
  void testAdd_AddsToControllerAndReturnsListOfDtos() {
    AddAiChatSessionDto addDto = new AddAiChatSessionDto();
    AiChatSessionDto dto = new AiChatSessionDto(1L);
    doReturn(List.of(dto)).when(mCrudController).add(List.of(addDto));

    List<AiChatSessionDto> dtos = this.controller.add(List.of(addDto));

    assertEquals(List.of(dto), dtos);
  }

  @Test
  void testUpdate_PutsToControllerAndReturnsListOfDtos() {
    UpdateAiChatSessionDto updateDto = new UpdateAiChatSessionDto();
    AiChatSessionDto dto = new AiChatSessionDto(1L);
    doReturn(List.of(dto)).when(mCrudController).put(List.of(updateDto));

    List<AiChatSessionDto> dtos = this.controller.update(List.of(updateDto));

    assertEquals(List.of(dto), dtos);
  }

  @Test
  void testPatch_PatchToControllerAndReturnsListOfDtos() {
    UpdateAiChatSessionDto patchDto = new UpdateAiChatSessionDto();
    AiChatSessionDto dto = new AiChatSessionDto(1L);
    doReturn(List.of(dto)).when(mCrudController).patch(List.of(patchDto));

    List<AiChatSessionDto> dtos = this.controller.patch(List.of(patchDto));

    assertEquals(List.of(dto), dtos);
  }
}
