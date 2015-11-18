package models;

public class CourseSemester
{
  private boolean offered;
  private Course course;
  private Semester semester;
  private int enrollment_limit;

  CourseSemester() {
   
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
