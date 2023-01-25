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


alter table store_db.order_item
    add constraint fk_order_item_product foreign key (fk_product_id) references store_db.product
        on update cascade on delete cascade;

alter table store_db.order_item
    add constraint fk_order_item_order foreign key (fk_order_id) references store_db.order
        on update cascade on delete cascade;

alter table store_db.address
    add constraint fk_address_client foreign key (fk_client_id) references store_db.client
        on update cascade on delete cascade;

alter table store_db.order
    add constraint fk_order_client foreign key (fk_client_id) references store_db.client
        on update cascade on delete cascade
