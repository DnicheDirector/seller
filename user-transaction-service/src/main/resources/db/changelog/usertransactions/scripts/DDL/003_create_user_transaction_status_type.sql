--liquibase formatted sql
--changeset alexander:user-transactions-3

CREATE TYPE USER_TRANSACTION_STATUS AS ENUM ('IN_PROGRESS', 'SUCCESS', 'ERROR');

--rollback DROP TYPE USER_TRANSACTION_STATUS;