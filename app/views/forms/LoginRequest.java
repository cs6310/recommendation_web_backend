package views.forms;

import play.data.validation.Constraints.Required;

public class LoginRequest {
	// 1. Student ID
	@Required
	public Integer id;
	// 2. Password
	@Required
	public String password;
}
