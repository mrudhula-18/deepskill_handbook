--------------------------------------------------------------------------------
-- 05_package_employee_pkg.sql
-- Creates the EMPLOYEE_PKG package (spec + body) that groups employee
-- lifecycle operations, then demonstrates calling each member.
-- Run 01_schema_and_seed.sql first.
--------------------------------------------------------------------------------

-- Uncomment for re-runs:
-- DROP PACKAGE employee_pkg;

--------------------------------------------------------------------------------
-- Package specification
--------------------------------------------------------------------------------
CREATE OR REPLACE PACKAGE employee_pkg IS

    -- Inserts a new employee record.
    PROCEDURE hire_employee (
        p_emp_id   IN NUMBER,
        p_emp_name IN VARCHAR2,
        p_salary   IN NUMBER,
        p_dept_id  IN NUMBER
    );

    -- Removes an employee record.
    PROCEDURE terminate_employee (
        p_emp_id IN NUMBER
    );

    -- Returns the average salary for a department (0 if no employees).
    FUNCTION get_average_salary (
        p_dept_id IN NUMBER
    ) RETURN NUMBER;

END employee_pkg;
/

--------------------------------------------------------------------------------
-- Package body
--------------------------------------------------------------------------------
CREATE OR REPLACE PACKAGE BODY employee_pkg IS

    PROCEDURE hire_employee (
        p_emp_id   IN NUMBER,
        p_emp_name IN VARCHAR2,
        p_salary   IN NUMBER,
        p_dept_id  IN NUMBER
    ) IS
    BEGIN
        INSERT INTO employees (emp_id, emp_name, salary, dept_id)
        VALUES (p_emp_id, p_emp_name, p_salary, p_dept_id);

        COMMIT;

    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            RAISE_APPLICATION_ERROR(-20020, 'Employee ' || p_emp_id || ' already exists.');
        WHEN OTHERS THEN
            RAISE;
    END hire_employee;


    PROCEDURE terminate_employee (
        p_emp_id IN NUMBER
    ) IS
    BEGIN
        DELETE FROM employees
        WHERE  emp_id = p_emp_id;

        IF SQL%ROWCOUNT = 0 THEN
            RAISE_APPLICATION_ERROR(-20021, 'No employee found with emp_id = ' || p_emp_id);
        END IF;

        COMMIT;

    EXCEPTION
        WHEN OTHERS THEN
            RAISE;
    END terminate_employee;


    FUNCTION get_average_salary (
        p_dept_id IN NUMBER
    ) RETURN NUMBER IS
        v_avg_salary NUMBER;
    BEGIN
        SELECT NVL(AVG(salary), 0)
        INTO   v_avg_salary
        FROM   employees
        WHERE  dept_id = p_dept_id;

        RETURN v_avg_salary;

    EXCEPTION
        WHEN OTHERS THEN
            RETURN 0;
    END get_average_salary;

END employee_pkg;
/

--------------------------------------------------------------------------------
-- Demo block: invokes each package member.
--------------------------------------------------------------------------------
SET SERVEROUTPUT ON

DECLARE
    v_avg_salary NUMBER;
BEGIN
    -- Hire a new employee into the Finance department (dept_id = 30)
    employee_pkg.hire_employee(
        p_emp_id   => 111,
        p_emp_name => 'Kavita Desai',
        p_salary   => 60000,
        p_dept_id  => 30
    );
    DBMS_OUTPUT.PUT_LINE('Hired employee 111 (Kavita Desai).');

    -- Check the new average salary for Finance
    v_avg_salary := employee_pkg.get_average_salary(30);
    DBMS_OUTPUT.PUT_LINE('Average salary in department 30 (Finance): ' || v_avg_salary);

    -- Terminate the employee we just hired
    employee_pkg.terminate_employee(111);
    DBMS_OUTPUT.PUT_LINE('Terminated employee 111.');
END;
/
