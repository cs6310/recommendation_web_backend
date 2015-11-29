package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Course;
import play.Logger;
import play.Play;
import services.CourseService;
import services.ServicesInstances;

public class CourseLoader {
	// Each line is as follows
	// courseId;courseName;childCourse1;childCourse2...
	public static void LoadCourses(String filepath) throws IOException {
		InputStream is = Play.application().resourceAsStream(filepath);
		if (is == null) {
			Logger.error("Unable to read file for courses." + filepath);
			return;
		}
		// Maps the course id to the course
		HashMap<Integer, Course> courseMap = new HashMap<Integer, Course>();
		// Maps the parent course Id to a list of child course ids.
		HashMap<Integer, ArrayList<Integer>> childMap = new HashMap<Integer, ArrayList<Integer>>();
		
		// Read file.
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
	 
		String line = null;
		while ((line = br.readLine()) != null) {
			String[] fields = line.split(";");
			// Trim whitespaces.
			for (int i = 0; i < fields.length; i++)
				fields[i] = fields[i].trim();
			int courseId =-1;
			String courseName;
			for (int i = 0; i < fields.length; i++) {
				switch(i) {
				case 0:
					courseId = Integer.parseInt(fields[i]);
					break;
				case 1:
					courseName = fields[i];
					courseMap.put(courseId, new Course(courseId, courseName));
					break;
				default:
					fillDependencyMap(childMap, courseId, Integer.parseInt(fields[i]));
					break;
				}
			}
			// System.out.println(fields.length + " " + line);
		}
	 
		br.close();
		
		// Resolve courses. (Make connections between courses).
		for (Map.Entry<Integer, Course> entry: courseMap.entrySet()){
			// Get course
			Course course = entry.getValue();
			// Check for child classes.
			if (childMap.containsKey(entry.getKey())) {
				List<Integer> childCourseIds = childMap.get(entry.getKey());
				ArrayList<Course> childCourses = new ArrayList<Course>();
				// Get the child course instance.
				for (Integer childCourseId: childCourseIds) {
					if (courseMap.containsKey(childCourseId)) {
						Course childCourse = courseMap.get(childCourseId);
						childCourse.setParentClass(course);
						childCourses.add(childCourse);
					} else {
						//TODO should indicate the courses isn't here.
					}
				}
				// Assign the list of child classes.
				course.setChildClasses(childCourses);
			}
			
		}
		
		// Store courses in database.
		CourseService courseService = (CourseService) ServicesInstances.COURSE_SERVICE.getService();
		for (Map.Entry<Integer, Course> entry: courseMap.entrySet()){
			courseService.storeCourse(entry.getValue());
		}
	}
	private static void fillDependencyMap(HashMap<Integer, ArrayList<Integer>>dependencyMap, int parentId, int childId) {
		if (dependencyMap.containsKey(parentId)) {
			dependencyMap.get(parentId).add(childId);
		} else {
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(childId);
			dependencyMap.put(parentId, list);
		}
		
	}
}
