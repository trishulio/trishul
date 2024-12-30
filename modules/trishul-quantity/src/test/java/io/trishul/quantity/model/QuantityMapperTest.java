package io.trishul.quantity.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.trishul.quantity.model.dto.QuantityDto;
import io.trishul.quantity.unit.SupportedUnits;
import io.trishul.quantity.unit.UnitEntity;
import java.math.BigDecimal;
import javax.measure.Quantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class QuantityMapperTest {
  QuantityMapper mapper;

  @BeforeEach
  public void init() {
    mapper = QuantityMapper.INSTANCE;
  }

  @Test
  public void testToDto_ReturnsDto_WhenQuantityIsNotNull() {
    QuantityDto dto
        = mapper.toDto(Quantities.getQuantity(new BigDecimal(100), SupportedUnits.GRAM));

    assertEquals("g", dto.getSymbol());
    assertEquals(new BigDecimal(100), dto.getValue());
  }

  @Test
  public void testToDto_ReturnsNull_WhenQuantityIsNull() {
    assertNull(mapper.toDto(null));
  }

  @Test
  public void testToEntity_ReturnsEntity_WhenQuantityIsNotNull() {
    Quantity<?> qty = Quantities.getQuantity(new BigDecimal("10.00"), Units.AMPERE);

    QuantityEntity entity = mapper.toEntity(qty);
    QuantityEntity expected
        = new QuantityEntity(new UnitEntity("A", null), new BigDecimal("10.00"));

    assertEquals(expected, entity);
  }

  @Test
  public void testToEntity_ReturnsNull_WhenQuantityIsNull() {
    assertNull(mapper.toEntity(null));
  }

  @Test
  public void testFromDto_ReturnsQuantity_WhenDtoIsNotNull() {
    Quantity<?> quantity = mapper.fromDto(new QuantityDto("g", new BigDecimal(100)));
    assertEquals(SupportedUnits.GRAM, quantity.getUnit());
    assertEquals(new BigDecimal(100), quantity.getValue());
  }

  @Test
  public void testFromDto_ReturnsNull_WhenDtoIsNull() {
    assertNull(mapper.fromDto(null));
  }

  @Test
  public void testFromEntity_ReturnsNull_WhenEntityIsNotNull() {
    assertNull(mapper.fromEntity(null));
  }
}
