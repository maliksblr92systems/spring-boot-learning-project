BEGIN;
-- contents of V1__init_schema.sql
CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    thumbnail VARCHAR(255) ,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
    

--    created_at TIMESTAMP NOT NULL DEFAULT now()
);
COMMIT;