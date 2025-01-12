package io.trishul.quantity.model;

import java.math.BigDecimal;

import javax.measure.Quantity;
import javax.measure.Unit;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.trishul.quantity.model.dto.QuantityDto;
import io.trishul.quantity.unit.QuantityUnitMapper;
import tec.uom.se.quantity.Quantities;

@Mapper(uses = {QuantityUnitMapper.class})
public abstract class QuantityMapper {
  public static final QuantityMapper INSTANCE = Mappers.getMapper(QuantityMapper.class);

  public QuantityDto toDto(Quantity<?> quantity) {
    QuantityDto dto = null;
    if (quantity != null) {
      dto = new QuantityDto();
      String unitSymbol = QuantityUnitMapper.INSTANCE.toSymbol(quantity.getUnit());
      dto.setSymbol(unitSymbol);
      dto.setValue(parseNumber(quantity.getValue()));
    }

    return dto;
  }

  public abstract QuantityEntity toEntity(Quantity<?> quantity);

  public Quantity<?> fromDto(QuantityDto dto) {
    Quantity<?> qty = null;
    if (dto != null) {
      Unit<?> unit = QuantityUnitMapper.INSTANCE.fromSymbol(dto.getSymbol());
      qty = Quantities.getQuantity(dto.getValue(), unit);
    }

    return qty;
  }

  public Quantity<?> fromEntity(QuantityEntity entity) {
    Quantity<?> qty = null;
    if (entity != null) {
      Unit<?> unit = QuantityUnitMapper.INSTANCE.fromSymbol(entity.getUnit().getSymbol());
      Number value = entity.getValue();
      qty = Quantities.getQuantity(value, unit);
    }

    return qty;
  }

  /**
   * Numbers can be casted as BigDecimal here because Spring by default parses JSON values to
   * BigDecimal when field type is defined as Number. But for clarity, the QuantityDto also uses
   * BigDecimal so that it can be casted here if there is a change in default behavior by Spring.
   */
  public BigDecimal parseNumber(Number num) {
    BigDecimal bigDecimal = null;
    if (num != null && num instanceof Double) {
      bigDecimal = new BigDecimal(num.toString());
    } else {
      bigDecimal = (BigDecimal) num;
    }
    return bigDecimal;
  }
}
