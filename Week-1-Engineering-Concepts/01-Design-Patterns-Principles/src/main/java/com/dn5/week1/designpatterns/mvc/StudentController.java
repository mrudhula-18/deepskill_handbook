package com.dn5.week1.designpatterns.mvc;

/** Wires the model and the view together without either knowing about the other. */
public class StudentController {

    private final StudentModel model;
    private final StudentView view;

    public StudentController(StudentModel model, StudentView view) {
        this.model = model;
        this.view = view;
    }

    public void setStudentName(String name) {
        model.setName(name);
    }

    public void setStudentGrade(String grade) {
        model.setGrade(grade);
    }

    public String getStudentName() {
        return model.getName();
    }

    public String getStudentGrade() {
        return model.getGrade();
    }

    public void updateView() {
        view.printStudentDetails(model.getName(), model.getGrade());
    }
}
