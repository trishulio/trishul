# trishul-quantity

Physical quantity handling using JSR-385 (Units of Measurement API) with support for mass, volume, and discrete units.

> **Use this when**: You need to store, calculate, or convert physical quantities (weight, volume, count) with unit awareness in your domain entities.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-quantity</artifactId>
</dependency>
```

## Quick Start

```java
import static io.trishul.quantity.unit.SupportedUnits.*;
import tec.uom.se.quantity.Quantities;

// 1. Create quantities with units
Quantity<Volume> beer = Quantities.getQuantity(500, MILLILITRE);
Quantity<Mass> hops = Quantities.getQuantity(50, GRAM);

// 2. Perform arithmetic
QuantityCalculator calc = QuantityCalculator.INSTANCE;
Quantity<?> totalVolume = calc.add(batch1, batch2);

// 3. Persist via JPA-embeddable entities
QuantityEntity entity = quantityMapper.toEntity(beer);
```

## When Would I Use This?

### When you need unit-aware quantities in JPA entities

Use `QuantityEntity` - a JPA-embeddable that stores value and unit:

```java
@Entity
public class Ingredient extends BaseEntity {

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "quantity_value"))
    @AssociationOverride(name = "unit",
        joinColumns = @JoinColumn(name = "quantity_unit_symbol", referencedColumnName = "symbol"))
    private QuantityEntity quantity;

    public Quantity<?> getQuantity() {
        return quantityMapper.fromEntity(quantity);
    }
}
```

### When you need to add or subtract quantities safely

Use `QuantityCalculator` - validates unit compatibility before arithmetic:

```java
QuantityCalculator calc = QuantityCalculator.INSTANCE;

Quantity<Volume> batch1 = Quantities.getQuantity(100, LITRE);
Quantity<Volume> batch2 = Quantities.getQuantity(50, LITRE);

// Same dimension - works
Quantity<?> total = calc.add(batch1, batch2);  // 150 l

// Different dimensions - throws exception
Quantity<Mass> grain = Quantities.getQuantity(10, KILOGRAM);
calc.add(batch1, grain);  // Throws IncompatibleQuantityUnitException
```

### When you need to check unit compatibility before operations

Use `QuantityCalculator.areCompatibleQuantities()`:

```java
Quantity<Volume> volume = Quantities.getQuantity(100, LITRE);
Quantity<Mass> mass = Quantities.getQuantity(10, KILOGRAM);

QuantityCalculator calc = QuantityCalculator.INSTANCE;
calc.areCompatibleQuantities(volume1, volume2);  // true (both Volume)
calc.areCompatibleQuantities(volume, mass);      // false (Volume vs Mass)
calc.isExpectedUnit(LITRE, volume);              // true
```

### When you need to convert between units

Use `QuantityCalculator` conversion methods:

```java
Quantity<Volume> inMl = Quantities.getQuantity(500, MILLILITRE);

// Convert to system unit value while keeping display unit
Quantity<?> systemValue = calc.toSystemQuantityValueWithDisplayUnit(inMl);

// Convert back to display unit value
Quantity<Volume> displayValue = calc.fromSystemQuantityValueWithDisplayUnit(inMl);
```

### When you need discrete counts (not mass/volume)

Use `SupportedUnits.EACH`:

```java
Quantity<AmountOfSubstance> bottles = Quantities.getQuantity(24, EACH);
Quantity<AmountOfSubstance> cases = Quantities.getQuantity(10, EACH);

Quantity<?> total = calc.add(bottles, cases);  // 34 each
```

## Supported Units

| Unit | Symbol | Type | Conversion |
|------|--------|------|------------|
| `EACH` | `each` | Discrete | Base unit |
| `LITRE` | `l` | Volume | Base unit |
| `MILLILITRE` | `ml` | Volume | 1/1000 l |
| `HECTOLITRE` | `hl` | Volume | 100 l |
| `KILOGRAM` | `kg` | Mass | Base unit |
| `GRAM` | `g` | Mass | 1/1000 kg |
| `MILLIGRAM` | `mg` | Mass | 1/1000000 kg |

**Defaults:**
- `DEFAULT_MASS = KILOGRAM`
- `DEFAULT_VOLUME = LITRE`

## Complete Example

```java
@Entity
public class Recipe extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL)
    private List<RecipeIngredient> ingredients;

    public Quantity<Mass> getTotalMass() {
        QuantityCalculator calc = QuantityCalculator.INSTANCE;
        return ingredients.stream()
            .map(RecipeIngredient::getQuantity)
            .filter(q -> calc.isExpectedUnit(KILOGRAM, q) || calc.isExpectedUnit(GRAM, q))
            .reduce(Quantities.getQuantity(0, KILOGRAM), calc::add);
    }
}

@Entity
public class RecipeIngredient extends BaseEntity {

    @Embedded
    private QuantityEntity quantity;

    @ManyToOne
    private Ingredient ingredient;
}
```

## Adding New Units

1. Define in `SupportedUnits.java`:

```java
public static final Unit<Volume> GALLON = new TransformedUnit<>("gal", LITRE,
    LITRE.getSystemUnit(), RationalConverter.of(3785, 1000));
```

2. Add to database:

```sql
INSERT INTO qty_unit (symbol, name) VALUES ('gal', 'Gallon');
```

## Reference

### Core Classes

| Class | Purpose |
|-------|---------|
| `QuantityEntity` | JPA-embeddable quantity (value + unit) |
| `UnitEntity` | Unit of measurement entity (symbol) |
| `SupportedUnits` | Predefined units (LITRE, KILOGRAM, EACH, etc.) |
| `QuantityCalculator` | Arithmetic and unit validation |
| `IncompatibleQuantityUnitException` | Thrown on incompatible unit operations |

### Mappers

| Mapper | Conversion |
|--------|------------|
| `QuantityMapper` | `Quantity<?>` ↔ `QuantityEntity` |
| `QuantityUnitMapper` | `Unit<?>` ↔ `UnitEntity` |

### Accessor Interfaces

| Interface | Purpose |
|-----------|---------|
| `QuantityAccessor<T>` | Entity with `getQuantity()`/`setQuantity()` |
| `BaseQuantityUnitAccessor<T>` | Entity with base unit accessor |

### JSON Format

```json
{
  "value": 500.0,
  "unit": "ml"
}
```

### Database Schema

```sql
-- Unit reference table
CREATE TABLE qty_unit (
    symbol VARCHAR(10) PRIMARY KEY,
    name VARCHAR(255)
);

-- Seed data
INSERT INTO qty_unit (symbol, name) VALUES
    ('each', 'Each'), ('l', 'Litre'), ('ml', 'Millilitre'),
    ('hl', 'Hectolitre'), ('kg', 'Kilogram'), ('g', 'Gram'), ('mg', 'Milligram');

-- Embedded columns example
CREATE TABLE ingredient (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    quantity_value DECIMAL(20,4),
    quantity_unit_symbol VARCHAR(10) REFERENCES qty_unit(symbol)
);
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-model` | Base entity classes |
| `trishul-money` | Monetary value handling (complementary domain type) |
