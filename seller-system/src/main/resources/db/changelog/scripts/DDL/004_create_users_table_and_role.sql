CREATE TYPE ROLE AS ENUM ('MANAGER', 'DIRECTOR', 'STORAGE_MANAGER');

CREATE TABLE users
(
    id         UUID                     NOT NULL,
    created    TIMESTAMP WITH TIME ZONE NOT NULL,
    email      VARCHAR(255)             NOT NULL,
    name       VARCHAR(255)             NOT NULL,
    role       ROLE                     NOT NULL,
    updated    TIMESTAMP WITH TIME ZONE NOT NULL,
    username   VARCHAR(255)             NOT NULL,
    company_id BIGINT                   NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT fk_users_company_id FOREIGN KEY (company_id) REFERENCES company (id)
);