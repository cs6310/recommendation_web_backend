package models;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Student")
public class Student extends Person {

  private String permission_type;
  /**
   * The list of individual courses the student wants to take.
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "PERSON_ID_REF", referencedColumnName = "PERSON_ID")
  private List<Course> courses;


  // This returns the permission level for the student
  // This will be used in determining what people have access to what
  // actions inside the system.
  public String get_permission() {
	  return permission_type;
  }

  @ManyToMany(cascade = CascadeType.MERGE , fetch = FetchType.EAGER)
  @JoinColumn(name = "PERSON_ID_REF", referencedColumnName = "PERSON_ID")
  private List<CourseSemester> course_semester;


  public Student() {
	  super(0);
  }
  /**
   * Constructor
   * @param id (required) The student id.
   * @param courses (required) The list of courses wanted by the student.
   */
  public Student(int id, List<Course> courses) {
	  super(id);

      this.courses = courses;
  }

  /**
   * @return the list of courses the student wants to take.
   */
  public List<Course> getCourses() {
      return courses;
  }

  /**
   * @param requestedCourses (required) The ids of the courses that a student wants to take
   */
  public void setCourses(List<Course> requestedCourses) {
	  courses.clear();
	  for (Course course : requestedCourses) {
		  courses.add(course);
	  }
  }

  /**
   * @param courseId (required) the id used to look up the couse
   * @return true or false
   */
  public boolean findStudentCourseById(int courseId) {
	  for (Course course : courses) {
		  if (course.getId() == courseId) {
			  return true;
		  }
	  }
	  return false;
  }

  /**
   * @param courseSemesters (required) the courseSemesters to be set for the Student
   */
  public void setCourseSemesters(List<CourseSemester> courseSemesters) {
	  course_semester = new ArrayList<CourseSemester>(courseSemesters);
  }

  /**
   * @return the CourseSemesters associated with this student as a List (ArrayList)
   */
  public List<CourseSemester> getCourseSemesters() {
	  return course_semester;
  }

  /*
   *  (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
      return super.get_UID() + "---" + courses.toString();
  }
}
