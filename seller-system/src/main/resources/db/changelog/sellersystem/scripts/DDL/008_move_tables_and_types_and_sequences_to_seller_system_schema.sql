--liquibase formatted sql
--changeset alexander:seller-system-11

CREATE SCHEMA seller_system;

ALTER TYPE ROLE
SET SCHEMA seller_system;

--rollback ALTER TYPE seller_system.ROLE SET SCHEMA public;

ALTER SEQUENCE category_sequence
SET SCHEMA seller_system;

--rollback ALTER SEQUENCE seller_system.category_sequence SET SCHEMA public;

ALTER SEQUENCE position_sequence
SET SCHEMA seller_system;

--rollback ALTER SEQUENCE seller_system.position_sequence SET SCHEMA public;

ALTER TABLE category
SET SCHEMA seller_system;

--rollback ALTER TABLE seller_system.category SET SCHEMA public;

ALTER TABLE item
SET SCHEMA seller_system;

--rollback ALTER TABLE seller_system.item SET SCHEMA public;

ALTER TABLE users
SET SCHEMA seller_system;

--rollback ALTER TABLE seller_system.users SET SCHEMA public;

ALTER TABLE position
SET SCHEMA seller_system;

--rollback ALTER TABLE seller_system.position SET SCHEMA public;

--rollback DROP SCHEMA seller_system;