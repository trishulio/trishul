package io.trishul.address.model.dto;

public class AddressDto {
    private Long id;

    private String addressLine1;

    private String addressLine2;

    private String country;

    private String province;

    private String city;

    private String postalCode;

    public AddressDto() {}

    public AddressDto(
            Long id,
            String addressLine1,
            String addressLine2,
            String country,
            String province,
            String city,
            String postalCode) {
        this.id = id;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.country = country;
        this.province = province;
        this.city = city;
        this.postalCode = postalCode;
    }

    public Long getId() {
        return id;
    }

    public final void setId(Long id) {
        this.id = id;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public final void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public final void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCountry() {
        return country;
    }

    public final void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public final void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public final void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public final void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
