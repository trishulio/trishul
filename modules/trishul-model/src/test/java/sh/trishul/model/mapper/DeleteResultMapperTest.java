package sh.trishul.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import sh.trishul.model.base.dto.DeleteResultDto;
import sh.trishul.model.base.pojo.DeleteResult;

class DeleteResultMapperTest {
  private final DeleteResultMapper mapper = DeleteResultMapper.INSTANCE;

  @Test
  void testToDto_ReturnsNull_WhenArgIsNull() {
    assertNull(mapper.toDto(null));
  }

  @Test
  void testToDto_ReturnsDto_WhenArgIsNotNull() {
    DeleteResult result = new DeleteResult(10L);
    DeleteResultDto dto = mapper.toDto(result);
    assertEquals(10L, dto.getCount());
  }

  @Test
  void testFromDto_ReturnsNull_WhenArgIsNull() {
    assertNull(mapper.fromDto(null));
  }

  @Test
  void testFromDto_ReturnsPojo_WhenArgIsNotNull() {
    DeleteResultDto dto = new DeleteResultDto(10L);
    DeleteResult result = mapper.fromDto(dto);
    assertEquals(10L, result.getCount());
  }
}
