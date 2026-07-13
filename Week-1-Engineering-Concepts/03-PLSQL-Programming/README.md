# 03 - PL/SQL Programming

This module is a set of Oracle PL/SQL / SQL scripts. It is **not** a
Maven/Java module - there is no `pom.xml`, no source tree, nothing to
compile. Every file here is a plain `.sql` script written for Oracle
Database syntax (SQL*Plus / SQL Developer dialect), using `/` block
terminators after each PL/SQL unit.

## Run order

Run the scripts in numeric order; each one builds on the schema and data
created by `01_schema_and_seed.sql`.

| # | File | Description |
|---|------|-------------|
| 1 | `01_schema_and_seed.sql` | Creates `departments` and `employees` tables and seeds them with 3 departments and 10 employees. |
| 2 | `02_variables_control_structures.sql` | Anonymous block covering variable declarations (including `%TYPE`), `IF/ELSIF/ELSE`, `CASE`, a numeric `FOR` loop, and a `WHILE` loop. |
| 3 | `03_cursors.sql` | Explicit cursor (open/fetch/close), a `%ROWTYPE` lookup, and an implicit cursor `FOR` loop. |
| 4 | `04_procedures_functions.sql` | `give_raise` procedure (OUT parameter, validation) and `get_department_total` function, plus a demo block calling both. |
| 5 | `05_package_employee_pkg.sql` | `employee_pkg` package (spec + body) with `hire_employee`, `terminate_employee`, and `get_average_salary`, plus a demo block. |
| 6 | `06_triggers.sql` | `salary_audit` table, a `BEFORE INSERT` validation trigger, and an `AFTER UPDATE` audit trigger on `employees`. |
| 7 | `07_exception_handling.sql` | Predefined exception (`NO_DATA_FOUND`), user-defined exception (`e_invalid_salary`), and a generic `WHEN OTHERS` handler. |

## How to run

### SQL*Plus

```
sqlplus user/pass@db
@01_schema_and_seed.sql
@02_variables_control_structures.sql
@03_cursors.sql
@04_procedures_functions.sql
@05_package_employee_pkg.sql
@06_triggers.sql
@07_exception_handling.sql
```

### SQL Developer

Open each script in order and run it as a script (F5), rather than
executing statement-by-statement, so that the `/` block terminators are
honored correctly.

## Seeing DBMS_OUTPUT

Every script that uses `DBMS_OUTPUT.PUT_LINE` already includes
`SET SERVEROUTPUT ON` at the top, which is required in SQL*Plus for
output to be displayed.

In SQL Developer, `SET SERVEROUTPUT ON` alone is not enough - you must
also open the **DBMS Output** panel (View > DBMS Output) and click the
green "+" to enable/start output capture for the connection before
running a script, otherwise `PUT_LINE` output will not be shown.
