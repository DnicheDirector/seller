--liquibase formatted sql
--changeset alexander:seller-system-3

CREATE TABLE item
(
    id          UUID                     NOT NULL,
    created     TIMESTAMP WITH TIME ZONE NOT NULL,
    description VARCHAR(255),
    name        VARCHAR(255)             NOT NULL,
    category_id BIGINT                   NOT NULL,
    CONSTRAINT pk_item PRIMARY KEY (id),
    CONSTRAINT fk_item_category_id FOREIGN KEY (category_id) REFERENCES category (id)
);

--rollback DROP TABLE item;