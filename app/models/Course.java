package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

  /**
   * @param the course that is a prerequisite for this
   */
  public void setParentClass(Course course) {
	  parent = course;
  }

  /**
   * @param the courses that this course is a prerequisite for
   */
  public void setChildClasses(List<Course> courses) {
	  childClasses = courses;
  }

  /**
   * @param the course that this course is a prerequisite for
   */
  public void addChildClass(Course course) {
	  if (childClasses == null) {
		  childClasses = new ArrayList<Course>();
	  }
	  childClasses.add(course);
  }

  /**
   * @return the course that is a prerequisite for this
   */
  public Course getParentClass() {
	  return parent;
  }

  /**
   * @return the courses that this course is a prerequisite for
   */
  public List<Course> getChildCourses() {
	  return childClasses;
  }

  /**
   * @return the string for the name of the course
   */
  public String getCourseName() {
	  return courseName;
  }

  /**
   * @return the id for the course for identification
   */
  public int getId() {
	  return id;
  }

  @Override
  public int hashCode() {
      return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
          // if deriving: appendSuper(super.hashCode()).
          append(id).
          toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
	  if (obj == null) {
		  return false;
	  }
     if (!(obj instanceof Person))
          return false;
      if (obj == this)
          return true;

      Person rhs = (Person) obj;
      return id == rhs.get_UID();
  }
}
