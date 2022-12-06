--liquibase formatted sql

--changeset alexander:user-transactions-4

ALTER TABLE user_transactions.user_transaction
    ADD COLUMN status USER_TRANSACTION_STATUS NOT NULL DEFAULT 'SUCCESS';

--rollback ALTER TABLE user_transactions.user_transaction DROP COLUMN status;
