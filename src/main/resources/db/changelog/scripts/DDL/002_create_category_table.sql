CREATE SEQUENCE category_sequence START 1 INCREMENT 1;

CREATE TABLE category
(
    id                 BIGINT NOT NULL,
    description        TEXT,
    name               TEXT   NOT NULL,
    parent_category_id BIGINT,
    CONSTRAINT pk_category PRIMARY KEY (id),
    CONSTRAINT fk_category_parent_category_id FOREIGN KEY (parent_category_id) REFERENCES category (id)
);