package models;

import java.util.ArrayList;

public class HistoricalPreferences 
{
  private Student student;
  private Semester semester;
  private ArrayList<Course> courses;

  HistoricalPreferences() {

  }

  public ArrayList<Course> get_courses() {
	  return courses;
  }
  
  public Semester get_semester() {
	  return semester;
  }
  
  public Student get_student() {
	  return student;
  }
}
