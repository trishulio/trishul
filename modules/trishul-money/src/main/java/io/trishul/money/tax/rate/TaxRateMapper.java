package io.trishul.money.tax.rate;

import io.trishul.money.tax.rate.dto.TaxRateDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaxRateMapper {
  final TaxRateMapper INSTANCE = Mappers.getMapper(TaxRateMapper.class);

  TaxRateDto toDto(TaxRate taxRate);

  TaxRate fromDto(TaxRateDto dto);
}
