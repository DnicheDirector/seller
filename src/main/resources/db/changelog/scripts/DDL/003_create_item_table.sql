CREATE TABLE item
(
    id          UUID      NOT NULL,
    created     TIMESTAMP NOT NULL,
    description TEXT,
    name        TEXT      NOT NULL,
    category_id BIGINT    NOT NULL,
    CONSTRAINT pk_item PRIMARY KEY (id),
    CONSTRAINT fk_item_category_id FOREIGN KEY (category_id) REFERENCES category (id)
);