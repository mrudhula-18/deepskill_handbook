--------------------------------------------------------------------------------
-- 03_cursors.sql
-- Demonstrates an explicit cursor (open/fetch/close in a loop), a %ROWTYPE
-- variable, and an implicit cursor FOR loop.
-- Run 01_schema_and_seed.sql first.
--------------------------------------------------------------------------------
SET SERVEROUTPUT ON

----------------------------------------------------------------------
-- Block 1: explicit cursor, opened/fetched/closed manually,
-- restricted to a single department (IT = 10).
----------------------------------------------------------------------
DECLARE
    CURSOR emp_cur IS
        SELECT emp_name, salary
        FROM   employees
        WHERE  dept_id = 10;

    v_emp_name  employees.emp_name%TYPE;
    v_salary    employees.salary%TYPE;
BEGIN
    DBMS_OUTPUT.PUT_LINE('--- Explicit cursor: employees in IT (dept_id = 10) ---');

    OPEN emp_cur;
    LOOP
        FETCH emp_cur INTO v_emp_name, v_salary;
        EXIT WHEN emp_cur%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('Name: ' || v_emp_name || ', Salary: ' || v_salary);
    END LOOP;
    CLOSE emp_cur;
END;
/

----------------------------------------------------------------------
-- Block 2: %ROWTYPE variable populated via SELECT ... INTO for a
-- single row.
----------------------------------------------------------------------
DECLARE
    emp_rec employees%ROWTYPE;
BEGIN
    DBMS_OUTPUT.PUT_LINE('--- %ROWTYPE example: single employee lookup ---');

    SELECT *
    INTO   emp_rec
    FROM   employees
    WHERE  emp_id = 107;

    DBMS_OUTPUT.PUT_LINE('Emp ID: ' || emp_rec.emp_id ||
                          ', Name: ' || emp_rec.emp_name ||
                          ', Salary: ' || emp_rec.salary ||
                          ', Dept ID: ' || emp_rec.dept_id);
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('No employee found for the given emp_id.');
END;
/

----------------------------------------------------------------------
-- Block 3: cursor FOR LOOP - open/fetch/close is implicit, iterates
-- over every employee.
----------------------------------------------------------------------
BEGIN
    DBMS_OUTPUT.PUT_LINE('--- Cursor FOR loop: all employees ---');

    FOR emp_row IN (SELECT e.emp_name, e.salary, d.dept_name
                     FROM   employees e
                     JOIN   departments d ON d.dept_id = e.dept_id
                     ORDER BY e.emp_id)
    LOOP
        DBMS_OUTPUT.PUT_LINE(emp_row.emp_name || ' | ' ||
                              emp_row.dept_name || ' | ' ||
                              emp_row.salary);
    END LOOP;
END;
/
