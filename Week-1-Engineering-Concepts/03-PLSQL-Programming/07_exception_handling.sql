--------------------------------------------------------------------------------
-- 07_exception_handling.sql
-- Demonstrates a predefined exception (NO_DATA_FOUND), a user-defined
-- exception (e_invalid_salary), and a generic WHEN OTHERS handler.
-- Run 01_schema_and_seed.sql first.
--------------------------------------------------------------------------------
SET SERVEROUTPUT ON

----------------------------------------------------------------------
-- Block 1: predefined exception NO_DATA_FOUND
-- Looks up an emp_id that does not exist in the EMPLOYEES table.
----------------------------------------------------------------------
DECLARE
    v_emp_name employees.emp_name%TYPE;
BEGIN
    SELECT emp_name
    INTO   v_emp_name
    FROM   employees
    WHERE  emp_id = 9999;   -- does not exist

    DBMS_OUTPUT.PUT_LINE('Employee found: ' || v_emp_name);

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('NO_DATA_FOUND caught: no employee with emp_id = 9999.');
END;
/

----------------------------------------------------------------------
-- Block 2: user-defined exception e_invalid_salary
-- Manually validates a salary value and raises a custom exception
-- when the value is invalid.
----------------------------------------------------------------------
DECLARE
    e_invalid_salary EXCEPTION;
    v_new_salary     NUMBER := -500;   -- deliberately invalid
BEGIN
    IF v_new_salary <= 0 THEN
        RAISE e_invalid_salary;
    END IF;

    DBMS_OUTPUT.PUT_LINE('Salary accepted: ' || v_new_salary);

EXCEPTION
    WHEN e_invalid_salary THEN
        DBMS_OUTPUT.PUT_LINE('e_invalid_salary caught: ' || v_new_salary ||
                              ' is not a valid salary (must be positive).');
END;
/

----------------------------------------------------------------------
-- Block 3: generic WHEN OTHERS handler
-- Forces a runtime error (division by zero) and reports SQLCODE/SQLERRM.
----------------------------------------------------------------------
DECLARE
    v_result NUMBER;
    v_divisor NUMBER := 0;
BEGIN
    v_result := 100 / v_divisor;   -- raises ZERO_DIVIDE

    DBMS_OUTPUT.PUT_LINE('Result: ' || v_result);

EXCEPTION
    WHEN ZERO_DIVIDE THEN
        DBMS_OUTPUT.PUT_LINE('ZERO_DIVIDE caught explicitly.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Unexpected error. SQLCODE=' || SQLCODE ||
                              ', SQLERRM=' || SQLERRM);
END;
/
