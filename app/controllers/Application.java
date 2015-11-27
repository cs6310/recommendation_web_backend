package controllers;

import java.io.IOException;
import java.util.Map;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Project1Scheduler;
import models.StudentRequest;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.Form;
import play.mvc.*;
import play.data.validation.ValidationError;



public class Application extends Controller {
	public static Project1Scheduler scheduler = new Project1Scheduler();
    public static Result index() {
        return ok(views.html.index.render());
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
        	System.out.println("ERror "+ studentRequestForm.errorsAsJson().toString()); 
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
}
