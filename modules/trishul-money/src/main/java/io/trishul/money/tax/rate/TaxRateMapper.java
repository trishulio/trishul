package io.trishul.money.tax.rate;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.trishul.money.tax.rate.dto.TaxRateDto;

@Mapper
public interface TaxRateMapper {
    final TaxRateMapper INSTANCE = Mappers.getMapper(TaxRateMapper.class);

    TaxRateDto toDto(TaxRate taxRate);

    TaxRate fromDto(TaxRateDto dto);
}
