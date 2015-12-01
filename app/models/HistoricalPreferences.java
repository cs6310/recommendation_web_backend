package models;

import java.util.ArrayList;
/**
 * The historical preferences are designed to keep track of the preferences of
 * a user for the courses they want to take for each semester.
 */
public class HistoricalPreferences
{
  private Student student;
  private Semester semester;
  private ArrayList<Course> courses;

  /**
   * Constructor
   * this Constructor takes in nothing and is void
   */
  HistoricalPreferences() {

  }

  /**
   * @return the ArrayList of courses for the HistoricalPreferences ordered in
   * ascending order of preference
   */
  public ArrayList<Course> get_courses() {
	  return courses;
  }

  /**
   * @return the semester for the HistoricalPreferences
   */
  public Semester get_semester() {
	  return semester;
  }

  /**
   * @return the student for the HistoricalPreferences
   */
  public Student get_student() {
	  return student;
  }
}
