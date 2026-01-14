
BEGIN;
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description  VARCHAR(255),
    thumbnail VARCHAR(255) ,
    category_id INTEGER NOT NULL,
   created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT product_category_fk FOREIGN KEY (category_id) REFERENCES categories(id)
);
COMMIT;