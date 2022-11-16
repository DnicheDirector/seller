CREATE SEQUENCE position_sequence START 1 INCREMENT 1;

CREATE TABLE position
(
    id            BIGINT                   NOT NULL,
    amount        NUMERIC(19, 2)           NOT NULL,
    created       TIMESTAMP WITH TIME ZONE NOT NULL,
    company_id    BIGINT                   NOT NULL,
    created_by_id UUID                     NOT NULL,
    item_id       UUID                     NOT NULL,
    CONSTRAINT pk_position PRIMARY KEY (id),
    CONSTRAINT fk_position_company_id FOREIGN KEY (company_id) REFERENCES company (id),
    CONSTRAINT fk_position_created_by_id FOREIGN KEY (created_by_id) REFERENCES users (id),
    CONSTRAINT fk_position_item_id FOREIGN KEY (item_id) REFERENCES item (id)
);