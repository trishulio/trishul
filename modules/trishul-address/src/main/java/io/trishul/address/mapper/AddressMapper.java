package io.trishul.address.mapper;

import io.trishul.address.model.Address;
import io.trishul.address.model.dto.AddressDto;
import io.trishul.model.base.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper extends BaseMapper<Address, AddressDto, AddressDto, AddressDto> {
  AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
}
