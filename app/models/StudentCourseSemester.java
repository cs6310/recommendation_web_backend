package models;

// This class servers as a join controller between a student and a CourseSemester
public class StudentCourseSemester
{
  // The student this is connected to
  private Student student;
  // The course semester this is connected to
  private CourseSemester course_semester;

  // Initializer takes in a Student and a CourseSemester
  StudentCourseSemester(Student student, CourseSemester courseSemester) {
    this.student = student;
    this.course_semester = courseSemester;
  }

  // returns the Student
  public Student get_student() {
	  return student;
  }

  // returns the CourseSemester
  public CourseSemester get_course_semester() {
	  return course_semester;
  }

}
