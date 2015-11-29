package views.forms;

import models.Student;
import play.data.validation.Constraints.Required;
import services.ServicesInstances;
import services.StudentService;

public class LoginRequest {
	// 1. Student ID
	@Required
	public Integer id;
	// 2. Password
	@Required
	public String password;
	
	public String validate() {
		if (isStudentId(id)) {
			StudentService studentService = (StudentService) ServicesInstances.STUDENT_SERVICE.getService();
			Student student = studentService.getById(id);
			if (student == null) {
				return helpers.Constants.INVALID_ID_ERROR;
			} else if (!student.getPassword().equals(password)) {
				return helpers.Constants.INVALID_CREDENTIALS_ERROR;
			}
		} else if (isAdminId(id)) {
			//TODO once admin info is in a table.
			return null;
			
		}
		return null;
	}
	
	public static boolean isStudentId(Integer _id) {
		return _id > 0 && _id < 1000;
	}
	
	public static boolean isAdminId(Integer _id) {
		return _id > 1000 && _id < 10000;
	}
}
