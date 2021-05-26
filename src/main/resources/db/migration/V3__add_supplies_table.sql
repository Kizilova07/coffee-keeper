CREATE TABLE IF NOT EXISTS "supplies"
(
    id          SERIAL PRIMARY KEY,
	description VARCHAR(100) NOT NULL,
	amount      NUMERIC(10, 2) NOT NULL,
	cost NUMERIC(10, 2) NOT NULL,
    retail_product_id     BIGINT NOT NULL,
    CONSTRAINT "retail_product_id_fkey" FOREIGN KEY (retail_product_id) REFERENCES retail_products (id)
);