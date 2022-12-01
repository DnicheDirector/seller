--liquibase formatted sql
--changeset alexander:seller-system-1

CREATE SEQUENCE company_sequence START 1 INCREMENT 1;

--rollback DROP TABLE company;

CREATE TABLE company
(
    id          BIGINT                   NOT NULL,
    created     TIMESTAMP WITH TIME ZONE NOT NULL,
    description VARCHAR(255)             NOT NULL,
    email       VARCHAR(255)             NOT NULL,
    name        VARCHAR(255)             NOT NULL,
    CONSTRAINT pk_company PRIMARY KEY (id)
);

--rollback DROP SEQUENCE company_sequence;