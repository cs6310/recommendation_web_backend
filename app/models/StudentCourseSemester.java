package models;

public class StudentCourseSemester
{
  private Student student;
  private CourseSemester course_semester;

  StudentCourseSemester(Student student, CourseSemester courseSemester) {
    this.student = student;
    this.course_semester = courseSemester;
  }
  
  public Student get_student() {
	  return student;
  }
  
  public CourseSemester get_course_semester() {
	  return course_semester;
  } 
  
}
