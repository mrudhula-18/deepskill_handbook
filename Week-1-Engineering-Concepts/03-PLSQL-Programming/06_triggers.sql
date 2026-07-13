--------------------------------------------------------------------------------
-- 06_triggers.sql
-- Creates a SALARY_AUDIT table plus a BEFORE INSERT validation trigger
-- and an AFTER UPDATE audit trigger on EMPLOYEES, then demonstrates both.
-- Run 01_schema_and_seed.sql first.
--------------------------------------------------------------------------------

-- Uncomment for re-runs:
-- DROP TRIGGER trg_validate_salary;
-- DROP TRIGGER trg_salary_audit;
-- DROP TABLE salary_audit PURGE;

--------------------------------------------------------------------------------
-- Audit table: records every change to an employee's salary.
--------------------------------------------------------------------------------
CREATE TABLE salary_audit (
    audit_id    NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    emp_id      NUMBER,
    old_salary  NUMBER,
    new_salary  NUMBER,
    changed_on  DATE DEFAULT SYSDATE
);

--------------------------------------------------------------------------------
-- TRIGGER trg_validate_salary
-- Prevents inserting an employee with a non-positive salary.
--------------------------------------------------------------------------------
CREATE OR REPLACE TRIGGER trg_validate_salary
    BEFORE INSERT ON employees
    FOR EACH ROW
BEGIN
    IF :NEW.salary IS NULL OR :NEW.salary <= 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Salary must be a positive value.');
    END IF;
END trg_validate_salary;
/

--------------------------------------------------------------------------------
-- TRIGGER trg_salary_audit
-- Whenever an employee's salary is updated to a different value, log the
-- old and new salary into salary_audit.
-- NOTE: the WHEN clause uses unprefixed NEW/OLD (no colon).
--------------------------------------------------------------------------------
CREATE OR REPLACE TRIGGER trg_salary_audit
    AFTER UPDATE ON employees
    FOR EACH ROW
    WHEN (NEW.salary != OLD.salary)
BEGIN
    INSERT INTO salary_audit (emp_id, old_salary, new_salary, changed_on)
    VALUES (:OLD.emp_id, :OLD.salary, :NEW.salary, SYSDATE);
END trg_salary_audit;
/

--------------------------------------------------------------------------------
-- Demo block: exercises both triggers.
--------------------------------------------------------------------------------
SET SERVEROUTPUT ON

BEGIN
    -- This UPDATE fires trg_salary_audit because the salary changes.
    UPDATE employees
    SET    salary = salary + 5000
    WHERE  emp_id = 102;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Salary updated for emp_id 102; audit row inserted.');
END;
/

-- Uncomment to see trg_validate_salary reject an invalid insert:
-- INSERT INTO employees (emp_id, emp_name, salary, dept_id) VALUES (999, 'Bad Salary', -100, 10);

-- Inspect the audit trail:
-- SELECT * FROM salary_audit ORDER BY changed_on;
