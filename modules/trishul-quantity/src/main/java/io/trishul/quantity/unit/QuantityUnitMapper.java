package io.trishul.quantity.unit;

import io.trishul.quantity.unit.dto.UnitDto;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.measure.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mapper
public abstract class QuantityUnitMapper {
  private static final Logger log = LoggerFactory.getLogger(QuantityUnitMapper.class);

  public static final QuantityUnitMapper INSTANCE = Mappers.getMapper(QuantityUnitMapper.class);

  private static final Map<String, Unit<?>> UNITS = getAllUnits();

  public QuantityUnitMapper() {}

  public Unit<?> fromSymbol(String symbol) {
    Unit<?> unit = null;
    if (symbol != null) {
      unit = UNITS.get(symbol);
    }

    return unit;
  }

  public Unit<?> fromEntity(UnitEntity entity) {
    Unit<?> unit = null;
    if (entity != null) {
      unit = fromSymbol(entity.getSymbol());
    }
    return unit;
  }

  public static String toSymbol(Unit<?> unit) {
    String symbol = null;
    if (unit != null) {
      symbol = unit.toString();
    }

    return symbol;
  }

  public UnitEntity toEntity(Unit<?> unit) {
    UnitEntity entity = null;
    if (unit != null) {
      entity = new UnitEntity(unit.toString());
    }

    return entity;
  }

  Unit<?> fromDto(UnitDto dto) {
    Unit<?> unit = null;
    if (dto != null) {
      unit = fromSymbol(dto.getSymbol());
    }

    return unit;
  }

  public UnitDto toDto(Unit<?> unit) {
    UnitDto dto = null;
    if (unit != null) {
      dto = new UnitDto(toSymbol(unit));
    }

    return dto;
  }

  public UnitDto toDto(UnitEntity unit) {
    UnitDto dto = null;
    if (unit != null) {
      dto = new UnitDto(unit.getSymbol());
    }

    return dto;
  }

  static Object getFieldValue(Field field) {
    try {
      return field.get(null);
    } catch (IllegalAccessException e) {
      String msg = String.format("Failed to retrieve the field value because: %s", e.getMessage());
      log.error(msg);
      throw new RuntimeException(msg, e);
    }
  }

  private static Map<String, Unit<?>> getAllUnits() {
    Field[] fields = SupportedUnits.class.getFields();

    Map<String, Unit<?>> unitMap
        = Arrays.stream(fields).filter(field -> !field.getName().contains("DEFAULT"))
            .map(QuantityUnitMapper::getFieldValue).filter(o -> o instanceof Unit)
            .collect(Collectors.toMap(o -> toSymbol(((Unit<?>) o)), o -> (Unit<?>) o));

    return unitMap;
  }
}
