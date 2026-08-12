package sh.trishul.quantity.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import javax.measure.Quantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.quantity.model.dto.QuantityDto;
import sh.trishul.quantity.unit.SupportedUnits;
import sh.trishul.quantity.unit.UnitEntity;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

class QuantityMapperTest {
  QuantityMapper mapper;

  @BeforeEach
  void init() {
    mapper = QuantityMapper.INSTANCE;
  }

  @Test
  void testToDto_ReturnsDto_WhenQuantityIsNotNull() {
    QuantityDto dto
        = mapper.toDto(Quantities.getQuantity(new BigDecimal(100), SupportedUnits.GRAM));

    assertEquals("g", dto.getSymbol());
    assertEquals(new BigDecimal(100), dto.getValue());
  }

  @Test
  void testToDto_ReturnsNull_WhenQuantityIsNull() {
    assertNull(mapper.toDto(null));
  }

  @Test
  void testToEntity_ReturnsEntity_WhenQuantityIsNotNull() {
    Quantity<?> qty = Quantities.getQuantity(new BigDecimal("10.00"), Units.AMPERE);

    QuantityEntity entity = mapper.toEntity(qty);
    QuantityEntity expected
        = new QuantityEntity(new UnitEntity("A", null), new BigDecimal("10.00"));

    assertEquals(expected, entity);
  }

  @Test
  void testToEntity_ReturnsNull_WhenQuantityIsNull() {
    assertNull(mapper.toEntity(null));
  }

  @Test
  void testFromDto_ReturnsQuantity_WhenDtoIsNotNull() {
    Quantity<?> quantity = mapper.fromDto(new QuantityDto("g", new BigDecimal(100)));
    assertEquals(SupportedUnits.GRAM, quantity.getUnit());
    assertEquals(new BigDecimal(100), quantity.getValue());
  }

  @Test
  void testFromDto_ReturnsNull_WhenDtoIsNull() {
    assertNull(mapper.fromDto(null));
  }

  @Test
  void testFromEntity_ReturnsNull_WhenEntityIsNotNull() {
    assertNull(mapper.fromEntity(null));
  }

  @Test
  void testFromEntity_ReturnsQuantity_WhenEntityIsNotNull() {
    QuantityEntity entity = new QuantityEntity(new UnitEntity("g", null), new BigDecimal("100"));
    Quantity<?> qty = mapper.fromEntity(entity);
    assertEquals(SupportedUnits.GRAM, qty.getUnit());
    assertEquals(new BigDecimal("100"), qty.getValue());
  }

  @Test
  void testParseNumber_ReturnsBigDecimal_WhenDoubleIsPassed() {
    BigDecimal decimal = mapper.parseNumber(10.5d);
    assertEquals(new BigDecimal("10.5"), decimal);
  }

  @Test
  void testParseNumber_ReturnsNull_WhenNullIsPassed() {
    assertNull(mapper.parseNumber(null));
  }
}
