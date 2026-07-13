package com.dn5.week1.designpatterns.mvc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MvcTest {

    @Test
    void controllerUpdatesAndReadsModelWithoutViewOrModelKnowingAboutEachOther() {
        StudentModel model = new StudentModel();
        StudentView view = new StudentView();
        StudentController controller = new StudentController(model, view);

        controller.setStudentName("Eve");
        controller.setStudentGrade("A+");

        assertEquals("Eve", controller.getStudentName());
        assertEquals("A+", controller.getStudentGrade());
        assertEquals("Eve", model.getName());
        assertEquals("A+", model.getGrade());
    }

    @Test
    void updateViewDoesNotThrowAndReflectsModelState() {
        StudentModel model = new StudentModel();
        StudentView view = new StudentView();
        StudentController controller = new StudentController(model, view);

        controller.setStudentName("Frank");
        controller.setStudentGrade("B");

        controller.updateView();
    }
}
