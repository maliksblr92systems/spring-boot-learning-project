BEGIN;
-- contents of V1__init_schema.sql
CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(355),
    last_name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(255),
    city VARCHAR(255),
    country VARCHAR(255),
    age INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()

--    created_at TIMESTAMP NOT NULL DEFAULT now()
);
COMMIT;