package io.trishul.address.model.dto;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddressDtoTest {
  private AddressDto addressDto;

  @BeforeEach
  void init() {
    addressDto = new AddressDto();
  }

  @Test
  void testConstructor() {
    Long id = 1L;
    String addressLine1 = "addressLine1";
    String addressLine2 = "addressLine2";
    String country = "country";
    String province = "province";
    String city = "city";
    String postalCode = "postalCode";

    AddressDto addressDto
        = new AddressDto(id, addressLine1, addressLine2, country, province, city, postalCode);

    assertSame(id, addressDto.getId());
    assertSame(addressLine1, addressDto.getAddressLine1());
    assertSame(addressLine2, addressDto.getAddressLine2());
    assertSame(country, addressDto.getCountry());
    assertSame(province, addressDto.getProvince());
    assertSame(city, addressDto.getCity());
    assertSame(postalCode, addressDto.getPostalCode());
  }

  @Test
  void testGetSetId() {
    assertSame(addressDto, addressDto.setId(1L));
    assertSame(1L, addressDto.getId());
  }

  @Test
  void testGetSetAddressLine1() {
    assertSame(addressDto, addressDto.setAddressLine1("line1"));
    assertSame("line1", addressDto.getAddressLine1());
  }

  @Test
  void testGetSetAddressLine2() {
    assertSame(addressDto, addressDto.setAddressLine2("line2"));
    assertSame("line2", addressDto.getAddressLine2());
  }

  @Test
  void testGetSetCity() {
    assertSame(addressDto, addressDto.setCity("city"));
    assertSame("city", addressDto.getCity());
  }

  @Test
  void testGetSetCountry() {
    assertSame(addressDto, addressDto.setCountry("country"));
    assertSame("country", addressDto.getCountry());
  }

  @Test
  void testGetSetProvince() {
    assertSame(addressDto, addressDto.setProvince("province"));
    assertSame("province", addressDto.getProvince());
  }

  @Test
  void testGetPostalCode() {
    assertSame(addressDto, addressDto.setPostalCode("postalCode"));
    assertSame("postalCode", addressDto.getPostalCode());
  }
}
