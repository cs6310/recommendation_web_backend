import java.util.Arrays;
import java.util.List;

import models.Course;

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
		 * Courses
		 */
		// Create the courses.
		List<Course> courses= Arrays.asList(new Course(0),
											new Course(1));
		CourseService courseService = (CourseService) ServicesInstances.COURSE_SERVICE.getService();
		// Store the courses in the database.
		for (Course course: courses) {
			courseService.storeCourse(course);
		}
		Logger.debug("Number of courses " + courseService.getAllCourses().size());
		
	}
}