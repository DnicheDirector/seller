--liquibase formatted sql
--changeset alexander:seller-system-7

ALTER TABLE users
DROP CONSTRAINT fk_users_company_id;

--rollback ALTER TABLE users ADD CONSTRAINT fk_users_company_id FOREIGN KEY (company_id) REFERENCES company (id);

--changeset alexander:seller-system-8

ALTER TABLE position
DROP CONSTRAINT fk_position_company_id;

--rollback ALTER TABLE position ADD CONSTRAINT fk_position_company_id FOREIGN KEY (company_id) REFERENCES company (id);

