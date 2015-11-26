package models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
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
	// 4. A prioritized list of courses for the next semester.
	@Required
	public List<Integer> coursesForNextSemester;
	
	public String toString() {
		String courseList = " courses: ";
		if (coursesForNextSemester != null) {
			for (Integer courseIndex: coursesForNextSemester) {
				courseList += " " + courseIndex.toString() + " ";
			}
		}
		
		return "id: (" + id +
				") password: (" + ((password==null) ? "" : password ) +
				")"+ courseList +
				" desiredCouresCount: (" + desiredCoursesCount +")";
	}
}
