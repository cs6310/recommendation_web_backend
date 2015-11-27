package models;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import static play.data.validation.Constraints.*;

public class StudentRequest {
	// 1. Student ID
	@Required
	public Integer id;
	// 2. Password
	@Required
	public String password;
	// 3. The number of desired courses
	@Required
	public Integer desiredCoursesCount;
	// @Required
	public List<Integer> prioritiesForCoursesForSemester;
	// Maps Course Id to priority for semester.
	public Map<Integer, Integer> prioritizedCourses;
	
	public int getPriorityListSize() {
		if (prioritiesForCoursesForSemester == null)
			return 0;
		return prioritiesForCoursesForSemester.size();
	}
	public String toString() {
		String priorities = " priorities: ";
		if (prioritiesForCoursesForSemester != null) {
			System.out.println("courselength " + prioritiesForCoursesForSemester.size());
			for (Integer priority: prioritiesForCoursesForSemester) {
				priorities += " " + priority.toString() + " ";
			}
		}
		
		return "id: (" + id +
				") password: (" + ((password==null) ? "" : password ) +
				")"+ priorities +
				" desiredCouresCount: (" + desiredCoursesCount +")";
	}
	
	public static List<String> getCourseCountOptions() {
        List<String> tmp = new ArrayList();
        tmp.add("1");
        tmp.add("2");
        return tmp;
	}

	public static List<String> getCoursesForSemester(int semesterId) {
		Project1Scheduler scheduler = new Project1Scheduler();
		List<Integer> courseIds = scheduler.getClassesForSemester(semesterId);
		Collections.sort(courseIds);
		List<String> coursesString = new ArrayList();
		for (Integer courseId: courseIds) {
			coursesString.add(courseId.toString());
		}
		return coursesString;
	}
}
