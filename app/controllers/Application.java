package controllers;

import java.io.IOException;

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



public class Application extends Controller {
	public static Project1Scheduler scheduler = new Project1Scheduler();
    public static Result index() {
        return ok(views.html.index.render());
    }

    public static Result showStudentForm() {
    	return ok(views.html.studentrequest.render(Form.form(StudentRequest.class).fill(new StudentRequest())));
    }

    public static Result processStudentForm() {
    	Form<StudentRequest> studentRequestForm = Form.form(StudentRequest.class).bindFromRequest();
        if(studentRequestForm.hasErrors()){
            flash("error", "Please correct errors below.");
            return badRequest(views.html.studentrequest.render(studentRequestForm));
        }else{
        	//Project1Scheduler scheduler = new Project1Scheduler();
        	StudentRequest request = studentRequestForm.get();
        	scheduler.calculateSchedule("/home/ubuntu/Downloads/student_schedule.txt", request);
        	return ok(request.toString());
        }
    } 
}
