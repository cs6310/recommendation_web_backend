package models;

import java.util.Set;

public class Semester
{
  private Set<CourseSemester> course_semesters;

  Semester() {

  }

  public Set<CourseSemester> get_course_semesters() {
	  return course_semesters;
  }
  
  public void add_course_semester(CourseSemester course_semester) {
	  course_semesters.add(course_semester);
  }
}
