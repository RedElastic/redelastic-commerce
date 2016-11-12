# Products schema

# --- !Ups

CREATE TABLE Product (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    sku varchar(30) NOT NULL,
    category_name varchar(80) NOT NULL,
    name varchar(255) NOT NULL,
    rich_content VARCHAR(MAX) NOT NULL,
    PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE Product;