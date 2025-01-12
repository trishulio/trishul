package io.trishul.address.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.trishul.model.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Address extends BaseEntity {
  @Column(name = "address_line_1")
  private String addressLine1;

  @Column(name = "address_line_2")
  private String addressLine2;

  @Column(name = "country")
  private String country;

  @Column(name = "province")
  private String province;

  @Column(name = "city")
  private String city;

  @Column(name = "postal_code")
  private String postalCode;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "last_updated")
  private LocalDateTime lastUpdated;

  public Address() {}

  public Address(String addressLine1, String addressLine2, String country, String province,
      String city, String postalCode, LocalDateTime createdAt, LocalDateTime lastUpdated) {
    this();
    this.addressLine1 = addressLine1;
    this.addressLine2 = addressLine2;
    this.country = country;
    this.province = province;
    this.city = city;
    this.postalCode = postalCode;
    this.createdAt = createdAt;
    this.lastUpdated = lastUpdated;
  }

  public String getAddressLine1() {
    return addressLine1;
  }

  public Address setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
    return this;
  }

  public String getAddressLine2() {
    return addressLine2;
  }

  public Address setAddressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
    return this;
  }

  public String getCountry() {
    return country;
  }

  public Address setCountry(String country) {
    this.country = country;
    return this;
  }

  public String getProvince() {
    return province;
  }

  public Address setProvince(String province) {
    this.province = province;
    return this;
  }

  public String getCity() {
    return city;
  }

  public Address setCity(String city) {
    this.city = city;
    return this;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public Address setPostalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public Address setCreatedAt(LocalDateTime created) {
    this.createdAt = created;
    return this;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public Address setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }
}
