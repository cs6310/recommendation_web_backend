package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Course")
public class Course {
  @Id
  private int id;
  private String courseName;
  @ManyToOne
  private Course parent; // aka Pre-requisite
  @OneToMany(mappedBy="parent")
  private List<Course> childClasses; // aka the classes opened after taking the pre-req.

  // Default Constructor needed for hibernate
  public Course() {  }
  public Course(int id, String courseName) {
	  this.id = id;
	  this.courseName = courseName;
	  this.childClasses = null;
	  this.parent = null;
  }
  
  public String toString() {
	  String childClassesStr = "";
	  if (childClasses == null) {
		  childClassesStr = "no child courses";
	  } else {
		  for (Course course: childClasses) {
			  childClassesStr += " " + course.id;
		  }
	  }
	  return "id: " + id
			  + " courseName: " + courseName
			  + " childCourses: " + childClassesStr
			  + " parent: " + ((parent == null)? "no parent": ""+parent.id);
  }
  
  public void setParentClass(Course course) {
	  parent = course;
  }
  
  public void setChildClasses(List<Course> courses) {
	  childClasses = courses;
  }
  
  public void addChildClass(Course course) {
	  if (childClasses == null) {
		  childClasses = new ArrayList<Course>();
	  }
	  childClasses.add(course);
  }
  
  public Course getParentClass() {
	  return parent;
  }

  public List<Course> getChildCourses() {
	  return childClasses;
  }
  
  public String getCourseName() {
	  return courseName;
  }

  /*
  public Set<CourseSemester> get_course_semesters() {
	  return course_semesters;
  }
  
  public void add_course_semester(CourseSemester course_semester) {
	  course_semesters.add(course_semester);
  }
  */
  /*
  public void set_course_ta(List<TA> Ta) {
	  this.courseTAs = ta;
  }
  
  public List<TA> get_course_ta() {
	  return courseTAs;
  }
  */
  public int getId() {
	  return id;
  }
}
