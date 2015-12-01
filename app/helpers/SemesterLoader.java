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
		int year = 2015;
		for (int i = 1; i <= helpers.Constants.NUMBER_OF_SEMESTERS; i++) {
			String semesterName = "";
			switch(i%3) {
			case 1:
				semesterName = "Fall " + year;
				break;
			case 2:
				year++; // Spring means we are in a new year.
				semesterName = "Spring " + year;
				break;
			case 0:
			default:
				semesterName = "Summer " + year;
			
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
