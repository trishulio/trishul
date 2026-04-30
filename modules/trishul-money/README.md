# trishul-money

Monetary value handling using Joda-Money with support for currencies, taxes (GST/PST/HST), and amount calculations.

> **Use this when**: You need to store, calculate, or transfer monetary values with currency awareness and Canadian tax support.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-money</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Create a monetary value
Money price = Money.of(CurrencyUnit.CAD, 100.00);

// 2. Apply Canadian taxes (Ontario HST)
Tax tax = new Tax().setHstRate(new TaxRate(new BigDecimal("0.13")));
TaxAmount taxAmount = new TaxCalculator().calculate(price, tax);

// 3. Get the complete amount with auto-calculated total
Amount orderAmount = new Amount(price, taxAmount);
Money total = orderAmount.getTotal();  // CAD 113.00

// 4. Persist via JPA-embeddable entities
MoneyEntity entity = MoneyMapper.INSTANCE.toEntity(price);
```

## When Would I Use This?

### When you need currency-aware monetary values in JPA entities

Use `MoneyEntity` - a JPA-embeddable that stores amount and currency:

```java
@Entity
public class Order extends BaseEntity {
    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "price_amount"))
    @AssociationOverride(name = "currency",
        joinColumns = @JoinColumn(name = "price_currency_code"))
    private MoneyEntity price;
}
```

### When you need to calculate Canadian sales taxes

Use `Tax` with `TaxCalculator` - handles GST/PST/HST with mutual exclusion rules:

```java
// British Columbia: GST + PST
Tax bcTax = new Tax()
    .setGstRate(new TaxRate(new BigDecimal("0.05")))   // 5% GST
    .setPstRate(new TaxRate(new BigDecimal("0.07")));  // 7% PST

// Ontario: HST only (replaces GST+PST)
Tax onTax = new Tax()
    .setHstRate(new TaxRate(new BigDecimal("0.13")));  // 13% HST

// Calculate tax amounts
TaxAmount taxAmount = new TaxCalculator().calculate(subTotal, bcTax);
```

**Tax Validation Rules:**
- Cannot set GST when HST is present
- Cannot set PST when HST is present
- HST is mutually exclusive with GST/PST

### When you need order totals with subtotal + tax breakdown

Use `Amount` - auto-calculates total from subtotal and tax amounts:

```java
Money subTotal = Money.of(CurrencyUnit.CAD, 100.00);
TaxAmount taxAmount = calculator.calculate(subTotal, tax);

Amount orderAmount = new Amount(subTotal, taxAmount);
// Total auto-computed on @PrePersist: subTotal + taxAmount.getTotalTaxAmount()
```

### When you need to convert between Joda-Money and JPA entities

Use `MoneyMapper` - bidirectional conversion:

```java
// Domain → Persistence
Money price = Money.of(CurrencyUnit.USD, 99.99);
MoneyEntity entity = MoneyMapper.INSTANCE.toEntity(price);

// Persistence → Domain
Money restored = MoneyMapper.INSTANCE.fromEntity(entity);
```

### When you need monetary arithmetic

Use `MoneyCalculator` - safe arithmetic operations:

```java
MoneyCalculator calc = MoneyCalculator.INSTANCE;
// Add, subtract, multiply operations with currency validation
```

## Complete Example

```java
@Entity
public class Invoice extends BaseEntity {
    
    @Embedded
    private Amount invoiceAmount;
    
    @Embedded
    private Tax applicableTax;
    
    public Invoice calculateTotal(Money subTotal) {
        TaxCalculator calculator = new TaxCalculator();
        TaxAmount taxAmount = calculator.calculate(subTotal, applicableTax);
        this.invoiceAmount = new Amount(subTotal, taxAmount);
        return this;
    }
}

// Usage
Invoice invoice = new Invoice()
    .setApplicableTax(new Tax().setHstRate(new TaxRate(new BigDecimal("0.13"))))
    .calculateTotal(Money.of(CurrencyUnit.CAD, 500.00));

// invoice.getInvoiceAmount().getTotal() = CAD 565.00
```

## Reference

### Core Classes

| Class | Purpose |
|-------|---------|
| `MoneyEntity` | JPA-embeddable monetary value (amount + currency) |
| `Currency` | ISO 4217 currency entity (numeric/alphabetic codes) |
| `Tax` | Canadian tax rates container (GST/PST/HST) |
| `TaxRate` | Single tax rate as decimal (e.g., 0.13 = 13%) |
| `TaxAmount` | Calculated tax amounts per tax type |
| `Amount` | Complete order amount (subtotal + tax + total) |

### Mappers

| Mapper | Conversion |
|--------|------------|
| `MoneyMapper` | `Money` ↔ `MoneyEntity` |
| `CurrencyMapper` | `CurrencyUnit` ↔ `Currency` |
| `TaxMapper` | `Tax` ↔ `TaxDto` |
| `AmountMapper` | `Amount` ↔ `AmountDto` |

### Calculators

| Calculator | Purpose |
|------------|---------|
| `MoneyCalculator` | Monetary arithmetic (add, subtract, multiply) |
| `TaxCalculator` | Calculate tax amounts from rates |
| `TaxRateCalculator` | Get combined tax rate from Tax object |

### JSON Format

```json
{
  "amount": 99.99,
  "currency": "USD"
}
```

### Database Schema

```sql
-- Currency reference table
CREATE TABLE currency (
    numeric_code VARCHAR(3) PRIMARY KEY,
    alphabetic_code VARCHAR(3) UNIQUE NOT NULL,
    name VARCHAR(255)
);

-- Embedded columns example
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    price_amount DECIMAL(20,4),
    price_currency_code VARCHAR(3) REFERENCES currency(numeric_code),
    
    -- Amount fields
    total_amount DECIMAL(20,4),
    sub_total_amount DECIMAL(20,4),
    gst_amount DECIMAL(20,4),
    pst_amount DECIMAL(20,4),
    hst_amount DECIMAL(20,4),
    
    -- Tax rates
    gst_rate DECIMAL(10,4),
    pst_rate DECIMAL(10,4),
    hst_rate DECIMAL(10,4)
);
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-model` | Base entity classes |
| `trishul-quantity` | Physical quantity handling (complementary domain type) |
