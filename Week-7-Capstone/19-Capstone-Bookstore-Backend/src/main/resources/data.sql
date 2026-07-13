-- Seed data for the Bookstore Backend (Module 19)
-- Relies on IDENTITY columns auto-assigning ids 1, 2, 3, ... in insertion order.

INSERT INTO authors (name) VALUES ('J.K. Rowling');
INSERT INTO authors (name) VALUES ('Robert C. Martin');
INSERT INTO authors (name) VALUES ('Andy Weir');

INSERT INTO categories (name) VALUES ('Fiction');
INSERT INTO categories (name) VALUES ('Technology');
INSERT INTO categories (name) VALUES ('Science Fiction');

-- author_id 1 = J.K. Rowling, 2 = Robert C. Martin, 3 = Andy Weir
-- category_id 1 = Fiction, 2 = Technology, 3 = Science Fiction

INSERT INTO books (title, isbn, price, stock_quantity, author_id, category_id) VALUES
    ('Harry Potter and the Philosopher''s Stone', '9780747532699', 14.99, 40, 1, 1);
INSERT INTO books (title, isbn, price, stock_quantity, author_id, category_id) VALUES
    ('Harry Potter and the Chamber of Secrets', '9780747538493', 15.99, 35, 1, 1);
INSERT INTO books (title, isbn, price, stock_quantity, author_id, category_id) VALUES
    ('Harry Potter and the Prisoner of Azkaban', '9780747542155', 16.99, 30, 1, 1);
INSERT INTO books (title, isbn, price, stock_quantity, author_id, category_id) VALUES
    ('Clean Code', '9780132350884', 39.99, 25, 2, 2);
INSERT INTO books (title, isbn, price, stock_quantity, author_id, category_id) VALUES
    ('Clean Architecture', '9780134494166', 34.99, 20, 2, 2);
INSERT INTO books (title, isbn, price, stock_quantity, author_id, category_id) VALUES
    ('The Clean Coder', '9780137081073', 29.99, 18, 2, 2);
INSERT INTO books (title, isbn, price, stock_quantity, author_id, category_id) VALUES
    ('Agile Principles, Patterns, and Practices', '9780131857254', 44.99, 12, 2, 2);
INSERT INTO books (title, isbn, price, stock_quantity, author_id, category_id) VALUES
    ('The Martian', '9780553418026', 12.99, 50, 3, 3);
INSERT INTO books (title, isbn, price, stock_quantity, author_id, category_id) VALUES
    ('Project Hail Mary', '9780593135204', 17.99, 45, 3, 3);
INSERT INTO books (title, isbn, price, stock_quantity, author_id, category_id) VALUES
    ('Artemis', '9780553448122', 13.99, 22, 3, 3);
INSERT INTO books (title, isbn, price, stock_quantity, author_id, category_id) VALUES
    ('Harry Potter and the Goblet of Fire', '9780747546245', 18.99, 28, 1, 1);
INSERT INTO books (title, isbn, price, stock_quantity, author_id, category_id) VALUES
    ('Clean Agile', '9780135781869', 27.99, 15, 2, 2);
