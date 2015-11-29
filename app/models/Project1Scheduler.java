package models;
//~--- non-JDK imports --------------------------------------------------------

import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

//~--- JDK imports ------------------------------------------------------------

import java.util.*;

import play.Logger;

import services.SemesterService;
import services.ServicesInstances;
import services.StudentService;

public class Project1Scheduler implements Scheduler {
  private static final int        NUMBER_OF_COURSES        = 18;
  //private static final int        NUMBER_OF_SEMESTERS      = 12;
  //private static final int        MAX_CLASSES_PER_SEMESTER = 2;
  private static HashSet<Integer> MASTER_COURSE_CATALOG    = new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7,
                                                                 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18));
  private static List<Integer> SUMMER_COURSES = new ArrayList<Integer>(Arrays.asList(2, 3, 4, 6, 8, 9, 12, 13));
  private static List<Integer> FALL_COURSES   = new ArrayList<Integer>(Arrays.asList(2, 3, 4, 6, 8, 9, 12, 13, 1, 7,
                                                    11, 15, 17));
  private static List<Integer> SPRING_COURSES = new ArrayList<Integer>(Arrays.asList(2, 3, 4, 6, 8, 9, 12, 13, 5, 10,
                                                    14, 16, 18));

  private static boolean DEBUG =false;    // Defaults to false

  /**
   * This method determines the list of classes available depending on the semester.
   * @param semesterId is the semester id
   * @return returns a list of classes represented by their class ids.
   */
  public List<Integer> getClassesForSemester(int semesterId) {
      if ((semesterId == 1) || (semesterId == 4) || (semesterId == 7) || (semesterId == 10)) {
          return FALL_COURSES;
      } else if ((semesterId == 2) || (semesterId == 5) || (semesterId == 8) || (semesterId == 11)) {
          return SPRING_COURSES;
      } else {
          return SUMMER_COURSES;
      }
  }

  /**
   * printSchedule is a debug function that just prints a human friendly
   * version of the schedule for each student.
   * @param students
   * @param GRBVars
   * @throws GRBException
   */
  private void printSchedule(List<Student> students, HashMap<String, GRBVar> GRBVars) throws GRBException {
      for (Student student : students) {
          System.out.printf("Student %d\n", student.get_UID());

          for (int semesterId = 1; semesterId <= helpers.Constants.NUMBER_OF_SEMESTERS; semesterId++) {
              System.out.printf("Semester %d)\t", semesterId);

              for (Course course : student.getCourses()) {
                  String varId = createUniqueVariableId(student.get_UID(), course.getId(), semesterId);

                  if (GRBVars.get(varId).get(GRB.DoubleAttr.X) == 1) {
                      System.out.printf(" (Course %s) ", course.getId());
                  }
              }

              System.out.println("");
          }
      }
  }

  /*
   *  (non-Javadoc)
   * @see Scheduler#calculateSchedule(java.lang.String)
   */
  public void calculateSchedule() {

	  // Get the students from the database
	  StudentService studentService = (StudentService) ServicesInstances.STUDENT_SERVICE.getService();
	  List<Student> students = new ArrayList<Student>(studentService.getAllStudents());
	  /*
		for (Student student: students) {
			Logger.debug(student.toString() + "/" + student.getCourses().size());
		}
		*/

      GRBEnv env;

      try {
          env = new GRBEnv("mip1.log");

          GRBModel                model   = new GRBModel(env);
          HashMap<String, GRBVar> GRBVars = new HashMap<String, GRBVar>();

          // Create the variables which represent all the combinations of student id, course id, and semester id.
          for (Student student : students) {
              for (int courseId = 1; courseId <= NUMBER_OF_COURSES; courseId++) {
                  for (int semesterId = 1; semesterId <= helpers.Constants.NUMBER_OF_SEMESTERS; semesterId++) {
                      String varId = createUniqueVariableId(student.get_UID(), courseId, semesterId);

                      GRBVars.put(varId, model.addVar(0, 1, 0.0, GRB.BINARY, varId));
                  }
              }
          }

          // Create the variable to represent the class size for each class.
          GRBVar X = model.addVar(0, students.size(), 0.0, GRB.INTEGER, "X1");

          // Integrate new variables
          model.update();

          // Set the objective as minimizing X.
          GRBLinExpr expr = new GRBLinExpr();

          expr.addTerm(1, X);
          model.setObjective(expr, GRB.MINIMIZE);

          // Constraints.
          // Page 5. Equation 1.
          // Max number of classes a student can take a semester is MAX_CLASSES_PER_SEMESTER
          for (int semesterId = 1; semesterId <= helpers.Constants.NUMBER_OF_SEMESTERS; semesterId++) {
              for (Student student : students) {
                  expr = new GRBLinExpr();

                  for (int courseId = 1; courseId <= NUMBER_OF_COURSES; courseId++) {

                      // Verify the student wants to take the course
                      if (!student.findStudentCourseById(courseId)) {
                          continue;
                      }

                      // Verify the class is available for the semester.
                      if (getClassesForSemester(semesterId).contains(courseId)) {
                          String varId = createUniqueVariableId(student.get_UID(), courseId, semesterId);

                          expr.addTerm(1, GRBVars.get(varId));
                      }
                  }

                  // Add constraint.
                  model.addConstr(expr, GRB.LESS_EQUAL, helpers.Constants.MAX_CLASSES_PER_SEMESTER, "" + semesterId + "");
              }
          }

          // Page 6. Equation 1.
          // Max number of students in a class for any given semester is less than or equal to X.
          for (int courseId = 1; courseId <= NUMBER_OF_COURSES; courseId++) {
              for (int semesterId = 1; semesterId <= helpers.Constants.NUMBER_OF_SEMESTERS; semesterId++) {

                  // Verify the class is available for the semester.
                  if (getClassesForSemester(semesterId).contains(courseId)) {
                      expr = new GRBLinExpr();

                      for (Student student : students) {

                          // Verify the student wants to take the course
                          if (!student.findStudentCourseById(courseId)) {
                              continue;
                          }

                          String varId = createUniqueVariableId(student.get_UID(), courseId, semesterId);

                          expr.addTerm(1, GRBVars.get(varId));
                      }

                      // Add constraint
                      model.addConstr(expr, GRB.LESS_EQUAL, X, "X");    // TODO
                  }
              }
          }

          // Page 6. Equation 1.
          // A student can only take a class once.
          for (Student student : students) {

              // Make a master list of all possible courses.
              HashSet<Integer> courseCatalog = new HashSet<Integer>(MASTER_COURSE_CATALOG);

              // First look at the courses the student wants to take.
              for (Course course : student.getCourses()) {
                  expr = new GRBLinExpr();

                  for (int semesterId = 1; semesterId <= helpers.Constants.NUMBER_OF_SEMESTERS; semesterId++) {

                      // Verify the class is available for that semester.
                      if (getClassesForSemester(semesterId).contains(course.getId())) {
                          String varId = createUniqueVariableId(student.get_UID(), course.getId(), semesterId);

                          expr.addTerm(1, GRBVars.get(varId));

                          // Remove the course from the list of all possible classes.
                          courseCatalog.remove(course.getId());
                      }
                  }

                  // Add constraint
                  model.addConstr(
                      expr, GRB.EQUAL, 1,
                      new StringBuffer().append(student.get_UID()).append('-').append(course.getId()).toString());
              }

              // For courses the student will NOT take from the remaining courseCatalog.
              for (Integer courseId : courseCatalog) {
                  expr = new GRBLinExpr();

                  for (int semesterId = 1; semesterId <= helpers.Constants.NUMBER_OF_SEMESTERS; semesterId++) {

                      // Verify the class is available for that semester.
                      if (getClassesForSemester(semesterId).contains(courseId)) {
                          String varId = createUniqueVariableId(student.get_UID(), courseId, semesterId);

                          expr.addTerm(0, GRBVars.get(varId));
                      }
                  }

                  // Add constraint
                  model.addConstr(
                      expr, GRB.EQUAL, 0,
                      new StringBuffer().append(student.get_UID()).append('-').append(courseId).toString());
              }
          }

          // Page 6. Equation 4.
          // Given a course has a pre-req, the pre-req must be taken before the course.
          for (Student student : students) {

              // Expression for the course itself.
              GRBLinExpr exprClass = new GRBLinExpr();

              // Expression for the pre-req course.
              GRBLinExpr exprClassPreReq = new GRBLinExpr();

              for (Course course : student.getCourses()) {

                  // Check if courseId has a prereq
                  if (course.getParentClass() == null) {

                      // if not a class that has a pre-req, skip.
                      continue;
                  }

                  for (int semesterId = 1; semesterId <= helpers.Constants.NUMBER_OF_SEMESTERS; semesterId++) {

                      // Dont bother adding a term if the class doesnt exist for that semester really.
                      if (!getClassesForSemester(semesterId).contains(course.getId())) {
                          continue;
                      }

                      String varIdClass = createUniqueVariableId(student.get_UID(), course.getId(), semesterId);

                      exprClass.addTerm(semesterId, GRBVars.get(varIdClass));

                      String varIdClassPreReq = createUniqueVariableId(student.get_UID(),
                                                    course.getParentClass().getId(), semesterId);

                      exprClassPreReq.addTerm(semesterId, GRBVars.get(varIdClassPreReq));

                      // Debug to show the setup of vars between class and pre-req for a given student.
                      if (DEBUG) {
                          System.out.printf("Student %d Class Key %s Pre-Req Class Key %s \n",
                                            student.get_UID(), varIdClass, varIdClassPreReq);
                      }
                  }
              }

              // Add constraint.
              model.addConstr(exprClassPreReq, GRB.LESS_EQUAL, exprClass, student.get_UID() + "-courses-prereq");
              
          }

          // All constraints added.
          // Optimize the model
          model.optimize();

          double objectiveValue = model.get(GRB.DoubleAttr.ObjVal);

          System.out.printf("X = %f\n", objectiveValue);
          
          GRBVARS_GLOBAL= GRBVars;

          if (DEBUG) {

              // Show schedule if debug flag is true.
              printSchedule(students, GRBVars);
          }
      } catch (GRBException e) {
          System.out.println(e.getMessage());
      }
  }

  /**
   * createUniqueVariableId creates a unique string that represents a unique combination of
   * student id, course id, and semester id. Useful looking for a particular GRBVar to re-use.
   * @param studentId
   * @param courseId
   * @param semesterId
   * @return a unique string representing the studentid, courseid, and semesterid.
   */
  private String createUniqueVariableId(int studentId, int courseId, int semesterId) {
      return new StringBuffer().append(studentId).append('-').append(courseId).append('-').append(
          semesterId).toString();
  }

  public double getObjectiveValue() {

      // TODO: You will need to implement this
      return 0;
  }

  public Vector<String> getCoursesForStudentSemester(String student, String semester) {

      // TODO: You will need to implement this
      return null;
  }

  public Vector<String> getStudentsForCourseSemester(String course, String semester) {

      // TODO: You will need to implement this
      return null;
  }
  
  private HashMap<String, GRBVar> GRBVARS_GLOBAL;
  public List<CourseSemester> getCourseSemestersforStudent(int studentId) {
	  List<CourseSemester> coursesForStudentBySemester = new ArrayList<CourseSemester>();
	  StudentService studentService = (StudentService) ServicesInstances.STUDENT_SERVICE.getService();
	  List<Student> students = new ArrayList<Student>(studentService.getAllStudents());

	  for (Student student : students) {
		  // System.out.printf("Student %d\n", student.get_UID());
		  if (student.get_UID() == studentId) {
			  for (int semesterId = 1; semesterId <= helpers.Constants.NUMBER_OF_SEMESTERS; semesterId++) {
				  //System.out.printf("Semester %d)\t", semesterId);
				  Semester newSemester = new Semester();
				  SemesterService semesterService = (SemesterService) ServicesInstances.SEMESTER_SERVICE.getService();
				  if (semesterService != null ) {
					  newSemester = semesterService.getById(semesterId);
				  }
				  for (Course course : student.getCourses()) {
					  String varId = createUniqueVariableId(student.get_UID(), course.getId(), semesterId);

					  try {
						if (GRBVARS_GLOBAL.get(varId).get(GRB.DoubleAttr.X) == 1) {
							  // System.out.printf(" (Course %s) ", course.getId());
							  CourseSemester courseSemester = new CourseSemester(course,newSemester);
							  coursesForStudentBySemester.add(courseSemester);
						  }
					} catch (GRBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  }

				  //System.out.println("");
			  }
		  }


	  }

	  return coursesForStudentBySemester;
  }
}
