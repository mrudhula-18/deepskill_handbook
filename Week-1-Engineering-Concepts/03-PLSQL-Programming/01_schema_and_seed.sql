--------------------------------------------------------------------------------
-- 01_schema_and_seed.sql
-- Creates the DEPARTMENTS and EMPLOYEES tables used by every script in this
-- module, and seeds them with sample data.
--------------------------------------------------------------------------------

-- Uncomment the lines below if you need to re-run this script against a
-- schema where these objects already exist.
-- DROP TABLE employees PURGE;
-- DROP TABLE departments PURGE;

CREATE TABLE departments (
    dept_id     NUMBER PRIMARY KEY,
    dept_name   VARCHAR2(50)
);

CREATE TABLE employees (
    emp_id      NUMBER PRIMARY KEY,
    emp_name    VARCHAR2(50),
    salary      NUMBER,
    dept_id     NUMBER REFERENCES departments(dept_id)
);

--------------------------------------------------------------------------------
-- Seed data: departments
--------------------------------------------------------------------------------
INSERT INTO departments (dept_id, dept_name) VALUES (10, 'IT');
INSERT INTO departments (dept_id, dept_name) VALUES (20, 'HR');
INSERT INTO departments (dept_id, dept_name) VALUES (30, 'Finance');

--------------------------------------------------------------------------------
-- Seed data: employees (spread across the three departments, varied salaries)
--------------------------------------------------------------------------------
INSERT INTO employees (emp_id, emp_name, salary, dept_id) VALUES (101, 'Arun Kumar',     55000, 10);
INSERT INTO employees (emp_id, emp_name, salary, dept_id) VALUES (102, 'Bhavna Rao',     72000, 10);
INSERT INTO employees (emp_id, emp_name, salary, dept_id) VALUES (103, 'Chetan Iyer',    48000, 10);
INSERT INTO employees (emp_id, emp_name, salary, dept_id) VALUES (104, 'Divya Menon',    39000, 20);
INSERT INTO employees (emp_id, emp_name, salary, dept_id) VALUES (105, 'Esha Kapoor',    44500, 20);
INSERT INTO employees (emp_id, emp_name, salary, dept_id) VALUES (106, 'Farhan Sheikh',  61000, 30);
INSERT INTO employees (emp_id, emp_name, salary, dept_id) VALUES (107, 'Geeta Nair',     83000, 30);
INSERT INTO employees (emp_id, emp_name, salary, dept_id) VALUES (108, 'Harish Patil',   29500, 20);
INSERT INTO employees (emp_id, emp_name, salary, dept_id) VALUES (109, 'Ishita Verma',   95000, 10);
INSERT INTO employees (emp_id, emp_name, salary, dept_id) VALUES (110, 'Jatin Malhotra', 52500, 30);

COMMIT;
