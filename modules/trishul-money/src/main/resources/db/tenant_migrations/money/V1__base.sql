CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE CURRENCY (
    NUMERIC_CODE INTEGER PRIMARY KEY,
    CODE CHAR(3) NOT NULL
);
INSERT INTO CURRENCY VALUES (124, 'CAD');
