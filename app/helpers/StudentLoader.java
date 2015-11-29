package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.Course;
import models.Student;
import play.Logger;
import play.Play;
import services.CourseService;
import services.ServicesInstances;
import services.StudentService;

public class StudentLoader {
	// Each line is as follows
	// courseId;courseName;childCourse1;childCourse2...
	public static void LoadStudent(String filepath) throws IOException {
		InputStream is = Play.application().resourceAsStream(filepath);
		if (is == null) {
			Logger.error("Unable to read file for courses." + filepath);
			return;
		}
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
		int            id             = 1;
		List<Student> students = new ArrayList<Student>();
		String line = null;
		CourseService courseService = (CourseService) ServicesInstances.COURSE_SERVICE.getService();
		// Read the students and their courses.
		while ((line = bufferedReader.readLine()) != null) {
			if ((!(line.contains("%"))) && (!(line.isEmpty()))) {
				Scanner read = new Scanner(line);

				
				List<Course> courses = new ArrayList<Course>();

				while (read.hasNext()) {
					int courseId = (int) read.nextFloat();
					Course course = courseService.getById(courseId);
					if (course == null) {
						Logger.error("Could not find course with id: " +courseId + " when adding for student " + id);
					} else {
						courses.add(courseService.getById(courseId));
					}
				}
				Student student = new Student(id, courses);

				students.add(student);
				id++;
			}
		}
		// Store the students in the database.
		StudentService studentService = (StudentService) ServicesInstances.STUDENT_SERVICE.getService();
		for (Student student: students) {
			studentService.storeStudent(student);
		}
	}
}
