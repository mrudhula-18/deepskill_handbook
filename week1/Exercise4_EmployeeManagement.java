class Employee{
    int employeeId; String name; String position; double salary;
    Employee(int employeeId,String name,String position,double salary){
        this.employeeId=employeeId; this.name=name; this.position=position; this.salary=salary;
    }
}
public class Exercise4_EmployeeManagement{
    static Employee[] employees=new Employee[100];
    static int count=0;
}