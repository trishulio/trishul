package io.trishul.address.model.dto;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddressDtoTest {
    private AddressDto addressDto;

    @BeforeEach
    public void init() {
        addressDto = new AddressDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        String addressLine1 = "addressLine1";
        String addressLine2 = "addressLine2";
        String country = "country";
        String province = "province";
        String city = "city";
        String postalCode = "postalCode";

        AddressDto addressDto = new AddressDto(id, addressLine1, addressLine2, country, province, city, postalCode);

        assertSame(id, addressDto.getId());
        assertSame(addressLine1, addressDto.getAddressLine1());
        assertSame(addressLine2, addressDto.getAddressLine2());
        assertSame(country, addressDto.getCountry());
        assertSame(province, addressDto.getProvince());
        assertSame(city, addressDto.getCity());
        assertSame(postalCode, addressDto.getPostalCode());
    }

    @Test
    public void testGetSetId() {
        addressDto.setId(1L);
        assertSame(1L, addressDto.getId());
    }

    @Test
    public void testGetSetAddressLine1() {
        addressDto.setAddressLine1("line1");
        assertSame("line1", addressDto.getAddressLine1());
    }

    @Test
    public void testGetSetAddressLine2() {
        addressDto.setAddressLine2("line2");
        assertSame("line2", addressDto.getAddressLine2());
    }

    @Test
    public void testGetSetCity() {
        addressDto.setCity("city");
        assertSame("city", addressDto.getCity());
    }

    @Test
    public void testGetSetCountry() {
        addressDto.setCountry("country");
        assertSame("country", addressDto.getCountry());
    }

    @Test
    public void testGetSetProvince() {
        addressDto.setProvince("province");
        assertSame("province", addressDto.getProvince());
    }

    @Test
    public void testGetPostalCode() {
        addressDto.setPostalCode("postalCode");
        assertSame("postalCode", addressDto.getPostalCode());
    }
}
