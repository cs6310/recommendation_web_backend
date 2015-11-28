package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//@Entity
//@Table(name="Student")
//@DiscriminatorValue("S") // S for student
@Entity
@Table(name="Student")
public class Student extends Person
{
	//@Id
	//private int id;
  private String permission_type;
  /**
   * The list of individual courses the student wants to take.
   */
  //@OneToMany(targetEntity=Course.class, mappedBy="Student", fetch=FetchType.EAGER)
  @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
  @JoinColumn(name = "PERSON_ID_REF", referencedColumnName = "PERSON_ID")
  private List<Course> courses;

  Student(int id) {
    super(id);
  }

  // This returns the permission level for the student
  // This will be used in determining what people have access to what
  // actions inside the system.
  public String get_permission() {
	  return permission_type;
  }


  //*****************
  //* Project 1 Code
  //*****************
  /**
   * Constructor
   * @param id (required) The student id.
   * @param line (required) The string containing the list of courses.
   */
  public Student(int id, String line) {
	  super(id);
      Scanner read = new Scanner(line);

      courses = new ArrayList<Course>();

      while (read.hasNext()) {
          int courseId = (int) read.nextFloat();

          courses.add(new Course(courseId));
      }
  } 

  /**
   * @return the list of courses the student wants to take.
   */
  public List<Course> getCourses() {
      return courses;
  }

  /**
   * @return nothing
   * @param requestedCourses (required) The ids of the courses that a student wants to take
   */
  public void setCourses(List<Course> requestedCourses) {
	  courses.clear();
	  for (Course course : requestedCourses) {
		  courses.add(course);
	  }
  }
  
  public boolean findStudentCourseById(int courseId) {
	  for (Course course : courses) {
		  if (course.getId() == courseId) {
			  return true;
		  }
	  }
	  return false;
  }

  /*
   *  (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
      return super.get_UID() + "---" + courses.toString();
  }
}
