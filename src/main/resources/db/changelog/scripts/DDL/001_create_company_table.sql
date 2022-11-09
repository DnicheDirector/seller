CREATE SEQUENCE company_sequence START 1 INCREMENT 1;

CREATE TABLE company
(
    id          BIGINT    NOT NULL,
    created     TIMESTAMP NOT NULL,
    description TEXT      NOT NULL,
    email       TEXT      NOT NULL,
    name        TEXT      NOT NULL,
    CONSTRAINT pk_company PRIMARY KEY (id)
);