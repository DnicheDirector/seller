CREATE SEQUENCE IF NOT EXISTS companies.company_sequence START 1 INCREMENT 1;

CREATE TABLE IF NOT EXISTS companies.company
(
    id          BIGINT                   NOT NULL,
    created     TIMESTAMP WITH TIME ZONE NOT NULL,
    description VARCHAR(255)             NOT NULL,
    email       VARCHAR(255)             NOT NULL,
    name        VARCHAR(255)             NOT NULL,
    CONSTRAINT pk_company PRIMARY KEY (id)
);