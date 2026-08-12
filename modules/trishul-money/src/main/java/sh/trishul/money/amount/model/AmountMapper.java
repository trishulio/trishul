package sh.trishul.money.amount.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sh.trishul.model.mapper.DeleteResultMapper;
import sh.trishul.money.MoneyMapper;
import sh.trishul.money.tax.model.TaxMapper;

@Mapper(uses = {TaxMapper.class, MoneyMapper.class, DeleteResultMapper.class})
public interface AmountMapper {
  final AmountMapper INSTANCE = Mappers.getMapper(AmountMapper.class);

  AmountDto toDto(Amount amount);

  Amount fromDto(AmountDto dto);
}
