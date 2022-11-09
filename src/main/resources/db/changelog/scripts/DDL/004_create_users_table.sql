CREATE TABLE users
(
    id         UUID      NOT NULL,
    created    TIMESTAMP NOT NULL,
    email      TEXT      NOT NULL,
    name       TEXT      NOT NULL,
    role       TEXT      NOT NULL,
    updated    TIMESTAMP NOT NULL,
    username   TEXT      NOT NULL,
    company_id BIGINT    NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT fk_users_company_id FOREIGN KEY (company_id) REFERENCES company (id)
);