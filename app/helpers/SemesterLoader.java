package helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import services.CourseService;
import services.SemesterService;
import services.ServicesInstances;

import models.Course;
import models.Semester;

public class SemesterLoader {
	public static void LoadSemesters() {
		List<Semester> semesters = new ArrayList<Semester>();
		// Create semesters.
		for (int i = 1; i <= helpers.Constants.NUMBER_OF_SEMESTERS; i++) {
			String semesterName = "";
			switch(i%3) {
			case 1:
				semesterName = "Fall";
				break;
			case 2:
				semesterName = "Spring";
				break;
			case 0:
			default:
				semesterName = "Summer";
			
			}
			semesters.add(new Semester(i, semesterName));
		}
		
		// Store semesters in database.
		SemesterService semesterService = (SemesterService) ServicesInstances.SEMESTER_SERVICE.getService();
		for (Semester semester: semesters){
			semesterService.storeSemester(semester);
		}
	}

}
