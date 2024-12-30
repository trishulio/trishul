package io.trishul.address.mapper;

import io.trishul.address.model.Address;
import io.trishul.address.model.dto.AddressDto;

public interface AddressMapper { // TODO, implement BaseMapper
  <A extends Address> AddressDto toDto(A address);

  <A extends AddressDto> Address fromDto(A dto);
}
