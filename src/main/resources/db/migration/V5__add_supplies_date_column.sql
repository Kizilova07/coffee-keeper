alter table supplies
add column "date" varchar(15) not null;

alter table remains add unique (retail_product_id);