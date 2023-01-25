CREATE SCHEMA IF NOT EXISTS store_db;

CREATE TABLE IF NOT EXISTS store_db.product
(
    id          serial  not null primary key,
    "name"      text    not null,
    description text,
    price       decimal not null
);