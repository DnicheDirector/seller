INSERT INTO CATEGORY (id, description, name) VALUES (10, 'description', 'electronics');
INSERT INTO ITEM (id, created, description, name, category_id) VALUES
('b812e9a3-9c8f-46cd-ae79-cabecded7852', '2022-02-05T12:59:11.332', 'description1', 'notebook', 10),
('fd422f25-7e5e-4b90-9d5f-cc835aa0900f', '2021-02-05T12:59:11.332', 'description2', 'smartphone', 10);

INSERT INTO COMPANY (id, created, description, email, name) VALUES (100, '2020-02-05T12:59:11.332', 'description', 'example2@gmail.com', 'company100');

INSERT INTO USERS (id, created, email, name, role, updated, username, company_id) VALUES ('fd422f25-7e5e-4b90-9d5f-cc835aa0900f', '2021-02-05T12:59:11.332', 'user1@gmail.com', 'Sasha', 'DIRECTOR','2021-02-05T12:59:11.332', 'username1', 100);

INSERT INTO POSITION (id, amount, created, company_id, created_by_id, item_id) VALUES
(10, 1000, '2022-09-05T12:59:11.332', 100, 'fd422f25-7e5e-4b90-9d5f-cc835aa0900f', 'b812e9a3-9c8f-46cd-ae79-cabecded7852'),
(11, 500, '2021-09-05T12:59:11.332', 100, 'fd422f25-7e5e-4b90-9d5f-cc835aa0900f', 'fd422f25-7e5e-4b90-9d5f-cc835aa0900f');