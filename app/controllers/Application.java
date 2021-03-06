package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import controllers.middleware.MyAuthenticator;
import models.AdministratorRequest;
import models.Course;
import models.CourseSemester;
import models.Project1Scheduler;
import models.Semester;
import models.Student;
import play.Logger;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Security;
import services.CourseService;
import services.SemesterService;
import services.ServicesInstances;
import services.StudentService;
import views.forms.LoginRequest;
import views.forms.StudentRequest;

public class Application extends Controller {
	
	public static Project1Scheduler scheduler = new Project1Scheduler();
	@Transactional
	@Security.Authenticated(MyAuthenticator.class)
    public static Result index() {
    	scheduler.calculateSchedule();
        return ok(views.html.index.render(""));
    }

	private static List<Course> studentCourses;
	@Transactional
	@Security.Authenticated(MyAuthenticator.class)
    public static Result showStudentForm() {
    	int semester = Integer.parseInt(session("semester"));
    	scheduler.calculateSchedule();
    	studentCourses = new ArrayList<Course>();
    	List<CourseSemester> cs = scheduler.getCourseSemestersforStudent(Integer.parseInt(session("id")));
		CourseService courseService = (CourseService) ServicesInstances.COURSE_SERVICE.getService();
    	for (CourseSemester courseSemester : cs) {
    		if (courseSemester.get_semester().getId() < semester) {
	        	Course course = new Course();
	        	course = courseService.getById(courseSemester.get_course().getId());
	        	//Logger.info("course is " + course.getCourseName());
    			studentCourses.add(course);
    		}
    	}
    	return ok(views.html.studentrequest.render(
    				Form.form(StudentRequest.class).fill(new StudentRequest()),
    				StudentRequest.getCourseCountOptions(),
    				StudentRequest.getCoursesForSemester(semester), courseService
    				));
    }
    
	@Security.Authenticated(MyAuthenticator.class)
    public static Result showPreStudentForm() {
    	return ok(views.html.prestudentrequest.render(Form.form(SemesterNumber.class)));
    }

	@Security.Authenticated(MyAuthenticator.class)
    public static Result processPreStudentForm() {
    	Form<SemesterNumber> semesterForm = Form.form(SemesterNumber.class).bindFromRequest();
    	SemesterNumber semester = semesterForm.get();
    	if (semester.id > 0) {
    		session("semester",""+(semester.id)+"");
    	}
		return redirect(routes.Application.showStudentForm());
    }

	@Security.Authenticated(MyAuthenticator.class)
	@Transactional
    public static Result processStudentForm() {
    	Form<StudentRequest> studentRequestForm = Form.form(StudentRequest.class).bindFromRequest();
    	int semester = Integer.parseInt(session("semester"));
    	List<String> coursesForSemester = StudentRequest.getCoursesForSemester(semester);
    	CourseService courseService = (CourseService) ServicesInstances.COURSE_SERVICE.getService();
    	if(studentRequestForm.hasErrors()){
        	//for (Map.Entry<String, ValidationError> entry: studentRequestForm.errors())
        	System.out.println("Error "+ studentRequestForm.errorsAsJson().toString()); 
            flash("error", "Please correct errors below.");
            return badRequest(views.html.studentrequest.render(studentRequestForm,
            													StudentRequest.getCourseCountOptions(),
            													coursesForSemester, courseService));
    	}else {
        	StudentRequest request = studentRequestForm.get();
        	System.out.println(request.toString());
    		if (coursesForSemester.size() != request.getPriorityListSize()) {
            flash("error", "Only filled out priorities for " +request.getPriorityListSize()
            		+ " of "+ coursesForSemester.size() + " courses. Please prioritize all courses.");
            return badRequest(views.html.studentrequest.render(studentRequestForm,
            													StudentRequest.getCourseCountOptions(),
            													coursesForSemester, courseService));           
	        }else{
	        	System.out.println(request.toString());
	        	List<Integer> priorities = request.prioritiesForCoursesForSemester;
	        	int count = 0;
	        	int existingCourseSize = studentCourses.size();
	        	//CourseService courseService = (CourseService) ServicesInstances.COURSE_SERVICE.getService();
	        	Course course = new Course();
	        	for (int i = existingCourseSize; i < 12; i++) {
		            if (courseService != null ) {
		            	 course = courseService.getById(priorities.get(count));
		            }
		            if (!(studentCourses.contains(course))) {
		            	studentCourses.add(course);
		            } else {
		            	//count--;
		            }
	        		
	        		count++;
	        	}
	        	StudentService studentService = (StudentService) ServicesInstances.STUDENT_SERVICE.getService();
	        	Student student = new Student();
	            if (studentService != null ) {
	            	 student = studentService.getById(Integer.parseInt(session("id")));
	            }	
	        	student.setCourses(studentCourses);
	        	studentService.updateStudent(student);
	        	scheduler.calculateSchedule();
	        	List<CourseSemester> cs = scheduler.getCourseSemestersforStudent(Integer.parseInt(session("id")));
	        	HashMap<Course,Float> demand = scheduler.getCourseDemand(cs, semester);
	        	Collections.sort(cs, new Comparator<CourseSemester>() {

	                public int compare(CourseSemester cs1, CourseSemester cs2) {
	                	int val = cs1.get_semester().getId() - cs2.get_semester().getId();
	                	if (val != 0)
	                		return val;
	                	return cs1.get_course().getId() - cs2.get_course().getId();
	                    // return o2.getScores().get(0).compareTo(o1.getScores().get(0));
	                	//return 0;
	                }
	            });
	        	Map<Semester, Set<Course>> fullSchedule = new LinkedHashMap<Semester, Set<Course>>();
	        	for (CourseSemester courseSemester: cs) {
	        		if (fullSchedule.containsKey(courseSemester.get_semester()) == false) {
	        			Set<Course> courses_ = new LinkedHashSet<Course>();
	        			courses_.add(courseSemester.get_course());
	        			fullSchedule.put(courseSemester.get_semester(), courses_);
	        		} else {
	        			fullSchedule.get(courseSemester.get_semester()).add(courseSemester.get_course());
	        		}
	        	}
	            return ok(views.html.studentrequestoutput.render(cs, fullSchedule,demand));
	        }
    	}
    } 
    
	@Security.Authenticated(MyAuthenticator.class)
    public static Result showAdminForm(){
    	return ok(views.html.adminrequest.render(""));
    }
    
	@Security.Authenticated(MyAuthenticator.class)
    public static Result processAdminForm() {
		MultipartFormData body = request().body().asMultipartFormData();
	  	  List<FilePart> filePartList = new ArrayList<FilePart>();
	  	  List<File> fileList = new ArrayList<File>();
	  	  AdministratorRequest adReq = null;
	  	  
	  	  List<String> fileNamesList = new ArrayList<String>();
	  	  fileNamesList.add("classEnrollment");fileNamesList.add("professorCourse");fileNamesList.add("taCourse");
	  	  
	  	  int fileCounter =3;
	  	  int TOTAL_FILES = 3;
	  	  try{
	  		  for(int i=0; i < fileNamesList.size(); i++){
	  			//System.out.println("Inside the upload method-> for loop");
	  			  filePartList.add(body.getFile(fileNamesList.get(i)));
	  			if (filePartList.get(i) != null) {
	  				FilePart filePart = filePartList.get(i);
	  	    	    fileList.add(filePart.getFile());
	  			}
	  			else {
	  				fileCounter--;    	       
	  	    	  }
	  		  }
	  		if(fileCounter < TOTAL_FILES){
	  			return ok(views.html.adminrequest.render(
		    	    		"ERROR: Required 3 files. Uploaded " + fileCounter + " files.")); 
	  		}else{
	  			//Parse input from all submitted files and convert them into Maps.
	  			adReq = new AdministratorRequest(fileList);
	  			adReq.processInput();
	  			List<CourseSemester> list = new ArrayList<CourseSemester>();
	  			return ok(views.html.adminoutput.render(
	  					"Files submitted successfully!", 
	  					list,
	  					adReq.getClassEnrollment())
	  					//, Student->Courses_>Recommended Solution is to be passed to adminoutput template here.
	  					);
	  		}
	  	  }catch(NullPointerException e){
	  		  System.out.println(e.getMessage());
	  		  return ok(views.html.adminrequest.render("Something went wrong. Please Try again."));
	  	  }  	    	  
  	}
    
    public static Result login() {
    	return ok(views.html.login.render(Form.form(LoginRequest.class)));
    }
    public static Result logout() {
    	session().clear();
    	return redirect(routes.Application.login());
    }
    
    @Transactional
    public static Result authenticate() {
        Form<LoginRequest> loginForm = Form.form(LoginRequest.class).bindFromRequest();

        if (loginForm.hasErrors() || loginForm.hasGlobalErrors()) {
        	return badRequest(views.html.login.render(loginForm));
        }
        LoginRequest login = loginForm.get();
        // Students will have ids [1, 999]
		if (LoginRequest.isStudentId(login.id)){
			Logger.info("student");
			session("id", ""+(login.id)+"");
			session("type", "student");
			return redirect(routes.Application.showPreStudentForm());
		} else if (LoginRequest.isAdminId(login.id)){
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
