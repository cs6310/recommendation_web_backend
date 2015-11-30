package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
/**
 * Class to handle Administrator users input 
 * provided through files. 
 *
 */
public class AdministratorRequest {
	// List of classes offered next semester and their enrollment limit
	private Map<String,CourseSemester> classEnrollment;
	// Professor-Course Assignment
	private Map<Course, Professor> professorCourse;
	// TA-Course Assignment
	private Map<Course, TA> taCourseAssignment;
	// List of files with the information to be parsed.
	private List<File> inputFileList;
	
	//Default Constructor
	public AdministratorRequest(){
	}
	
	public AdministratorRequest(List<File> fileList){
		inputFileList = fileList;
		classEnrollment = new HashMap<String, CourseSemester>();
		professorCourse = new HashMap<Course, Professor>();
		taCourseAssignment = new HashMap<Course, TA>();
	}
	//Could implement in parallel as a future enhancement
	public void processInput(){		 
		processInputClassEnrollment();
		processInputProfCourse();
		processInputTACourse();
	}
	
	public boolean processInputClassEnrollment(){
		boolean result = false; 
//		% Format is: Course.id;Course.courseName;CourseSemester.enrollment_limit
//		5001;CS6210;30;
		File file = inputFileList.get(0);
		try{
			FileReader fr = new FileReader(file);
		    BufferedReader br = new BufferedReader(fr);
		    String line;
		    while((line = br.readLine()) != null){
		        if(line.contains("%")){
		        	System.out.println("Comment Line found. Skipping...");	
		        	continue;
		        }
		        System.out.println(line);
		        addClassEnrollmentInfo(line, ";");
		    }
		    br.close();
		}catch(IOException e){
			System.out.println("IOException: " + e.getMessage());
		}		
		return result;
	}
	/**
	 * Method to parse lines read from file into entries in the classEnrollment map
	 * @param line
	 * @param separator
	 */
	private void addClassEnrollmentInfo(String line, String separator){
		String[] arr = line.split(";");
		for(String s: arr){
			System.out.println("Token: " + s);
		}
        if(arr.length == 3){
        	int id = Integer.parseInt(arr[0]);
            int enrollLimit = Integer.parseInt(arr[2]);
            classEnrollment.put(arr[0], 
            		new CourseSemester(true, new Course(id, arr[1]), new Semester(0,"Spring 2016"),enrollLimit));            
        }        
	}
	
	public boolean processInputProfCourse(){
		boolean result = false; 
//		% Format is: Professor.id;Course.id;Course.courseName;
//		601;5001;CS6210;
		File file = inputFileList.get(1);
		try{
			FileReader fr = new FileReader(file);
		    BufferedReader br = new BufferedReader(fr);
		    String line;
		    while((line = br.readLine()) != null){
		        if(line.contains("%")){
		        	System.out.println("Comment Line found. Skipping...");	
		        	continue;
		        }
		        System.out.println(line);
		        addProfessorCourseInfo(line, ";");
		    }
		    br.close();
		}catch(IOException e){
			System.out.println("IOException: " + e.getMessage());
		}
		
		return true;
	}
	/**
	 * Method to parse lines read from file into entries in the professorCourse map
	 * @param line
	 * @param separator
	 */
	private void addProfessorCourseInfo(String line, String separator){
		String[] arr = line.split(";");
		for(String s: arr){
			System.out.println("Prof-Course Token: " + s);
		}
        if(arr.length == 3){
        	int id = Integer.parseInt(arr[0]);                       
            professorCourse.put(new Course(id, arr[1]), new Professor(id)) ;
        }        
	}
	
	public boolean processInputTACourse(){
		boolean result = false; 
//		% Format is: TA.id;Course.id;Course.courseName;
//		801;5001;CS6210;
		File file = inputFileList.get(1);
		try{
			FileReader fr = new FileReader(file);
		    BufferedReader br = new BufferedReader(fr);
		    String line;
		    while((line = br.readLine()) != null){
		        if(line.contains("%")){
		        	System.out.println("Comment Line found. Skipping...");	
		        	continue;
		        }
		        System.out.println(line);
		        addTACourseInfo(line, ";");
		    }
		    br.close();
		}catch(IOException e){
			System.out.println("IOException: " + e.getMessage());
		}
		
		return true;
	}
	/**
	 * Method to parse lines read from file into entries in the professorCourse map
	 * @param line
	 * @param separator
	 */
	private void addTACourseInfo(String line, String separator){
		String[] arr = line.split(";");
		for(String s: arr){
			System.out.println("TA-Course Token: " + s);
		}
        if(arr.length == 3){
        	int id = Integer.parseInt(arr[0]);         
            taCourseAssignment.put(new Course(id, arr[1]), new TA(id));
        }        
	}
	
	public Map<String, CourseSemester> getClassEnrollment() {
		return classEnrollment;
	}
	public void setClassEnrollment(Map<String, CourseSemester> classEnrollment) {
		this.classEnrollment = classEnrollment;
	}
	public Map<Course, Professor> getProfessorCourse() {
		return professorCourse;
	}
	public void setProfessorCourse(Map<Course, Professor> professorCourse) {
		this.professorCourse = professorCourse;
	}
	public Map<Course, TA> getTaCourseAssignment() {
		return taCourseAssignment;
	}
	public void setTaCourseAssignment(Map<Course, TA> taCourseAssignment) {
		this.taCourseAssignment = taCourseAssignment;
	}
	public List<File> getInputFileList() {
		return inputFileList;
	}
	public void setInputFileList(List<File> inputFileList) {
		this.inputFileList = inputFileList;
	}
	
	
}
