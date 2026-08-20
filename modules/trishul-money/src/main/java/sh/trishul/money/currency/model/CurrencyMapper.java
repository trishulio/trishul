package sh.trishul.money.currency.model;

import org.joda.money.CurrencyUnit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class CurrencyMapper {
  public static final CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

  public Currency toEntity(String code) {
    Currency entity = null;
    CurrencyUnit unit = toUnit(code);
    if (unit != null) {
      entity = toEntity(unit);
    }

    return entity;
  }

  public CurrencyUnit toUnit(String code) {
    CurrencyUnit unit = null;
    if (code != null) {
      unit = CurrencyUnit.of(code);
    }

    return unit;
  }

  public abstract Currency toEntity(CurrencyUnit unit);
}
