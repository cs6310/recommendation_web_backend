package models;
//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

import views.forms.StudentRequest;

public interface Scheduler {
    public void calculateSchedule(String dataFolder, StudentRequest request);

    public double getObjectiveValue();

    public Vector<String> getCoursesForStudentSemester(String student, String semester);

    public Vector<String> getStudentsForCourseSemester(String course, String semester);
}
