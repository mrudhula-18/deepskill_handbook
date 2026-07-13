-- Seed data executed after Hibernate creates the schema
-- (spring.jpa.defer-datasource-initialization=true + spring.sql.init.mode=always)

INSERT INTO categories (id, name) VALUES (1, 'Electronics');
INSERT INTO categories (id, name) VALUES (2, 'Books');
INSERT INTO categories (id, name) VALUES (3, 'Home & Kitchen');

INSERT INTO products (id, name, price, category_id, created_at, updated_at) VALUES
    (1, 'Wireless Mouse', 19.99, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO products (id, name, price, category_id, created_at, updated_at) VALUES
    (2, 'Mechanical Keyboard', 79.50, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO products (id, name, price, category_id, created_at, updated_at) VALUES
    (3, '27-inch Monitor', 249.00, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO products (id, name, price, category_id, created_at, updated_at) VALUES
    (4, 'USB-C Hub', 34.75, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO products (id, name, price, category_id, created_at, updated_at) VALUES
    (5, 'Effective Java', 45.00, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO products (id, name, price, category_id, created_at, updated_at) VALUES
    (6, 'Clean Code', 38.20, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO products (id, name, price, category_id, created_at, updated_at) VALUES
    (7, 'Non-Stick Frying Pan', 25.99, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO products (id, name, price, category_id, created_at, updated_at) VALUES
    (8, 'Stainless Steel Kettle', 42.30, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO products (id, name, price, category_id, created_at, updated_at) VALUES
    (9, 'Blender 700W', 59.99, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
