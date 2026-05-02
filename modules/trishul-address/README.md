# trishul-address

Reusable address entity base class for JPA entities requiring postal address fields.

> **Use this when**: You need a consistent address structure (street, city, province, country, postal code) across multiple domain entities.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-address</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Extend the Address base class for your entity
@Entity
@Table(name = "customer_address")
public class CustomerAddress extends Address {
    @Id
    private Long id;

    @ManyToOne
    private Customer customer;
}

// 2. Use fluent setters
CustomerAddress address = new CustomerAddress()
    .setAddressLine1("123 Main St")
    .setCity("Toronto")
    .setProvince("ON")
    .setCountry("Canada")
    .setPostalCode("M5V 1A1");

// 3. Map to DTO for API responses
AddressDto dto = addressMapper.toDto(address);
```

## When Would I Use This?

### When you need address fields in multiple entity types

Extend `Address` as a `@MappedSuperclass` - avoids duplicating address fields:

```java
@Entity
@Table(name = "billing_address")
public class BillingAddress extends Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Invoice invoice;
}

@Entity
@Table(name = "shipping_address")
public class ShippingAddress extends Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;
}
```

Both entities inherit all address fields with consistent column names.

### When you need address DTOs for API responses

Use `AddressDto` and `AddressMapper`:

```java
// Entity → DTO
AddressDto dto = addressMapper.toDto(customerAddress);

// DTO → Entity (generic mapping)
Address address = addressMapper.fromDto(dto);
```

### When you need audit timestamps on addresses

`Address` includes built-in audit fields:

```java
@CreationTimestamp
@Column(name = "created_at", updatable = false)
private LocalDateTime createdAt;

@UpdateTimestamp
@Column(name = "last_updated")
private LocalDateTime lastUpdated;
```

## Address Fields

| Field | Column | Type | Description |
|-------|--------|------|-------------|
| `addressLine1` | `address_line_1` | String | Primary street address |
| `addressLine2` | `address_line_2` | String | Secondary address (apt, suite) |
| `city` | `city` | String | City name |
| `province` | `province` | String | Province/State |
| `country` | `country` | String | Country name |
| `postalCode` | `postal_code` | String | Postal/ZIP code |
| `createdAt` | `created_at` | LocalDateTime | Creation timestamp (auto) |
| `lastUpdated` | `last_updated` | LocalDateTime | Update timestamp (auto) |

## Complete Example

```java
// Entity definition
@Entity
@Table(name = "facility_address")
public class FacilityAddress extends Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "address")
    private Facility facility;

    public Long getId() {
        return id;
    }

    public FacilityAddress setId(Long id) {
        this.id = id;
        return this;
    }
}

// Repository
public interface FacilityAddressRepository extends JpaRepository<FacilityAddress, Long> {
    List<FacilityAddress> findByCity(String city);
    List<FacilityAddress> findByProvince(String province);
}

// Service usage
@Service
public class FacilityService {

    public Facility createFacility(String name, AddressDto addressDto) {
        FacilityAddress address = new FacilityAddress()
            .setAddressLine1(addressDto.getAddressLine1())
            .setAddressLine2(addressDto.getAddressLine2())
            .setCity(addressDto.getCity())
            .setProvince(addressDto.getProvince())
            .setCountry(addressDto.getCountry())
            .setPostalCode(addressDto.getPostalCode());

        return new Facility()
            .setName(name)
            .setAddress(address);
    }
}
```

## Reference

### Core Classes

| Class | Purpose |
|-------|---------|
| `Address` | `@MappedSuperclass` with address fields |
| `AddressDto` | DTO for API transfer |
| `AddressMapper` | Entity ↔ DTO mapping interface |

### Database Schema

```sql
-- Example table for a subclass entity
CREATE TABLE facility_address (
    id BIGSERIAL PRIMARY KEY,
    address_line_1 VARCHAR(255),
    address_line_2 VARCHAR(255),
    city VARCHAR(255),
    province VARCHAR(255),
    country VARCHAR(255),
    postal_code VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-model` | Base entity classes |
| `trishul-tenant` | Tenant entity with address |
