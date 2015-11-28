package models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Course")
public class Course
{
  //private Set<CourseSemester> course_semesters;
  private String course_ta;
  @Id
  private int id;

  public Course(int id) {
	  this.id = id;
	  
  }

  /*
  public Set<CourseSemester> get_course_semesters() {
	  return course_semesters;
  }
  
  public void add_course_semester(CourseSemester course_semester) {
	  course_semesters.add(course_semester);
  }
  */
  
  public void set_course_ta(String TA) {
	  this.course_ta = TA;
  }
  
  public String get_course_ta() {
	  return course_ta;
  }
  public int getId() {
	  return id;
  }
}
