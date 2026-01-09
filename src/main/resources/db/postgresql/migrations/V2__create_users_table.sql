BEGIN;
-- contents of V1__init_schema.sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(355),
    email VARCHAR(255) UNIQUE,
    username VARCHAR(255) UNIQUE,
    phone VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()

--    created_at TIMESTAMP NOT NULL DEFAULT now()
);
COMMIT;