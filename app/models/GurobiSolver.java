package models;

import java.util.ArrayList;
import java.util.HashSet;

public class GurobiSolver
{
	private ArrayList<Course> offered_courses;
	private ArrayList<Professor> available_professors;
	private ArrayList<Course> required_courses;
	private HashSet<Course> course_desirability;
	private ArrayList<TA> ta_pool;
	
	private static final int NUMBER_OF_COURSES = 18;
	private static final int NUMBER_OF_SEMESTERS = 12;
	private static final int MAX_CLASSES_PER_SEMESTER = 2;
	
  GurobiSolver() {

  }
  
  public void calculate_schedule_for_students() {
	  
  }
  
  public ArrayList<TA> get_ta_for_course(Course course) {

	  return null;
  }
  
  public Professor get_professor_for_course(Course course) {
	  return null;
  }
  
  public ArrayList<Student> get_students_for_course(Course course) {
	  return null;
  }
  
  public ArrayList<Course> get_courses_offered() {
	  return offered_courses;
  }
  
  public void set_offered_courses(ArrayList<Course> courses) {
	  offered_courses = courses;
  }
  
  public void set_available_professors(ArrayList<Professor> professors) {
	  available_professors = professors;
  }
  
  public void set_required_Coureses(ArrayList<Course> courses) {
	  required_courses = courses;
  }
  
  @SuppressWarnings("unchecked")
  public void set_courses_desirability(HashSet<Course> courses) {
	  course_desirability = (HashSet<Course>)courses.clone();
  }
  
  public void set_ta_pool(ArrayList<TA> tas) {
	  ta_pool = tas;
  }
  
	
}