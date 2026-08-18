package sh.trishul.address.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sh.trishul.address.model.Address;
import sh.trishul.address.model.dto.AddressDto;
import sh.trishul.model.base.mapper.BaseMapper;

@Mapper
public interface AddressMapper extends BaseMapper<Address, AddressDto, AddressDto, AddressDto> {
  AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
}
