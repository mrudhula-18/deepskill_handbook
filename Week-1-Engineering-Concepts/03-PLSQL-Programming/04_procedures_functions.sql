--------------------------------------------------------------------------------
-- 04_procedures_functions.sql
-- Creates a stored procedure (give_raise) and a stored function
-- (get_department_total), then demonstrates calling both.
-- Run 01_schema_and_seed.sql first.
--------------------------------------------------------------------------------

-- Uncomment for re-runs:
-- DROP PROCEDURE give_raise;
-- DROP FUNCTION get_department_total;

--------------------------------------------------------------------------------
-- PROCEDURE give_raise
-- Increases an employee's salary by p_percent and returns the new salary
-- through the OUT parameter. Raises an application error if the employee
-- does not exist.
--------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE give_raise (
    p_emp_id     IN  NUMBER,
    p_percent    IN  NUMBER,
    p_new_salary OUT NUMBER
) IS
    v_current_salary employees.salary%TYPE;
BEGIN
    -- Basic validation of the input percentage
    IF p_percent IS NULL OR p_percent <= 0 THEN
        RAISE_APPLICATION_ERROR(-20010, 'Raise percent must be a positive number.');
    END IF;

    SELECT salary
    INTO   v_current_salary
    FROM   employees
    WHERE  emp_id = p_emp_id;

    p_new_salary := v_current_salary + (v_current_salary * p_percent / 100);

    UPDATE employees
    SET    salary = p_new_salary
    WHERE  emp_id = p_emp_id;

    COMMIT;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20011, 'No employee found with emp_id = ' || p_emp_id);
    WHEN OTHERS THEN
        RAISE;
END give_raise;
/

--------------------------------------------------------------------------------
-- FUNCTION get_department_total
-- Returns the total (SUM) of salaries for a given department. Returns 0
-- if the department has no employees (NVL-wrapped).
--------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION get_department_total (
    p_dept_id IN NUMBER
) RETURN NUMBER IS
    v_total NUMBER;
BEGIN
    SELECT NVL(SUM(salary), 0)
    INTO   v_total
    FROM   employees
    WHERE  dept_id = p_dept_id;

    RETURN v_total;

EXCEPTION
    WHEN OTHERS THEN
        RETURN 0;
END get_department_total;
/

--------------------------------------------------------------------------------
-- Demo block: calls the procedure and the function and prints results.
--------------------------------------------------------------------------------
SET SERVEROUTPUT ON

DECLARE
    v_new_salary NUMBER;
    v_dept_total NUMBER;
BEGIN
    -- Give employee 101 a 10% raise
    give_raise(p_emp_id => 101, p_percent => 10, p_new_salary => v_new_salary);
    DBMS_OUTPUT.PUT_LINE('New salary for emp_id 101 after 10% raise: ' || v_new_salary);

    -- Get the total salary bill for the IT department (dept_id = 10)
    v_dept_total := get_department_total(10);
    DBMS_OUTPUT.PUT_LINE('Total salary for department 10 (IT): ' || v_dept_total);
END;
/
