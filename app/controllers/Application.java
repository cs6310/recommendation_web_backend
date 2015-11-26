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
        	StudentRequest request = studentRequestForm.get();
        	return ok(request.toString());
        }
    } 
}
