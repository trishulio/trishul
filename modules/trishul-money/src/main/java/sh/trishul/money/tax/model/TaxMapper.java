package sh.trishul.money.tax.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sh.trishul.model.mapper.DeleteResultMapper;
import sh.trishul.money.tax.dto.TaxDto;
import sh.trishul.money.tax.rate.TaxRateMapper;

@Mapper(uses = {TaxRateMapper.class, DeleteResultMapper.class})
public interface TaxMapper {
  TaxMapper INSTANCE = Mappers.getMapper(TaxMapper.class);

  TaxDto toDto(Tax tax);

  Tax fromDto(TaxDto dto);
}
