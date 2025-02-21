CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE QTY_UNIT (
    SYMBOL VARCHAR(4) PRIMARY KEY,
    NAME VARCHAR(255) UNIQUE NOT NULL,
    BASE_UNIT_SYMBOL VARCHAR(4) REFERENCES QTY_UNIT(SYMBOL)
);

--Predefined Quantity Units
INSERT INTO QTY_UNIT VALUES
    ('mg', 'Milligram', 'kg'),
    ('g', 'Gram', 'kg'),
    ('kg', 'Kilogram', 'kg'),
    ('ml', 'Millilitre', 'l'),
    ('l', 'Litre', 'l'),
    ('hl', 'Hectolitre', 'l'),
    ('each', 'Each', 'each');
