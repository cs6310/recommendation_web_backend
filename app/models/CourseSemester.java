package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This course is intended to be the join between a Course and a Semester.
 * In English this would mean something like "CS6310 being taugh in the Fall of 2015"
 */
@Entity
@Table(name="CourseSemester")
public class CourseSemester
{
	@Id
	@Column(name="COURSE_SEMESTER_ID")
	private int id;
	private boolean offered;
	@ManyToOne
	@JoinColumn(name = "course")
	private Course course;
	@ManyToOne
	@JoinColumn(name = "semester")
	private Semester semester;
	private int enrollment_limit;

	public CourseSemester() {

	}

	/**
	 * Constructor
	 * @param course (requred) the course
	 * @param semester (requred) the semester
	 */
	public CourseSemester(Course course, Semester semester) {
		this.course = course;
		this.semester = semester;
	}

	/**
	 * Constructor
	 * @param course (requred) the course
	 * @param semester (requred) the semester
	 * @param offered (requred) whether or not the course is offered this semester
	 * @param enrollment_limit (requred) the enrollment_limit for this course for this semester
	 */
	public CourseSemester(boolean offered, Course course, Semester semester, int enrollment_limit){
		  this.offered = offered;
		  this.enrollment_limit = enrollment_limit;
		  this.course = course;
		  this.semester = semester;
	}

	/**
	 * @return a boolean on whether of not the course was offered
	 */
  public boolean get_offered() {
	  return offered;
  }

	/**
	 * @return the Course
	 */
  public Course get_course() {
	  return course;
  }

	/**
	 * @return the Semester
	 */
  public Semester get_semester() {
	  return semester;
  }

	/**
	 * @return the enrollment limit for this course semester
	 */
  public int get_enrollmment_limit() {
	  return enrollment_limit;
  }

	/**
	 * @param a boolean on whether of not the course was offered
	 */
  public void set_offered(boolean offered) {
	  this.offered = offered;
  }

	/**
	 * @param the Course
	 */
  public void set_course(Course course) {
	  this.course = course;
  }

	/**
	 * @param the Semester
	 */
  public void set_semester(Semester semester) {
	  this.semester = semester;
  }

	/**
	 * @param the enrollment limit for this course semester
	 */
  public void set_enrollment_limit(int enrollment_limit) {
	  this.enrollment_limit = enrollment_limit;
  }
}
