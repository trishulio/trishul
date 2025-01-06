package io.trishul.repo.jpa.repository.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.trishul.model.base.dto.BaseDto;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PageDtoTest {
  class DummyDto extends BaseDto {
  }

  PageDto<DummyDto> dto;

  @BeforeEach
  public void init() {
    dto = new PageDto<>();
  }

  @Test
  public void testNoArgConstructor() {
    assertTrue(dto.getContent().isEmpty());
    assertEquals(0, dto.getTotalPages());
    assertEquals(0L, dto.getTotalElements());
  }

  @Test
  public void testAllArgs() {
    List<DummyDto> content = List.of(new DummyDto());
    dto = new PageDto<>(content, 99, 999);
    assertEquals(List.of(new DummyDto()), dto.getContent());
    assertEquals(99, dto.getTotalPages());
    assertEquals(999, dto.getTotalElements());
  }

  @Test
  public void testAccessContent() {
    assertTrue(dto.getContent().isEmpty());
    dto.setContent(List.of(new DummyDto()));
    assertEquals(List.of(new DummyDto()), dto.getContent());
  }

  @Test
  public void testAccessTotalElement() {
    dto.setTotalElements(999);
    assertEquals(999, dto.getTotalElements());
  }

  @Test
  public void testAccessTotalPages() {
    dto.setTotalPages(99);
    assertEquals(99, dto.getTotalPages());
  }

  @Test
  public void testIterator_ReturnsContentIterator() {
    dto.setContent(List.of(new DummyDto()));

    Iterator<DummyDto> it = dto.iterator();

    assertTrue(it.hasNext());
    assertEquals(new DummyDto(), it.next());
  }
}
