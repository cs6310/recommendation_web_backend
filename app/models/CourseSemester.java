package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	public CourseSemester(Course course, Semester semester) {
		this.course = course;
		this.semester = semester;
	}
	
	public CourseSemester(boolean offered, Course course, Semester semester, int enrollment_limit){
		  this.offered = offered;
		  this.enrollment_limit = enrollment_limit;
		  this.course = course;
		  this.semester = semester;
	}
  
  public boolean get_offered() {
	  return offered;
  }
  
  public Course get_course() {
	  return course;
  }
  
  public Semester get_semester() {
	  return semester;
  }
  
  public int get_enrollmment_limit() {
	  return enrollment_limit;
  }
  
  public void set_offered(boolean offered) {
	  this.offered = offered;
  }
  
  public void set_course(Course course) {
	  this.course = course;
  }
  
  public void set_semester(Semester semester) {
	  this.semester = semester;
  }
  
  public void set_enrollment_limit(int enrollment_limit) {
	  this.enrollment_limit = enrollment_limit;
  }
}
