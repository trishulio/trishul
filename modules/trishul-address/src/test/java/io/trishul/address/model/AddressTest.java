package io.trishul.address.model;

import static org.junit.jupiter.api.Assertions.assertSame;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddressTest {
  private Address address;

  @BeforeEach
  void init() {
    address = new Address();
  }

  @Test
  void testConstructor() {
    String addressLine1 = "addressLine1";
    String addressLine2 = "addressLine2";
    String country = "country";
    String province = "province";
    String city = "city";
    String postalCode = "postalCode";
    LocalDateTime createdAt = LocalDateTime.of(2025, 1, 1, 0, 0);
    LocalDateTime lastUpdated = LocalDateTime.of(2025, 1, 2, 0, 0);

    Address address = new Address(addressLine1, addressLine2, country, province, city, postalCode,
        createdAt, lastUpdated);

    assertSame(addressLine1, address.getAddressLine1());
    assertSame(addressLine2, address.getAddressLine2());
    assertSame(country, address.getCountry());
    assertSame(province, address.getProvince());
    assertSame(city, address.getCity());
    assertSame(postalCode, address.getPostalCode());
    assertSame(createdAt, address.getCreatedAt());
    assertSame(lastUpdated, address.getLastUpdated());
  }

  @Test
  void testGetSetAddressLine1() {
    assertSame(address, address.setAddressLine1("line1"));
    assertSame("line1", address.getAddressLine1());
  }

  @Test
  void testGetSetAddressLine2() {
    assertSame(address, address.setAddressLine2("line2"));
    assertSame("line2", address.getAddressLine2());
  }

  @Test
  void testGetSetCountry() {
    assertSame(address, address.setCountry("country"));
    assertSame("country", address.getCountry());
  }

  @Test
  void testGetSetProvince() {
    assertSame(address, address.setProvince("province"));
    assertSame("province", address.getProvince());
  }

  @Test
  void testGetSetCity() {
    assertSame(address, address.setCity("city"));
    assertSame("city", address.getCity());
  }

  @Test
  void testGetSetPostalCode() {
    assertSame(address, address.setPostalCode("postalCode"));
    assertSame("postalCode", address.getPostalCode());
  }

  @Test
  void testGetSetCreatedAt() {
    LocalDateTime createdAt = LocalDateTime.of(2025, 1, 1, 0, 0);
    assertSame(address, address.setCreatedAt(createdAt));
    assertSame(createdAt, address.getCreatedAt());
  }

  @Test
  void testGetSetLastUpdated() {
    LocalDateTime lastUpdated = LocalDateTime.of(2025, 1, 2, 0, 0);
    assertSame(address, address.setLastUpdated(lastUpdated));
    assertSame(lastUpdated, address.getLastUpdated());
  }
}
