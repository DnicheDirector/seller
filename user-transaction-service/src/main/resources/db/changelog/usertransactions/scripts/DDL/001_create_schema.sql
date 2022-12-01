--liquibase formatted sql
--changeset alexander:user-transactions-1

CREATE SCHEMA IF NOT EXISTS user_transactions;

--rollback DROP SCHEMA user_transactions;