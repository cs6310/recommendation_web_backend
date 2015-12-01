package models;
//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

import views.forms.StudentRequest;

// This is the interface for our schedulers, this code is being re-used from project 1
public interface Scheduler {
    public void calculateSchedule();

    public double getObjectiveValue();

    public Vector<String> getCoursesForStudentSemester(String student, String semester);

    public Vector<String> getStudentsForCourseSemester(String course, String semester);
}
