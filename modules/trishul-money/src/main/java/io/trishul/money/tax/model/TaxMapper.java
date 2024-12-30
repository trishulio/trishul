package io.trishul.money.tax.model;

import io.trishul.money.tax.dto.TaxDto;
import io.trishul.money.tax.rate.TaxRateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {TaxRateMapper.class})
public interface TaxMapper {
  final TaxMapper INSTANCE = Mappers.getMapper(TaxMapper.class);

  TaxDto toDto(Tax tax);

  Tax fromDto(TaxDto dto);
}
