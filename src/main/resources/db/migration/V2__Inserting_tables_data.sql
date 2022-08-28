INSERT INTO products (name, product_value) VALUES ('Estojo', 25.0);
INSERT INTO products (name, product_value) VALUES ('Teclado', 300.0);
INSERT INTO products (name, product_value) VALUES ('PS5', 2500.0);
INSERT INTO products (name, product_value) VALUES ('Mouse', 300.0);

INSERT INTO orders (created_at, total) VALUES (CURRENT_TIMESTAMP, 325.0);
INSERT INTO orders (created_at, total) VALUES (CURRENT_TIMESTAMP, 2800.0);
INSERT INTO orders (created_at, total) VALUES (CURRENT_TIMESTAMP, 2525.0);
INSERT INTO orders (created_at, total) VALUES (CURRENT_TIMESTAMP, 600.0);

INSERT INTO clients (name) VALUES ('Rodrigo de Souza Paula');
INSERT INTO clients (name) VALUES ('João Freitas da Silva');

INSERT INTO tb_order_product (order_id, product_id) VALUES (1, 1);
INSERT INTO tb_order_product (order_id, product_id) VALUES (1, 2);
INSERT INTO tb_order_product (order_id, product_id) VALUES (2, 3);
INSERT INTO tb_order_product (order_id, product_id) VALUES (2, 4);
INSERT INTO tb_order_product (order_id, product_id) VALUES (3, 1);
INSERT INTO tb_order_product (order_id, product_id) VALUES (3, 3);
INSERT INTO tb_order_product (order_id, product_id) VALUES (4, 2);
INSERT INTO tb_order_product (order_id, product_id) VALUES (4, 4);

INSERT INTO tb_client_order (client_id, order_id) VALUES (1, 1);
INSERT INTO tb_client_order (client_id, order_id) VALUES (1, 2);
INSERT INTO tb_client_order (client_id, order_id) VALUES (2, 3);
INSERT INTO tb_client_order (client_id, order_id) VALUES (2, 4);