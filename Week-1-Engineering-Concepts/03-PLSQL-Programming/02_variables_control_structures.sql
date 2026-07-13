--------------------------------------------------------------------------------
-- 02_variables_control_structures.sql
-- Demonstrates variable declarations (including %TYPE), IF/ELSIF/ELSE,
-- CASE, a numeric FOR loop and a WHILE loop.
-- Run 01_schema_and_seed.sql first.
--------------------------------------------------------------------------------
SET SERVEROUTPUT ON

DECLARE
    -- Plain scalar variable declarations
    v_emp_id        NUMBER := 102;
    v_salary_band   VARCHAR2(20);
    v_dept_desc     VARCHAR2(100);
    v_counter       NUMBER := 1;

    -- %TYPE anchors the variable's datatype to the column's datatype
    v_emp_name      employees.emp_name%TYPE;
    v_salary        employees.salary%TYPE;
    v_dept_id       employees.dept_id%TYPE;
BEGIN
    ----------------------------------------------------------------------
    -- Fetch one employee's data into %TYPE variables
    ----------------------------------------------------------------------
    SELECT emp_name, salary, dept_id
    INTO   v_emp_name, v_salary, v_dept_id
    FROM   employees
    WHERE  emp_id = v_emp_id;

    DBMS_OUTPUT.PUT_LINE('Employee: ' || v_emp_name || ', Salary: ' || v_salary);

    ----------------------------------------------------------------------
    -- IF / ELSIF / ELSE : classify the salary into a band
    ----------------------------------------------------------------------
    IF v_salary < 40000 THEN
        v_salary_band := 'LOW';
    ELSIF v_salary BETWEEN 40000 AND 70000 THEN
        v_salary_band := 'MEDIUM';
    ELSE
        v_salary_band := 'HIGH';
    END IF;

    DBMS_OUTPUT.PUT_LINE('Salary band: ' || v_salary_band);

    ----------------------------------------------------------------------
    -- CASE (searched CASE) : map department id to a description
    ----------------------------------------------------------------------
    v_dept_desc :=
        CASE v_dept_id
            WHEN 10 THEN 'Information Technology department'
            WHEN 20 THEN 'Human Resources department'
            WHEN 30 THEN 'Finance department'
            ELSE 'Unknown department'
        END;

    DBMS_OUTPUT.PUT_LINE('Department description: ' || v_dept_desc);

    ----------------------------------------------------------------------
    -- Numeric FOR loop
    ----------------------------------------------------------------------
    DBMS_OUTPUT.PUT_LINE('--- FOR loop (1 to 5) ---');
    FOR i IN 1..5 LOOP
        DBMS_OUTPUT.PUT_LINE('FOR loop iteration: ' || i);
    END LOOP;

    ----------------------------------------------------------------------
    -- WHILE loop
    ----------------------------------------------------------------------
    DBMS_OUTPUT.PUT_LINE('--- WHILE loop (counts to 5) ---');
    WHILE v_counter <= 5 LOOP
        DBMS_OUTPUT.PUT_LINE('WHILE loop iteration: ' || v_counter);
        v_counter := v_counter + 1;
    END LOOP;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('No employee found with emp_id = ' || v_emp_id);
END;
/
