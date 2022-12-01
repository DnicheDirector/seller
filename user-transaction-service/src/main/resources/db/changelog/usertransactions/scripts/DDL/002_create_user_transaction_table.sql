--liquibase formatted sql
--changeset alexander:user-transactions-2

CREATE SEQUENCE user_transactions.user_transaction_sequence START 1 INCREMENT 1;

--rollback DROP TABLE user_transactions.user_transaction;

CREATE TABLE user_transactions.user_transaction
(
    id            BIGINT                   NOT NULL,
    position_id    BIGINT                   NOT NULL,
    amount        NUMERIC(19, 2)           NOT NULL,
    created       TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by_id UUID                     NOT NULL,
    CONSTRAINT pk_user_transaction PRIMARY KEY (id)
);

--rollback DROP SEQUENCE user_transactions.user_transaction_sequence;