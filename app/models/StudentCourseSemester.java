package models;

// This class servers as a join controller between a student and a CourseSemester
public class StudentCourseSemester
{
  // The student this is connected to
  private Student student;
  // The course semester this is connected to
  private CourseSemester course_semester;

  /**
   * Constructor
   * @param student (required) the student in the join Class
   * @param courseSemester (required) the CourseSemester in the join class
   */
  StudentCourseSemester(Student student, CourseSemester courseSemester) {
    this.student = student;
    this.course_semester = courseSemester;
  }

  /**
   * @return the Student of the join
   */
  public Student get_student() {
	  return student;
  }

  /**
   * @return the CourseSemester of the join
   */
  public CourseSemester get_course_semester() {
	  return course_semester;
  }

}
