package io.trishul.address.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.trishul.address.model.Address;
import io.trishul.address.model.dto.AddressDto;

@Mapper
public interface AddressMapper { // TODO, implement BaseMapper
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    <A extends Address> AddressDto toDto(A address);

    <A extends AddressDto> Address fromDto(A dto);
}
