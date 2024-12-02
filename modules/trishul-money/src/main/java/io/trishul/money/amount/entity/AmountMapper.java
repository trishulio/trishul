package io.trishul.money.amount.entity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.trishul.money.MoneyMapper;
import io.trishul.money.amount.dto.AmountDto;
import io.trishul.money.tax.model.TaxMapper;

@Mapper(uses = { TaxMapper.class, MoneyMapper.class })
public interface AmountMapper {
    final AmountMapper INSTANCE = Mappers.getMapper(AmountMapper.class);

    AmountDto toDto(Amount amount);

    Amount fromDto(AmountDto dto);
}
