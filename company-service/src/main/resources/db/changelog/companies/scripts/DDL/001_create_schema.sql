--liquibase formatted sql
--changeset alexander:companies-1

CREATE SCHEMA IF NOT EXISTS companies;

--rollback DROP SCHEMA companies;