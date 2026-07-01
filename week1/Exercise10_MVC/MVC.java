
class Student {
    String name;
    int id;
    String grade;

    Student(String n, int i, String g) {
        name = n; id = i; grade = g;
    }
}

class StudentView {
    void display(Student s) {
        System.out.println(s.name + " " + s.id + " " + s.grade);
    }
}

class StudentController {
    Student model;
    StudentView view;

    StudentController(Student m, StudentView v) {
        model = m;
        view = v;
    }

    void updateGrade(String g) {
        model.grade = g;
    }

    void show() { view.display(model); }
}

public class TestMVC {
    public static void main(String[] args) {
        Student s = new Student("John", 1, "A");
        StudentView v = new StudentView();
        StudentController c = new StudentController(s, v);

        c.show();
        c.updateGrade("A+");
        c.show();
    }
}
