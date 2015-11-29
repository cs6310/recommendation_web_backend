import helpers.CourseLoader;
import helpers.StudentLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Course;
import models.Student;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;

import services.*;
public class Global extends GlobalSettings {
	@Override
	public void onStart(Application app) {

		JPA.withTransaction(new play.libs.F.Callback0() {

			@Override
			public void invoke() throws Throwable {
				initializeData();
				
			}
			
		});

		Logger.info("Starting app: " + (app.isProd() ? "Production" :
										(app.isTest() ? "Testing": "Development"))
										+ " server");
	}
	static void initializeData() {
		/*
		 * Load Courses
		 */
		try {
			CourseLoader.LoadCourses("resources/initial-classes.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CourseService courseService = (CourseService) ServicesInstances.COURSE_SERVICE.getService();
		List<Course> courses = new ArrayList<Course>(courseService.getAllCourses());
		Logger.debug("Number of courses " + courses.size());
		/*
		for (Course course: courses) {
			Logger.debug(course.toString());
		}
		*/
		/*
		 * Load Students
		 */
		try {
			StudentLoader.LoadStudent("resources/student_schedule.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StudentService studentService = (StudentService) ServicesInstances.STUDENT_SERVICE.getService();
		List<Student> students = new ArrayList<Student>(studentService.getAllStudents());
		Logger.debug("Number of students " + students.size());
		/*
		for (Student student: students) {
			Logger.debug(student.toString());
		}
		*/

	}
}