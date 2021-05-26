CREATE TABLE IF NOT EXISTS user_roles
(
    id          SERIAL        PRIMARY KEY,
    "role"        VARCHAR(50)   NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS "users"
(
    id           SERIAL PRIMARY KEY,
    "name"  VARCHAR(50)      NOT NULL,
    phone  VARCHAR(15)      NOT NULL UNIQUE,
    email  VARCHAR(50)      NOT NULL UNIQUE,
    login  VARCHAR(50)      NOT NULL UNIQUE,
    password  VARCHAR(50)      NOT NULL UNIQUE,
    user_role_id BIGINT NOT NULL,
    CONSTRAINT "user_role_id_fkey" FOREIGN KEY (user_role_id) REFERENCES user_roles (id)
);

CREATE TABLE IF NOT EXISTS "suppliers"
(
    id         SERIAL PRIMARY KEY,
    "name" VARCHAR(50) NOT NULL UNIQUE,
	address VARCHAR(50) NOT NULL,
	phone VARCHAR(15) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS "retail_products"
(
    id          SERIAL PRIMARY KEY,
    "name"      VARCHAR(50) NOT NULL UNIQUE,
	description VARCHAR(100) NOT NULL,
	cost      NUMERIC(10, 2) NOT NULL,
	raw_cost NUMERIC(10, 2) NOT NULL,
    supplier_id     BIGINT     NOT NULL,
    CONSTRAINT "supplier_id_fkey" FOREIGN KEY (supplier_id) REFERENCES suppliers (id)
);

CREATE TABLE IF NOT EXISTS "sales"
(
    id          SERIAL PRIMARY KEY,
    "date" VARCHAR(12) NOT NULL,
	income      NUMERIC(10, 2) NOT NULL,
	profit      NUMERIC(10, 2) NOT NULL,
	payment_method VARCHAR(12) NOT NULL,
	user_id     BIGINT        NOT NULL,
    CONSTRAINT "user_id_fkey" FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS "sale_products"
(
    id          SERIAL PRIMARY KEY,
	amount     NUMERIC(10, 2) NOT NULL,
    retail_product_id     BIGINT NOT NULL,
    sale_id         BIGINT NOT NULL,
    CONSTRAINT "sale_id_fkey" FOREIGN KEY (sale_id) REFERENCES sales (id),
    CONSTRAINT "retail_product_id_fkey" FOREIGN KEY (retail_product_id) REFERENCES retail_products (id)
);

CREATE TABLE IF NOT EXISTS "cancellations"
(
    id          SERIAL PRIMARY KEY,
    "date" VARCHAR(12) NOT NULL,
    comment VARCHAR(100) NOT NULL,
	amount      NUMERIC(10, 2) NOT NULL,
	user_id     BIGINT        NOT NULL,
    retail_product_id     BIGINT NOT NULL,
    CONSTRAINT "user_id_fkey" FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT "retail_product_id_fkey" FOREIGN KEY (retail_product_id) REFERENCES retail_products (id)
);

CREATE TABLE IF NOT EXISTS "reports"
(
    id          SERIAL PRIMARY KEY,
    "date" VARCHAR(12) NOT NULL,
	description VARCHAR(100) NOT NULL,
	user_id     BIGINT        NOT NULL,
    sale_id     BIGINT,
    cancellation_id     BIGINT,
    CONSTRAINT "user_id_fkey" FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT "sale_id_fkey" FOREIGN KEY (sale_id) REFERENCES sales (id),
    CONSTRAINT "cancellation_id_fkey" FOREIGN KEY (cancellation_id) REFERENCES cancellations (id)
);