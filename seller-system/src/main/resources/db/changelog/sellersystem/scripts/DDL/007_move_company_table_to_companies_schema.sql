--liquibase formatted sql
--changeset alexander:seller-system-9

CREATE SCHEMA IF NOT EXISTS companies;

--rollback DROP SCHEMA companies;

--changeset alexander:seller-system-10

ALTER SEQUENCE company_sequence
SET SCHEMA companies;

--rollback ALTER SEQUENCE companies.company_sequence SET SCHEMA public;

ALTER TABLE company
SET SCHEMA companies;

--rollback ALTER TABLE companies.company SET SCHEMA public;
