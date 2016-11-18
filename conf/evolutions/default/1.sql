# Products schema

# --- !Ups

create table order_bean (
  id                        bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  email_address             varchar(255),
  shipping_option           varchar(255),
  street                    varchar(255),
  city                      varchar(255),
  province                  varchar(255),
  postal_code               varchar(255)
);

create sequence order_bean_seq start with 1000;

# --- !Downs

DROP TABLE order_bean;