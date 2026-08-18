package sh.trishul.address.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.address.model.Address;
import sh.trishul.address.model.dto.AddressDto;

class AddressMapperTest {
  private AddressMapper mapper;

  @BeforeEach
  void init() {
    this.mapper = AddressMapper.INSTANCE;
  }

  @Test
  void testFromAddDto_ReturnsNull_WhenDtoIsNull() {
    assertNull(mapper.fromAddDto(null));
  }

  @Test
  void testFromAddDto_ReturnsPojo_WhenDtoIsNotNull() {
    AddressDto dto = new AddressDto(1L, "line1", "line2", "country", "province", "city", "zip");
    Address address = mapper.fromAddDto(dto);

    assertEquals("line1", address.getAddressLine1());
    assertEquals("line2", address.getAddressLine2());
    assertEquals("country", address.getCountry());
    assertEquals("province", address.getProvince());
    assertEquals("city", address.getCity());
    assertEquals("zip", address.getPostalCode());
  }

  @Test
  void testFromUpdateDto_ReturnsNull_WhenDtoIsNull() {
    assertNull(mapper.fromUpdateDto(null));
  }

  @Test
  void testFromUpdateDto_ReturnsPojo_WhenDtoIsNotNull() {
    AddressDto dto = new AddressDto(1L, "line1", "line2", "country", "province", "city", "zip");
    Address address = mapper.fromUpdateDto(dto);

    assertEquals("line1", address.getAddressLine1());
    assertEquals("line2", address.getAddressLine2());
    assertEquals("country", address.getCountry());
    assertEquals("province", address.getProvince());
    assertEquals("city", address.getCity());
    assertEquals("zip", address.getPostalCode());
  }

  @Test
  void testToDto_ReturnsNull_WhenEntityIsNull() {
    assertNull(mapper.toDto(null));
  }

  @Test
  void testToDto_ReturnsDto_WhenEntityIsNotNull() {
    Address address = new Address().setAddressLine1("line1").setAddressLine2("line2")
        .setCountry("country").setProvince("province").setCity("city").setPostalCode("zip");

    AddressDto dto = mapper.toDto(address);

    assertEquals("line1", dto.getAddressLine1());
    assertEquals("line2", dto.getAddressLine2());
    assertEquals("country", dto.getCountry());
    assertEquals("province", dto.getProvince());
    assertEquals("city", dto.getCity());
    assertEquals("zip", dto.getPostalCode());
  }
}
