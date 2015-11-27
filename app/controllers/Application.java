package controllers;

import java.util.List;

import java.io.File;

import models.Project1Scheduler;
import models.StudentRequest;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;



public class Application extends Controller {
	public static Project1Scheduler scheduler = new Project1Scheduler();
    public static Result index() {
        return ok(views.html.index.render(""));
    }

    public static Result showStudentForm() {
    	return ok(views.html.studentrequest.render(
    				Form.form(StudentRequest.class).fill(new StudentRequest()),
    				StudentRequest.getCourseCountOptions(),
    				StudentRequest.getCoursesForSemester(1)
    				));
    }

    public static Result processStudentForm() {
    	Form<StudentRequest> studentRequestForm = Form.form(StudentRequest.class).bindFromRequest();

    	List<String> coursesForSemester = StudentRequest.getCoursesForSemester(1);

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
    
    public static Result showAdminForm(){
    	return ok(views.html.adminrequest.render());
    }
    
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
    
}
