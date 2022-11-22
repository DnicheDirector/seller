CREATE SCHEMA seller_system;

ALTER TYPE ROLE
SET SCHEMA seller_system;

ALTER SEQUENCE category_sequence
SET SCHEMA seller_system;

ALTER SEQUENCE position_sequence
SET SCHEMA seller_system;

ALTER TABLE category
SET SCHEMA seller_system;

ALTER TABLE item
SET SCHEMA seller_system;

ALTER TABLE users
SET SCHEMA seller_system;

ALTER TABLE position
SET SCHEMA seller_system;