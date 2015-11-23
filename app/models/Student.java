package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student extends Person
{
  private String permission_type;
  /**
   * The list of individual courses the student wants to take.
   */
  private List<Integer> courses;
  private int studentId;

  Student(int id) {
    super(id);
  }

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

      courses = new ArrayList<Integer>();

      while (read.hasNext()) {
          int course = (int) read.nextFloat();

          courses.add(course);
      }

      studentId = id;
  } 

  /**
   * @return the list of courses the student wants to take.
   */
  public List<Integer> getCourses() {
      return courses;
  }

  /**
   * @return the student id
   */
  public int getStudentId() {
      return studentId;
  }

  /*
   *  (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
      return studentId + "---" + courses.toString();
  }
}
