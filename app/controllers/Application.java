package controllers;

import java.util.List;

import java.io.File;

import controllers.middleware.MyAuthenticator;

import models.Project1Scheduler;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import services.ServicesInstances;
import services.StudentService;
import views.forms.LoginRequest;
import views.forms.StudentRequest;
import play.db.jpa.Transactional;
import play.mvc.Security;



public class Application extends Controller {
	
	public static Project1Scheduler scheduler = new Project1Scheduler();
	@Transactional
	@Security.Authenticated(MyAuthenticator.class)
    public static Result index() {
		StudentService studentService = (StudentService) ServicesInstances.STUDENT_SERVICE.getService();
		Logger.info("number of students " + studentService.getAllStudents().size());
        return ok(views.html.index.render(""));
		//return ok(views.html.prestudentrequest.render());
    }

	@Security.Authenticated(MyAuthenticator.class)
    public static Result showStudentForm() {
    	int semester = Integer.parseInt(session("semester"));
    	Logger.info("Semester " + semester);
    	
    	return ok(views.html.studentrequest.render(
    				Form.form(StudentRequest.class).fill(new StudentRequest()),
    				StudentRequest.getCourseCountOptions(),
    				StudentRequest.getCoursesForSemester(semester)
    				));
    }
    
    public static Result showPreStudentForm() {
    	return ok(views.html.prestudentrequest.render(Form.form(SemesterNumber.class)));
    	//return ok(views.html.prestudentrequest.render());
    }

    public static Result processPreStudentForm() {
    	Form<SemesterNumber> semesterForm = Form.form(SemesterNumber.class).bindFromRequest();
    	SemesterNumber semester = semesterForm.get();
    	if (semester.id > 0) {
    		session("semester",""+(semester.id)+"");
    	}
		return redirect(routes.Application.showStudentForm());
    }

	@Security.Authenticated(MyAuthenticator.class)
    public static Result processStudentForm() {
    	Form<StudentRequest> studentRequestForm = Form.form(StudentRequest.class).bindFromRequest();
    	int semester = Integer.parseInt(session("semester"));
    	List<String> coursesForSemester = StudentRequest.getCoursesForSemester(semester);

    	if(studentRequestForm.hasErrors()){
        	//for (Map.Entry<String, ValidationError> entry: studentRequestForm.errors())
        	System.out.println("Error "+ studentRequestForm.errorsAsJson().toString()); 
            flash("error", "Please correct errors below.");
            return badRequest(views.html.studentrequest.render(studentRequestForm,
            													StudentRequest.getCourseCountOptions(),
            													coursesForSemester));
    	}else {
        	StudentRequest request = studentRequestForm.get();
        	System.out.println(request.toString());
    		if (coursesForSemester.size() != request.getPriorityListSize()) {
            flash("error", "Only filled out priorities for " +request.getPriorityListSize()
            		+ " of "+ coursesForSemester.size() + " courses. Please prioritize all courses.");
            return badRequest(views.html.studentrequest.render(studentRequestForm,
            													StudentRequest.getCourseCountOptions(),
            													coursesForSemester));           
	        }else{
	        	//Project1Scheduler scheduler = new Project1Scheduler();
	        	System.out.println(request.toString());
	        	scheduler.calculateSchedule("/home/ubuntu/Downloads/student_schedule.txt", request);
	        	return ok(request.toString());
	        }
    	}
    } 
    
	@Security.Authenticated(MyAuthenticator.class)
    public static Result showAdminForm(){
    	return ok(views.html.adminrequest.render());
    }
    
	@Security.Authenticated(MyAuthenticator.class)
    public static Result upload() {
  	  MultipartFormData body = request().body().asMultipartFormData();
  	  FilePart picture;
  	  try{
  		  picture = body.getFile("picture"); 
  		  if (picture != null) {
  	    	    String fileName = picture.getFilename();
  	    	    String contentType = picture.getContentType(); 
  	    	    File file = picture.getFile();
  	    	    return ok(views.html.index.render("Files submitted successfully!"));
  	    	  } 
  	    	  else {
  	    	    flash("error", "Missing file");
  	    	    return ok(views.html.index.render("ERROR: Submission failed!"));    
  	    	  }
  		  
  	  }catch(NullPointerException e){
  		  System.out.println(e.getMessage());
  		  return ok(views.html.index.render("Files submitted successfully!"));
  	  }  	  
  	}
    
    public static Result login() {
    	return ok(views.html.login.render(Form.form(LoginRequest.class)));
    }
    public static Result logout() {
    	session().clear();
    	return redirect(routes.Application.login());
    }
    
    public static Result authenticate() {
        Form<LoginRequest> loginForm = Form.form(LoginRequest.class).bindFromRequest();

        if (loginForm.hasErrors()) {
        	return badRequest(views.html.login.render(loginForm));
        }
        LoginRequest login = loginForm.get();
		if (login.id < 1000){
			Logger.info("student");
			session("id", ""+(login.id)+"");
			session("type", "student");
			return redirect(routes.Application.showPreStudentForm());
		}
		
		if (login.id > 1000){
			session("id", ""+(login.id)+"");
			session("type", "administrator");
			return redirect(routes.Application.showAdminForm());
		}
        return notFound("not found");
    }

    
    public static class SemesterNumber {
    	public int id;
    }
}
