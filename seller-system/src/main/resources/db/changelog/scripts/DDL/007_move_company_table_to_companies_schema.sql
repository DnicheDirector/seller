CREATE SCHEMA IF NOT EXISTS companies;

ALTER SEQUENCE company_sequence
SET SCHEMA companies;

ALTER TABLE company
SET SCHEMA companies;
