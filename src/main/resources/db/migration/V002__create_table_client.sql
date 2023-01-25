CREATE TABLE IF NOT EXISTS store_db.order_item
(
    id serial not null primary key,
    count int not null,
    fk_order_id int not null,
    fk_product_id int not null
);

CREATE TABLE IF NOT EXISTS store_db.order
(
    id serial not null primary key,
    fk_client_id int not null
);

CREATE TABLE IF NOT EXISTS store_db.address
(
    id serial not null primary key,
    fk_client_id int not null,
    country text,
    city text,
    street text,
    house text
);

CREATE TABLE IF NOT EXISTS store_db.client
(
    id serial not null primary key,
    "name" text,
    email text,
    phone text
);
