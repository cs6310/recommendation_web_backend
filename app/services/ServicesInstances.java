package services;
/**
 * This is the high level class that all of our persistance logic will inherit from
 */
public enum ServicesInstances {
	STUDENT_SERVICE(new StudentService()),
	COURSE_SERVICE(new CourseService()),
	SEMESTER_SERVICE(new SemesterService());

	private Object service;

	private ServicesInstances(Object service) {
		this.service = service;
	}

	public Object getService() {
		return this.service;
	}
}
